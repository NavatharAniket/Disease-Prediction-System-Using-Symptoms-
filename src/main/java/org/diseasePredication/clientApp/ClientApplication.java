package org.diseasePredication.clientApp;

import java.util.Scanner;

import org.diseasePredication.helper.DiseaseHelper;
import org.diseasePredication.helper.SymptomsHelper;
import org.diseasePredication.helper.UserHelper;
import org.diseasePredication.model.AdminLogin;
import org.diseasePredication.repository.ValidateAdmin;
import org.diseasePredication.repository.ValidateAdminRepoimpl;

public class ClientApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name;
		String password;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter 1 for Admin login ");
			System.out.println("Enter 2 for User Login ");
			System.out.println("Enter 3 if want to terminate code ");

			int ch = sc.nextInt();
			sc.nextLine();
			switch (ch) {
			case 1:
				// taking input from user for validation perpose

				System.out.println("Enter your name");
				name = sc.nextLine();

				System.out.println("Enter your password");
				password = sc.nextLine();
				// create object of admin pojo class for save name and password

				AdminLogin login = new AdminLogin();
				login.setUserName(name);
				login.setPassword(password);
				// create object of validateAdmin reference and validate Admin repo impl object
				// for validation
				ValidateAdmin validateAdmin = new ValidateAdminRepoimpl();
				// get input and check that successfully enter or not
				boolean b = validateAdmin.verifyAdminLogin(login);

				if (b) {
					do {
						System.out.println("Admin Login Sucessfully ");
						System.out.println("Admin Menu :-");
						System.out.println("Enter 1 for UserAdmin");
						System.out.println("Enter 2 for Symptoms Admin");
						System.out.println("Enter 3 for Disease Admin");
						System.out.println("Enter 4 for exit application");
						int choice = sc.nextInt();
						switch (choice) {
						case 1:
							UserHelper.crudUsers();
							break;
						case 2:
							SymptomsHelper.crudSymtoms();
							break;
						case 3:
							DiseaseHelper.crudDisease();
							break;
						case 4:
							break;
						default:
							System.out.println("Enter valid choice ");
						}
					} while (true);
				} else {
					System.out.println("Admin login unsucessfull........");
				}

				break;
			case 3:
				System.out.println("Code terminated Sucessfully ");
				System.exit(0);

			default:
				System.out.println("Enter invalid choice ");
			}
		} while (true);
	}

}
