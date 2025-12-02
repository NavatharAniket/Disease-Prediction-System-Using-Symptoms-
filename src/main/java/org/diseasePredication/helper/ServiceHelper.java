package org.diseasePredication.helper;

import org.diseasePredication.repository.UserRepository;
import org.diseasePredication.repository.UserRepositoryImpl;

public class ServiceHelper {
	static UserRepository UserRepository = new UserRepositoryImpl();
}
