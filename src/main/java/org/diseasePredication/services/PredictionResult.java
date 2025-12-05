package org.diseasePredication.services;

public class PredictionResult {
	private String disease;
	private double confidence;

	public PredictionResult(String disease, double confidence) {
		this.disease = disease;
		this.confidence = confidence;
	}

	public String getDisease() {
		return disease;
	}

	public double getConfidence() {
		return confidence;
	}
}
