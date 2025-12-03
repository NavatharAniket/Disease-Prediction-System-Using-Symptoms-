package org.diseasePredication.helper;

import org.diseasePredication.repository.SymptomsRepositoryImpl;
import org.diseasePredication.repository.UserRepository;
import org.diseasePredication.repository.UserRepositoryImpl;
import org.diseasePredication.repository.SymptomsRepository;

public class ServiceHelper {
	static UserRepository UserRepository=new UserRepositoryImpl();
	static SymptomsRepository symptomsRepository = new SymptomsRepositoryImpl();
	
	
}

