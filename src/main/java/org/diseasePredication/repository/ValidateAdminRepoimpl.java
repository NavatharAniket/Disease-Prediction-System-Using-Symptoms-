package org.diseasePredication.repository;

import org.diseasePredication.dbConfig.DBInitilize;
import org.diseasePredication.model.AdminLogin;

public class ValidateAdminRepoimpl extends DBInitilize implements ValidateAdmin{

	@Override
	public boolean verifyAdminLogin(AdminLogin login) {
		// TODO Auto-generated method stub
		
		try
		{
			stsmt = conn.prepareStatement("select * from admin where name=? and password=? ");
			stsmt.setString(1,login.getUserName());
			stsmt.setString(2,login.getPassword());
			
			rs=stsmt.executeQuery();
			
			if(rs.next())
			{
				return true;
			}
			
			
		}
		catch(Exception ex)
		{
			System.out.println("exception found in repository package and Validate Admin Repo impl class ");
			ex.printStackTrace();
			
		}
		return false;
	}

}
