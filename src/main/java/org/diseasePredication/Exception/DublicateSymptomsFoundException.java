package org.diseasePredication.Exception;

public class DublicateSymptomsFoundException extends RuntimeException {

		
		String name;
		public DublicateSymptomsFoundException(String name) {
			this.name = name;
		}
		public String getErrorMsg() {
			return "'"+name+"'-"+"Symptoms already register ";
		}


}
