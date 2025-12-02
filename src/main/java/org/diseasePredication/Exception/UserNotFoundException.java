package org.diseasePredication.Exception;

public class UserNotFoundException extends RuntimeException{
	String email;
	public UserNotFoundException(String email) {
		this.email= email;
	}
	public String getErrorMsg() {
		return "'"+email+"'-"+"This User not Found ";
	}
}
