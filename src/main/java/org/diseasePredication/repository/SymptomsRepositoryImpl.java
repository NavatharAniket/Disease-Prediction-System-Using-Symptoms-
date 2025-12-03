package org.diseasePredication.repository;

import java.util.ArrayList;
import java.util.List;

import org.diseasePredication.Exception.DublicateSymptomsFoundException;
import org.diseasePredication.Exception.SymptomsNotFoundException;
import org.diseasePredication.dbConfig.DBInitilize;
import org.diseasePredication.model.SymptomsModel;

public class SymptomsRepositoryImpl extends DBInitilize implements SymptomsRepository {
	List<SymptomsModel> list;

	@Override
	public boolean isAddSymptoms(SymptomsModel symptomsModel) {
		// TODO Auto-generated method stub
		try {

			stsmt = conn.prepareStatement("select symptom from symptoms where symptom = ?");
			stsmt.setString(1, symptomsModel.getSymtomsName());
			rs = stsmt.executeQuery();

			if (rs.next()) {
				throw new DublicateSymptomsFoundException(symptomsModel.getSymtomsName());
			}

			stsmt = conn.prepareStatement("insert into symptoms (symptom) values (?)");
			stsmt.setString(1, symptomsModel.getSymtomsName());
			int value = stsmt.executeUpdate();

			if (value > 0) {
				return true;
			}

			throw new DublicateSymptomsFoundException(symptomsModel.getSymtomsName());
		} catch (DublicateSymptomsFoundException d) {
			System.out.println("Eception for dublicate entry");
			System.out.println(d.getErrorMsg());
		} catch (Exception ex) {
			System.out.println("Exception at time of add Symptoms");
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<SymptomsModel> getAllSymptoms() {
		// TODO Auto-generated method stub
		try {
			list = new ArrayList<SymptomsModel>();

			stsmt = conn.prepareStatement("select * from symptoms");
			rs = stsmt.executeQuery();
			while (rs.next()) {
				SymptomsModel symptomsModel = new SymptomsModel();
				symptomsModel.setSymtomsName(rs.getString(2));
				list.add(symptomsModel);
			}
		} catch (Exception ex) {
			System.out.println("Exception ocure at gerSymptoms list  method in Symptoms RepositoryImpl");
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean isDeleteSymptoms(String symptomsName) {
		// TODO Auto-generated method stub
		try {
			stsmt = conn.prepareStatement("Delete from symptoms where symptom=?");
			stsmt.setString(1, symptomsName);
			int value = stsmt.executeUpdate();

			if (value > 0) {
				return true;
			}

			throw new SymptomsNotFoundException(symptomsName);
		} catch (SymptomsNotFoundException s) {
			System.out.println(s.getErrorMsg());
		} catch (Exception ex) {
			System.out.println("Exception from DElete Symptoms ");
			ex.printStackTrace();
		}
		return false;
	}

}
