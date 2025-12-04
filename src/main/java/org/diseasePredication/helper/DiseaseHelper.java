package org.diseasePredication.helper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.diseasePredication.Exception.DiseaseNotFoundException;
import org.diseasePredication.Exception.DublicateDiseaseFoundException;
import org.diseasePredication.model.DiseaseModel;
import org.diseasePredication.model.SymptomsModel;
import org.diseasePredication.repository.DiseaseRepository;
import org.diseasePredication.repository.DiseaseRepositoryImpl;

public class DiseaseHelper {
	public static void crudDisease() {
		Scanner sc = new Scanner(System.in);
		do {

			System.out.println("Enter 1 for add Disease");
			System.out.println("Enter 2 for View All Disease");
			System.out.println("Enter 3 for Update Disease");
			System.out.println("Enter 4 for Delete Disease");
			System.out.println("enter 5 go for admin panel");
			System.out.println("Enter your choice");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				try {
					sc.nextLine();
					System.out.println("Enter Disease name");
					String diseaseName = sc.nextLine();

					DiseaseModel diseaseModel = new DiseaseModel();
					diseaseModel.setDiseaseName(diseaseName);
					if (ServiceHelper.diseaseRepository.isDiseasePresent(diseaseModel)) {
						throw new DublicateDiseaseFoundException(diseaseName);
					}

					Set<String> allSymtomsOfDisease = new HashSet<String>();

					System.out.println("list of all Symptoms");
					List<SymptomsModel> list = ServiceHelper.symptomsRepository.getAllSymptoms();
					list.forEach(symptoms -> System.out.println(symptoms.getSymtomsName()));
					while (true) {
						System.out.print("Enter Symptom for disease");
						String symptom = sc.nextLine();
						allSymtomsOfDisease.add(symptom);
						System.out.println("Enter yes for add more symtom ");
						System.out.println("Enter no for not add more symptoms");
						String decision = sc.nextLine();
						if (decision.equals("no")) {
							break;
						}
					}
					DiseaseRepository diseaseRepository = new DiseaseRepositoryImpl();
					boolean b = diseaseRepository.isAddDisease(diseaseModel, allSymtomsOfDisease);
					if (b) {
					} else {
						System.out.println("Disease not added ");
					}
				} catch (DublicateDiseaseFoundException d) {
					System.out.println(d.getErrorMsg());
				} catch (Exception ex) {
					System.out.println("Exception at time of insert disease at disease helper");
					ex.printStackTrace();
				}
				break;
			case 2:
				System.out.println("all list of diseases ");
				Map<String, List<String>> hm = ServiceHelper.diseaseRepository.getAllDiseases();

				System.out.println(hm);
				break;
			case 3:
				sc.nextLine();
				try {
				System.out.println("Enter Disease name for update Disease");
				String diseaseName = sc.nextLine();
				DiseaseModel diseaseModel = new DiseaseModel();
				diseaseModel.setDiseaseName(diseaseName);
				
				DiseaseRepository diseaseRepository = new DiseaseRepositoryImpl();
				boolean b=diseaseRepository.isDiseasePresent(diseaseModel);
				if(!b)
				{
					throw new DiseaseNotFoundException(diseaseName);
				}
				System.out.println("\nExisting Symptoms for " + diseaseName + ":");
				List<SymptomsModel> existing = diseaseRepository.getSymptomsByDiseaseName(diseaseName);
				existing.forEach(s -> System.out.println(" - " + s.getSymtomsName()));

				System.out.println("All Symptoms List ");
				List<SymptomsModel> l = ServiceHelper.symptomsRepository.getAllSymptoms();
				l.forEach(s -> System.out.println(s.getSymtomsName()));
				Set<String> allSymtomsOfDisease = new HashSet<String>();

				while (true) {
					System.out.println("Enter Symptoms name ");
					String name = sc.nextLine();
					allSymtomsOfDisease.add(name);

					System.out.println("Enter yes for add more symtom ");
					System.out.println("Enter no for not add more symptoms");
					String decision = sc.nextLine();
					if (decision.equals("no")) {
						break;
					}
				}

				ServiceHelper.diseaseRepository.updateDisease(diseaseModel, allSymtomsOfDisease);
				}
				catch(DiseaseNotFoundException ex)
				{
					System.out.println(ex.getErrorMsg());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				break;
			case 4:

				sc.nextLine();
				try {

					System.out.println("Enter Disease name");
					String diseaseName = sc.nextLine();
					
					boolean b = ServiceHelper.diseaseRepository.isDeleteDisease(diseaseName);
					if (b) {
						System.out.println("Disease deleted");
					} else {
						System.out.println("Disease not deleted ");
					}
				}
				catch(DiseaseNotFoundException ex)
				{
					System.out.println(ex.getErrorMsg());
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				
				break;
			case 5:
				
				break;
			default:
				System.out.println("Enter valid choice");
			}
		} while (true);
	}
}
