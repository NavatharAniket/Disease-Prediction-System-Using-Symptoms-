package org.diseasePredication.helper;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

import org.diseasePredication.model.HistoryModel;
import org.diseasePredication.model.SymptomsModel;
import org.diseasePredication.repository.UserHistoryRepo;
import org.diseasePredication.repository.UserHistoryRepoImpl;
import org.diseasePredication.services.DiseasePredictor;
import org.diseasePredication.services.PredictionResult;

public class UserLoginHelper {

		public static void getUserDiseaseInfo(String email)
		{
			Scanner sc=new Scanner(System.in);
			System.out.println("List of all Symptoms");
			List<SymptomsModel>list = ServiceHelper.symptomsRepository.getAllSymptoms();
			list.forEach(model->System.out.println(model.getSymtomsName()));
			Set<String>userSymptoms=new HashSet<String>();
			
			Set<String>invalidSymtoms=new HashSet<String>();
			list.forEach(model->{
				invalidSymtoms.add(model.getSymtomsName());
			});
			while(true)
			{
				System.out.println("select Symtoms from above list");
				String symptomsName=sc.nextLine();
				if(!invalidSymtoms.contains(symptomsName))
				{
					System.out.println("Symtoms are invalid ");
					continue;
				}
				userSymptoms.add(symptomsName);
				System.out.println("Enter yes for select more ");
				System.out.println("Enter no for stop selection ");
				String dicision =sc.nextLine();
				if(dicision.equals("no"))
				{
					break;
				}
			}
			
			System.out.println(userSymptoms);
			
			String symptomsInput = String.join(",", userSymptoms);
			
			System.out.println("User Symptoms => " + symptomsInput);
			
			PredictionResult result = DiseasePredictor.predictDiseaseWithConfidence(symptomsInput);
			String deasease=result.getDisease();
			double d=(result.getConfidence() * 100);
			System.out.println("Predicted Disease: " + deasease);
			System.out.println("Confidence: " + d + "%");
			
			HistoryModel history=new HistoryModel();
			history.setEmail(email);
			history.setUserSymtoms(symptomsInput);
			history.setPredictedDisease(deasease);
			history.setConfidence(d);
			
			UserHistoryRepo userHistoryRepo=new UserHistoryRepoImpl();
			boolean b=userHistoryRepo.storeUserHistory(history);
			if(b)
			{
				System.out.println("History stored sucessfully ");
			}
			else
			{
				System.out.println("History not stored ");
			}
		}
}
