package org.diseasePredication.helper;

import java.util.List;
import java.util.Scanner;

import org.diseasePredication.Exception.DublicateUserFoundException;
import org.diseasePredication.Exception.UserNotFoundException;
import org.diseasePredication.model.HistoryModel;
import org.diseasePredication.model.UserModel;
import org.diseasePredication.repository.UserHistoryRepo;
import org.diseasePredication.repository.UserHistoryRepoImpl;

public class UserHelper {
	public static void crudUsers() {
		int k = 0;
		do {
			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("------------------------------------------------------------------------");
				System.out.println("1 - Add New user");
				System.out.println("2 - View All User");
				System.out.println("3 - Update User using email");
				System.out.println("4 - Delete User using email");
				System.out.println("5  - for get user history perticular history ");
				System.out.println("6 - for get all user history ");
				System.out.println("7 - enter for go admin panel ");
				System.out.println("------------------------------------------------------------------------");
				int choice = sc.nextInt();
				k = choice;
				switch (choice) {
				case 1:
					try {
						sc.nextLine();
						System.out.println("Enter user name");
						String userName = sc.nextLine();
						System.out.println("Enter user email");
						String email = sc.nextLine();
						System.out.println("Enter your password");
						String password = sc.nextLine();

						UserModel userModel = new UserModel();
						boolean b = ServiceHelper.UserRepository.isUserPresent(userModel);
						if (b) {
							throw new DublicateUserFoundException(email);
						}
						userModel.setName(userName);
						userModel.setPassword(password);
						userModel.setEmail(email);
						boolean result = ServiceHelper.UserRepository.isAddUser(userModel);
						if (result) {
							System.out.println("user added sucessfully...");
						} else {
							System.out.println("user not added...");
						}
					} catch (DublicateUserFoundException ex) {
						System.out.println(ex.getErrorMsg());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;

				case 2:
					List<UserModel> list = ServiceHelper.UserRepository.getAllUsers();
					list.forEach(user -> {
						System.out.println("{");
						System.out.println("  name: " + user.getName());
						System.out.println("  email: " + user.getEmail());
						System.out.println("  password: " + user.getPassword());
						System.out.println("}");
					});

					break;

				case 3:
					sc.nextLine();
					System.out.println("Enter email for update: ");
					String email = sc.nextLine();

					UserModel model = new UserModel();
					model.setEmail(email);
					// UserModel updated = ServiceHelper.UserRepository.updateUser(model);
					try {

						if (!ServiceHelper.UserRepository.isUserPresent(model)) {
							throw new UserNotFoundException(email); // inside try → valid
						}

						System.out.println(
								"--------------------------- What do you want to update -------------------------");
						System.out.println("1 - Update Username");
						System.out.println("2 - Update Password");
						System.out.println("3 - Update Both");
						System.out.print("Enter your choice: ");
						int up = sc.nextInt();
						sc.nextLine();

						switch (up) {
						case 1:
							System.out.print("Enter new username: ");
							String userName = sc.nextLine();
							model.setName(userName);
							break;

						case 2:
							System.out.print("Enter new password: ");
							String password = sc.nextLine();
							model.setPassword(password);
							break;

						case 3:
							sc.nextLine();
							System.out.print("Enter new username: ");
							userName = sc.nextLine();
							System.out.print("Enter new password: ");
							password = sc.nextLine();
							model.setName(userName);
							model.setPassword(password);
							break;

						default:
							System.out.println("Invalid option");
							continue;
						}

						UserModel updated = ServiceHelper.UserRepository.updateUser(model);
						System.out.println(updated != null ? "User updated successfully!" : "User update failed!");
					} catch (UserNotFoundException ex) {
						System.out.println(ex.getErrorMsg());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;

				case 4:
					sc.nextLine();
					System.out.println("Delete user From email");
					String mail = sc.nextLine();
					Boolean b = ServiceHelper.UserRepository.isDeleteUser(mail);
					if (b) {
						System.out.println("user Deleted Successfully...");
					} else {
						System.out.println("user Not Present Invalid Gmail ID...");
					}
					break;
				case 5:
					try {
						sc.nextLine();
						System.out.println("Enter Email for see history");
						String e = sc.nextLine();
						UserModel m = new UserModel();
						m.setEmail(e);
						boolean o = ServiceHelper.UserRepository.isUserPresent(m);
						if (!o) {
							throw new UserNotFoundException(e);
						}
						UserHistoryRepo userHistoryRepo = new UserHistoryRepoImpl();
						List<HistoryModel> l = userHistoryRepo.getUserHistory(e);
						if (l.isEmpty()) {
							System.out.println("user not have any medical history");
							break;
						}
						l.forEach(user -> {
							System.out.println("{");
							System.out.println("user gmail id " + user.getEmail());
							System.out.println("user desiease is " + user.getPredictedDisease());
							System.out.println("user selected symtoms " + user.getUserSymtoms());
							System.out.println("confidance of desease " + user.getConfidence());
							System.out.println("date of verification " + user.getDate());
							System.out.println("}");
						});

					} catch (UserNotFoundException u) {
						System.out.println(u.getErrorMsg());
					} catch (Exception ex) {
						System.out.println(ex);
					}
					break;
				case 6:
					UserHistoryRepo user = new UserHistoryRepoImpl();
					List<HistoryModel> s = user.getAllUsersHistory();

					if (s.isEmpty()) {
					    System.out.println("No user has any medical history yet.");
					} else {
					    s.forEach(m -> {
					        System.out.println("---------------------------------------");
					        System.out.println("User Gmail       : " + m.getEmail());
					        System.out.println("Symptoms Entered : " + m.getUserSymtoms());
					        System.out.println("Predicted Disease: " + m.getPredictedDisease());
					        System.out.println("Confidence       : " + m.getConfidence());
					        System.out.println("Date Verified    : " + m.getDate());
					        System.out.println("---------------------------------------");
					    });
					}
					break;
				case 7:
					System.out.println("Exit from used crud ");
					return;
				default:
					System.out.println("Enter envalid choice ...!");
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
		} while (true);
	}
}
