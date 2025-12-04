package org.diseasePredication.helper;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

import org.diseasePredication.model.SymptomsModel;
import org.diseasePredication.services.DiseasePredictor;

public class UserLoginHelper {
		public static void main(String[] args)
		{
			UserLoginHelper u=new UserLoginHelper();
			u.getUserDiseaseInfo();
		}
		public static void getUserDiseaseInfo()
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
			
			String predictedDisease = DiseasePredictor.predictDisease(symptomsInput);
			
			System.out.println("\n=== Prediction Result ===");
	        System.out.println("Based on your symptoms:");
	        System.out.println(userSymptoms);
	        System.out.println("\nPredicted Disease: " + predictedDisease);
		}
}
