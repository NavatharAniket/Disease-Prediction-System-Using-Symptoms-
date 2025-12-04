package org.diseasePredication.repository;

import org.diseasePredication.dbConfig.DBInitilize;
import org.diseasePredication.model.HistoryModel;

public class UserHistoryRepoImpl extends DBInitilize implements UserHistoryRepo {

	@Override
	public boolean storeUserHistory(HistoryModel history) {
		// TODO Auto-generated method stub
		try
		{
			
			stsmt=conn.prepareStatement("select uid from users where gmail=?");
			stsmt.setString(1, history.getEmail());
			rs=stsmt.executeQuery();
			if(rs.next())
			{
				int uid=rs.getInt(1);
				
				stsmt=conn.prepareStatement("insert into prediction_history (uid,input_symptoms,predicted_disease,confidence) values (?,?,?,?)");
				stsmt.setInt(1, uid);
				stsmt.setString(2, history.getUserSymtoms());
				stsmt.setString(3, history.getPredictedDisease());
				stsmt.setDouble(4, history.getConfidence());
				
				int val=stsmt.executeUpdate();
				if(val>0)
				{
					return true;
				}
				
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception at time store user history");
			ex.printStackTrace();
		}
		return false;
	}

}
