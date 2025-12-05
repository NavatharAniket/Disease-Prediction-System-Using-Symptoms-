package org.diseasePredication.services;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.diseasePredication.repository.ArefFileGenerator;
import org.diseasePredication.repository.ArefFileGeneratorImpl;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class ModelTrainerImpl implements ModelTrainer {
	@Override
	public void trainModel() {
		try {
			// Step 1: Regenerate ARFF file
			ArefFileGenerator generator = new ArefFileGeneratorImpl();
			generator.generateARFF();

			String arffPath = "src/main/resources/disease_dataset.arff";
			String modelPath = "src/main/resources/disease_model.model";

			// Step 2: Load ARFF
			DataSource ds = new DataSource(arffPath);
			Instances data = ds.getDataSet();
			data.setClassIndex(data.numAttributes() - 1);

			// Step 3: Convert string symptoms → numeric features
			StringToWordVector filter = new StringToWordVector();
			filter.setAttributeIndices("first"); // Convert symptoms column
			filter.setLowerCaseTokens(true);
			filter.setOutputWordCounts(true);
			filter.setTFTransform(true);
			filter.setIDFTransform(true);
			filter.setInputFormat(data);

			Instances filteredData = Filter.useFilter(data, filter);

			// Step 4: Train NaiveBayes
			Classifier classifier = new NaiveBayes();
			classifier.buildClassifier(filteredData);

			// Step 5: Save classifier + filter
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(modelPath));
			oos.writeObject(classifier);
			oos.writeObject(filter);
			oos.close();

			System.out.println("Model trained and saved successfully!");

		} catch (Exception ex) {
			System.out.println("Exception from model trainer");
			ex.printStackTrace();
		}
	}
}
