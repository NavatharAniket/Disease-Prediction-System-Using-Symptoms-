package org.diseasePredication.Exception;

public class DiseaseNotFoundException extends RuntimeException {
	String name;
	public DiseaseNotFoundException(String name)
	{
		this.name=name;
	}
	
	public String getErrorMsg()
	{
		return "' "+name+" '"+"  this disease not found";
	}

}
