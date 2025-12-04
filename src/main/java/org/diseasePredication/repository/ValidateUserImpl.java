package org.diseasePredication.repository;

import org.diseasePredication.Exception.UserCreaditialsInvalidException;
import org.diseasePredication.dbConfig.DBInitilize;
import org.diseasePredication.model.UserModel;

public class ValidateUserImpl extends DBInitilize implements ValidateUser {

	@Override
	public boolean isUserValid(UserModel userModel) {
		// TODO Auto-generated method stub
		
		try {
			
			stsmt=conn.prepareStatement("select * from users where gmail=? and password=?");
			stsmt.setString(1, userModel.getEmail());
			stsmt.setString(2, userModel.getPassword());
			rs=stsmt.executeQuery();
			if(rs.next())
			{
				return true;
			}
			throw new UserCreaditialsInvalidException();
		}
		catch(UserCreaditialsInvalidException ex)
		{
			System.out.print(ex.getErrorMeg());
		}
		catch(Exception ex)
		{
			System.out.println("Exception at time of Validate user");
			ex.printStackTrace();
		}
		return false;
	}

	

}
