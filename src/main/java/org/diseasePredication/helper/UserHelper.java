package org.diseasePredication.helper;

import java.util.List;
import java.util.Scanner;

import org.diseasePredication.Exception.UserNotFoundException;
import org.diseasePredication.model.UserModel;
public class UserHelper {
	public static void crudUsers(String[] args)
	{
		do
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("1 - Add New user");
			System.out.println("2 - View All User");
			System.out.println("3 - Update User using email");
			System.out.println("4 - Delete User using email");
			System.out.println("5 - enter for go admin panel ");
			int choice = sc.nextInt();
			
			switch(choice)
			{
				case 1:
					sc.nextLine();
					System.out.println("Enter user name");
					String userName = sc.nextLine();
					System.out.println("Enter user email");
					String email=sc.nextLine();
					System.out.println("Enter your password");
					String password = sc.nextLine();
					
					UserModel userModel = new UserModel();
					userModel.setName(userName);
					userModel.setPassword(password);
					userModel.setEmail(email);
					boolean result = ServiceHelper.UserRepository.isAddUser(userModel);
					if(result){
						System.out.println("user added sucessfully...");
					}else {
						System.out.println("user not added...");
					}
						break;
						
				case 2:
					List<UserModel> list = ServiceHelper.UserRepository.getAllUsers();
					list.forEach(user -> System.out.println(
					        user.getName() + "| \t"+user.getEmail()+"| \t " + user.getPassword()
					));

					break;
					
                case 3:
                	sc.nextLine();
                    System.out.println("Enter email for update: ");
                    email = sc.nextLine();

                    UserModel model = new UserModel();
                    model.setEmail(email);
                   // UserModel updated = ServiceHelper.UserRepository.updateUser(model);
                    try {
                    	
                    	if (!ServiceHelper.UserRepository.isUserPresent(model)) {
                            throw new UserNotFoundException(email);  // inside try → valid
                        }
                    	
                    System.out.println("--------------------------- What do you want to update -------------------------");
                    System.out.println("1 - Update Username");
                    System.out.println("2 - Update Password");
                    System.out.println("3 - Update Both");
                    System.out.print("Enter your choice: ");
                    int up = sc.nextInt();
                    sc.nextLine();

                    switch (up) {
                        case 1:
                            System.out.print("Enter new username: ");
                            userName = sc.nextLine();
                            model.setName(userName);
                            break;

                        case 2:
                            System.out.print("Enter new password: ");
                            password = sc.nextLine();
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
                    System.out.println(updated != null ?
                        "User updated successfully!" :
                        "User update failed!");
                    } catch (UserNotFoundException ex) {
                        System.out.println(ex.getErrorMsg());
                    }
                    catch(Exception ex)
                    {
                    	ex.printStackTrace();;
                    }
                    break;
					
				case 4:
					sc.nextLine();
					System.out.println("Delete user From email");
					String mail = sc.nextLine();
					Boolean b = ServiceHelper.UserRepository.isDeleteUser(mail);
					if(b){
						System.out.println("user Deleted Successfully...");
					}else {
						System.out.println("user Not Present Invalid Gmail ID...");
					}
					break;
				case 5:
					System.out.println("Exit from used crud ");
					System.exit(0);
				default:
					System.out.println("Enter envalid choice ...!");
			}
		}while(true);
	}
}
