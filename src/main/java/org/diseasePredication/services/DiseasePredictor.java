package org.diseasePredication.services;

import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DiseasePredictor {

	private static Instances modelStructure;
	private static Classifier classifier;
	private static StringToWordVector filter;

	static {
		try {
			boolean modelLoaded = loadModel();

			// If model failed to load → Train model automatically
			if (!modelLoaded) {
				System.out.println("⚠ Model missing or corrupted. Training new model...");

				ModelTrainer trainer = new ModelTrainerImpl();
				trainer.trainModel();

				// Load model again after training
				loadModel();
			}

			System.out.println("Prediction system ready.");

		} catch (Exception e) {
			System.out.println("Fatal error: cannot initialize prediction system.");
			e.printStackTrace();
		}
	}

	private static boolean loadModel() {
		try {

			File modelFile = new File("src/main/resources/disease_model.model");
			if (!modelFile.exists()) {
				System.out.println("⚠ Model file not found.");
				return false;
			}

			// Load ARFF structure
			DataSource ds = new DataSource("src/main/resources/disease_dataset.arff");
			modelStructure = ds.getDataSet();
			modelStructure.setClassIndex(modelStructure.numAttributes() - 1);

			// Load classifier + filter
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(modelFile));

			classifier = (Classifier) ois.readObject();
			filter = (StringToWordVector) ois.readObject();
			ois.close();

			System.out.println("✔ Model and filter loaded successfully.");
			return true;

		} catch (Exception e) {
			System.out.println("⚠ Failed to load model. Need retraining.");
			return false;
		}
	}

	public static String predictDisease(String symptomsInput) {
		try {
			// Create dataset with 1 instance
			Instances inputData = new Instances(modelStructure, 1);

			DenseInstance inst = new DenseInstance(modelStructure.numAttributes());
			inst.setDataset(modelStructure);

			// Set symptoms string
			inst.setValue(0, symptomsInput);
			inputData.add(inst);

			// Apply filter
			Instances filteredInput = Filter.useFilter(inputData, filter);

			// Classify
			double result = classifier.classifyInstance(filteredInput.firstInstance());

			// Return predicted disease
			return filteredInput.classAttribute().value((int) result);

		} catch (Exception e) {
			System.out.println("Prediction failed!");
			e.printStackTrace();
			return "Error in prediction";
		}
	}

	public static PredictionResult predictDiseaseWithConfidence(String symptomsInput) {
		try {
			Instances inputData = new Instances(modelStructure, 1);

			DenseInstance inst = new DenseInstance(modelStructure.numAttributes());
			inst.setDataset(modelStructure);

			inst.setValue(0, symptomsInput);
			inputData.add(inst);

			// Apply filter
			Instances filteredInput = Filter.useFilter(inputData, filter);

			// Get probability distribution
			double[] distribution = classifier.distributionForInstance(filteredInput.firstInstance());

			int bestIndex = 0;
			for (int i = 1; i < distribution.length; i++) {
				if (distribution[i] > distribution[bestIndex]) {
					bestIndex = i;
				}
			}

			String disease = filteredInput.classAttribute().value(bestIndex);
			double confidence = distribution[bestIndex];

			return new PredictionResult(disease, confidence);

		} catch (Exception e) {
			e.printStackTrace();
			return new PredictionResult("Error", 0.0);
		}
	}

}
