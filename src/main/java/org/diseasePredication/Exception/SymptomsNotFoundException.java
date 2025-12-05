package org.diseasePredication.Exception;

public class SymptomsNotFoundException extends RuntimeException {
	String str;

	public SymptomsNotFoundException(String str) {
		this.str = str;
	}

	public String getErrorMsg() {
		return "'" + str + "'-" + "this Symptoms not found our dataset ";
	}

}
