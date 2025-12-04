package org.diseasePredication.repository;

import java.util.ArrayList;
import java.util.List;

import org.diseasePredication.Exception.DublicateUserFoundException;
import org.diseasePredication.Exception.UserNotFoundException;
import org.diseasePredication.dbConfig.DBInitilize;
import org.diseasePredication.model.UserModel;

public class UserRepositoryImpl extends DBInitilize implements UserRepository {
	List<UserModel> list;

	@Override
	public boolean isAddUser(UserModel user) {
		// TODO Auto-generated method stub

		try {
			
			stsmt=conn.prepareStatement("select username from users where gmail =?");
			stsmt.setString(1, user.getEmail());
			rs=stsmt.executeQuery();
			while(rs.next())
			{
				throw new DublicateUserFoundException(user.getEmail());
			}
			stsmt = conn.prepareStatement("insert into users(username,password,gmail) values (?,?,?)");
			stsmt.setString(1, user.getName());
			stsmt.setString(2, user.getPassword());
			stsmt.setString(3, user.getEmail());
			int value = stsmt.executeUpdate();

			if (value > 0) {
				return true;
			}

			throw new DublicateUserFoundException(user.getEmail());

		} catch (DublicateUserFoundException ex) {
			System.out.println("Exception " + ex.getErrorMsg());
		} catch (Exception ex) {
			System.out.println("Exception from UserRepository at insertion time ");
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<UserModel> getAllUsers() {
		// TODO Auto-generated method stub
		try {
			list = new ArrayList<UserModel>();

			stsmt = conn.prepareStatement("select * from users");
			rs = stsmt.executeQuery();
			while (rs.next()) {
				UserModel userModel = new UserModel();
				userModel.setName(rs.getString(2));
				userModel.setPassword(rs.getString(3));
				userModel.setEmail(rs.getString(4));
				list.add(userModel);
			}
		} catch (Exception ex) {
			System.out.println("Exception ocure at gerAllUsers method in UserRepositoryImpl");
			ex.printStackTrace();
		}
		return list;
	}
	
	@Override
	public boolean isUserPresent(UserModel userModel) {
	    try {
	        stsmt = conn.prepareStatement("SELECT gmail FROM users WHERE gmail = ?");
	        stsmt.setString(1, userModel.getEmail());
	        rs = stsmt.executeQuery();

	        // If record exists → return true
	        return rs.next();
	    }
	    catch (Exception ex) {
	        ex.printStackTrace();
	        return false;
	    }
	}

	@Override
	public UserModel updateUser(UserModel userModel) {
		
		try {
			
			
				//case for handling when user name want to update 
		     if (userModel.getName() != null && userModel.getPassword() == null) {

				stsmt = conn.prepareStatement("update users set username = ? where gmail = ?");

				stsmt.setString(1, userModel.getName());
				stsmt.setString(2, userModel.getEmail());
			}

			// case when changing password
			else if (userModel.getName() == null && userModel.getPassword() != null) {

				stsmt = conn.prepareStatement("update users set password = ? where gmail = ?");

				stsmt.setString(1, userModel.getPassword());
				stsmt.setString(2, userModel.getEmail());
			}

			// case for handling both user name and password
			else if (userModel.getName() != null && userModel.getPassword() != null) {

				stsmt = conn.prepareStatement("update users set username = ?, password = ? where gmail = ?");

				stsmt.setString(1, userModel.getName());
				stsmt.setString(2, userModel.getPassword());
				stsmt.setString(3, userModel.getEmail());
			}

			// case both are not provided 
			else {
				return null;
			}

			int value = stsmt.executeUpdate();

			// if user not found then throws this exception
			if (value == 0) {
				throw new UserNotFoundException(userModel.getEmail());
			}

			return userModel;

		} catch (UserNotFoundException ex) {
			System.out.println("Exception: " + ex.getErrorMsg());
		} catch (Exception e) {
			System.out.println("Error in updateUser(): " + e);
		}

		return null;
	}

	@Override
	public boolean isDeleteUser(String email) {
		// TODO Auto-generated method stub
		try {
			stsmt = conn.prepareStatement("Delete from users where gmail = ?");
			stsmt.setString(1, email);
			int value = stsmt.executeUpdate();
			if (value > 0) {
				return true;
			}
			throw new UserNotFoundException(email);
		} catch (UserNotFoundException ex) {
			System.out.println("Exception :-" + ex.getErrorMsg());
		} catch (Exception e) {
			System.out.println("Exception at time of User Deletion ");
			e.printStackTrace();
		}
		return false;
	}

}
