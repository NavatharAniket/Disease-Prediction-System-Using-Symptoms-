package org.diseasePredication.helper;

import java.util.List;
import java.util.Scanner;

import org.diseasePredication.model.SymptomsModel;
import org.diseasePredication.repository.SymptomsRepository;
import org.diseasePredication.repository.SymptomsRepositoryImpl;

public class SymptomsHelper {
	public static void crudSymtoms() {
		do {
			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("------------------------------------------------------------------------");
				System.out.println("Enter 1 for Add Symptoms");
				System.out.println("Enter 2 for get all Symptoms");
				System.out.println("Enter 3 for Delete Symptoms");
				System.out.println("Enter 4 for go admin panel");
				System.out.println("------------------------------------------------------------------------");
				System.out.println("Enter your choice");
				int choice = sc.nextInt();
				switch (choice) {
				case 1:
					sc.nextLine();
					System.out.println("Enter Symptom name");
					String symptomName = sc.nextLine();
					SymptomsModel symptomsModel = new SymptomsModel();
					symptomsModel.setSymtomsName(symptomName);
					SymptomsRepository symptomsrepository = new SymptomsRepositoryImpl();
					boolean b = symptomsrepository.isAddSymptoms(symptomsModel);

					if (b) {
						System.out.println("Symptoms added successfully");
					} else {
						System.out.println("Symptoms not added Successfully ");
					}
					break;
				case 2:
					List<SymptomsModel> list = ServiceHelper.symptomsRepository.getAllSymptoms();
					for (int i = 0; i < list.size(); i++) {
						System.out.println((i + 1) + ". " + list.get(i).getSymtomsName());
					}
					break;
				case 3:
					sc.nextLine();
					System.out.println("Enter name for Delete Symptoms");
					String name = sc.nextLine();
					b = ServiceHelper.symptomsRepository.isDeleteSymptoms(name);
					if (b) {
						System.out.println("Symptoms deleted Sucessfully");
					} else {
						System.out.println("Symptoms not deleted ");
					}
					break;
				case 4:
					System.out.println("Exit from Symtoms crud ");
					return;
				default:
					System.out.println("Enter valid choice ...! ");
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
		} while (true);
	}
}
