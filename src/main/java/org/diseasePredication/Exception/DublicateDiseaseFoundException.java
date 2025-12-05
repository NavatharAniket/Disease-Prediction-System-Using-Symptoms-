package org.diseasePredication.Exception;

public class DublicateDiseaseFoundException extends RuntimeException {
	String name;

	public DublicateDiseaseFoundException(String name) {
		this.name = name;
	}

	public String getErrorMsg() {
		return "' " + name + " '" + " Disease All Ready Redisted";
	}

}
