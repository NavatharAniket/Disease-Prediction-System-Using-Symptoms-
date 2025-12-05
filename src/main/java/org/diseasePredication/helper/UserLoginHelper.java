package org.diseasePredication.helper;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import java.util.HashSet;

import org.diseasePredication.Exception.UserNotFoundException;
import org.diseasePredication.model.HistoryModel;
import org.diseasePredication.model.SymptomsModel;
import org.diseasePredication.repository.UserHistoryRepo;
import org.diseasePredication.repository.UserHistoryRepoImpl;
import org.diseasePredication.services.DiseasePredictor;
import org.diseasePredication.services.PredictionResult;

public class UserLoginHelper {

	public static void getUserDiseaseInfo(String email) {
		do {
			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("-----------------------------------------");
				System.out.println("1 for see your disease");
				System.out.println("2 for see your medical history");
				System.out.println("3 for return main menu");
				System.out.println("--------------------------------------------");
				int choice = sc.nextInt();

				switch (choice) {
				case 1:
					UserLoginHelper userLoginHelper = new UserLoginHelper();
					userLoginHelper.getUserDisease(email);
					break;
				case 2:
					try {
						UserHistoryRepo userHistoryRepo = new UserHistoryRepoImpl();
						List<HistoryModel> list = userHistoryRepo.getUserHistory(email);
						if(list.isEmpty())
						{
							System.out.println("user not have any medical history");
							break;
						}
						list.forEach(user -> {
							System.out.println("{");
							System.out.println("user gmail id " + user.getEmail());
							System.out.println("user desiease is " + user.getPredictedDisease());
							System.out.println("user selected symtoms " + user.getUserSymtoms());
							System.out.println("confidance of desease " + user.getConfidence());
							System.out.println("date of verification " + user.getDate());
							System.out.println("}");
						});
					} catch (Exception ex) {
						System.out.println(ex);
					}
					break;
				case 3:
					System.out.println("return to main menu");
					return;
				default:
					System.out.println("invalid choice ");
				}

			} catch (UserNotFoundException ex) {
				System.out.println(ex.getErrorMsg());
			} catch (Exception ex) {
				System.out.println(ex);
			}
		} while (true);
	}

	public void getUserDisease(String email) {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("List of all Symptoms");
			List<SymptomsModel> list = ServiceHelper.symptomsRepository.getAllSymptoms();
			list.forEach(model -> System.out.println(model.getSymtomsName()));
			Set<String> userSymptoms = new HashSet<String>();

			Set<String> invalidSymtoms = new HashSet<String>();
			list.forEach(model -> {
				invalidSymtoms.add(model.getSymtomsName());
			});
			while (true) {
				System.out.println("select Symtoms from above list");
				String symptomsName = sc.nextLine();
				if (!invalidSymtoms.contains(symptomsName)) {
					System.out.println("Symtoms are invalid ");
					continue;
				}
				userSymptoms.add(symptomsName);
				System.out.println("Enter yes for select more ");
				System.out.println("Enter no for stop selection ");
				String dicision = sc.nextLine();
				if (dicision.equals("no")) {
					break;
				}
			}

			System.out.println(userSymptoms);

			String symptomsInput = String.join(",", userSymptoms);

			System.out.println("User Symptoms => " + symptomsInput);

			PredictionResult result = DiseasePredictor.predictDiseaseWithConfidence(symptomsInput);
			String deasease = result.getDisease();
			double d = (result.getConfidence() * 100);
			System.out.println("Predicted Disease: " + deasease);
			System.out.println("Confidence: " + d + "%");

			HistoryModel history = new HistoryModel();
			history.setEmail(email);
			history.setUserSymtoms(symptomsInput);
			history.setPredictedDisease(deasease);
			history.setConfidence(d);

			UserHistoryRepo userHistoryRepo = new UserHistoryRepoImpl();
			boolean b = userHistoryRepo.storeUserHistory(history);
			if (b) {
				System.out.println("History stored sucessfully ");
			} else {
				System.out.println("History not stored ");
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return;
		}
	}
}
