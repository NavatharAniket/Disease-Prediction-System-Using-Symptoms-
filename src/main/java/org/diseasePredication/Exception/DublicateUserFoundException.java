package org.diseasePredication.Exception;

public class DublicateUserFoundException extends RuntimeException {

	String email;

	public DublicateUserFoundException(String email) {
		this.email = email;
	}

	public String getErrorMsg() {
		return "'" + email + "'-" + "email already register on our portal ";
	}

}
