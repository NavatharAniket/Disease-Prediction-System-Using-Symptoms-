package org.diseasePredication.repository;

import java.util.ArrayList;
import java.util.List;

import org.diseasePredication.Exception.UserNotFoundException;
import org.diseasePredication.dbConfig.DBInitilize;
import org.diseasePredication.model.HistoryModel;

public class UserHistoryRepoImpl extends DBInitilize implements UserHistoryRepo {

	@Override
	public boolean storeUserHistory(HistoryModel history) {
		// TODO Auto-generated method stub
		try {

			stsmt = conn.prepareStatement("select uid from users where gmail=?");
			stsmt.setString(1, history.getEmail());
			rs = stsmt.executeQuery();
			if (rs.next()) {
				int uid = rs.getInt(1);

				stsmt = conn.prepareStatement(
						"insert into prediction_history (uid,input_symptoms,predicted_disease,confidence) values (?,?,?,?)");
				stsmt.setInt(1, uid);
				stsmt.setString(2, history.getUserSymtoms());
				stsmt.setString(3, history.getPredictedDisease());
				stsmt.setDouble(4, history.getConfidence());

				int val = stsmt.executeUpdate();
				if (val > 0) {
					return true;
				}

			}

		} catch (Exception ex) {
			System.out.println("Exception at time store user history");
			System.out.println(ex);
		}
		return false;
	}

	@Override
	public List<HistoryModel> getUserHistory(String email) {
		// TODO Auto-generated method stub
		try {
			List<HistoryModel> list = new ArrayList<>();
			stsmt = conn.prepareStatement("select uid from users where gmail=?");
			stsmt.setString(1, email);
			rs = stsmt.executeQuery();
			if (rs.next()) {
				int uid = rs.getInt(1);
				stsmt = conn
						.prepareStatement("select * from prediction_history p join users u on p.uid=u.uid where p.uid=?");
				stsmt.setInt(1, uid);
				rs = stsmt.executeQuery();
				while (rs.next()) {
					HistoryModel model = new HistoryModel();
					model.setEmail(email);
					model.setUserSymtoms(rs.getString(3));
					model.setPredictedDisease(rs.getString(4));
					model.setConfidence(rs.getDouble(5));
					model.setDate(rs.getDate(6).toString());

					list.add(model);
				}

				return list;
			}
			throw new UserNotFoundException(email);
		} catch (UserNotFoundException ex) {
			System.out.println(ex.getErrorMsg());
		} catch (Exception ex) {
			System.out.println("exception at time get user history ");
			System.out.println(ex);
		}
		return null;
	}

	@Override
	public List<HistoryModel> getAllUsersHistory() {

	    List<HistoryModel> list = new ArrayList<>();

	    try {
	        // Fetch ALL users' history (no filtering)
	        stsmt = conn.prepareStatement("select p.input_symptoms, p.predicted_disease, p.confidence, p.date_time, u.gmail from prediction_history p join users u on p.uid = u.uid order by p.pid desc"
	        );

	        rs = stsmt.executeQuery();

	        while (rs.next()) {
	            HistoryModel model = new HistoryModel();
	            model.setEmail(rs.getString("gmail"));
	            model.setUserSymtoms(rs.getString("input_symptoms"));
	            model.setPredictedDisease(rs.getString("predicted_disease"));
	            model.setConfidence(rs.getDouble("confidence"));
	            model.setDate(rs.getDate("p.date_time").toString());

	            list.add(model);
	        }

	        return list;

	    } catch (Exception ex) {
	        System.out.println("Exception at time fetching all user history");
	        System.out.println(ex);
	    }

	    return new ArrayList<>(); // Never return null
	}



}
