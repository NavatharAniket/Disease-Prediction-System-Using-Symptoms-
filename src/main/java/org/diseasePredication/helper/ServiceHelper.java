package org.diseasePredication.helper;

import org.diseasePredication.repository.SymptomsRepositoryImpl;
import org.diseasePredication.repository.UserRepository;
import org.diseasePredication.repository.UserRepositoryImpl;
import org.diseasePredication.model.DiseaseRepositoryImpl;
import org.diseasePredication.repository.DiseaseRepository;
import org.diseasePredication.repository.SymptomsRepository;

public class ServiceHelper {
	public static UserRepository UserRepository=new UserRepositoryImpl();
	public static SymptomsRepository symptomsRepository = new SymptomsRepositoryImpl();
	public static DiseaseRepository diseaseRepository =new DiseaseRepositoryImpl();
	
	
}

