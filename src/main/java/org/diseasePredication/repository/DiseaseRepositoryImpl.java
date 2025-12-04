package org.diseasePredication.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.diseasePredication.Exception.DiseaseNotFoundException;
import org.diseasePredication.Exception.DublicateDiseaseFoundException;
import org.diseasePredication.dbConfig.DBInitilize;
import org.diseasePredication.model.DiseaseModel;
import org.diseasePredication.model.SymptomsModel;

public class DiseaseRepositoryImpl extends DBInitilize implements DiseaseRepository {
	List<SymptomsModel> listSymptoms;
	List<DiseaseModel> list;
	Map<String, List<String>> hm;

	@Override
	public boolean isAddDisease(DiseaseModel diseaseModel, Set<String> allSymtomsOfDisease) {
		// TODO Auto-generated method stub
		try {
			stsmt = conn.prepareStatement("insert into diseases (disease) values (?)");
			stsmt.setString(1, diseaseModel.getDiseaseName());
			int value = stsmt.executeUpdate();
			if (value > 0) {
				stsmt = conn.prepareStatement("select did from diseases where disease=?");
				stsmt.setString(1, diseaseModel.getDiseaseName());
				ResultSet r1 = stsmt.executeQuery();
				int did = 0;
				if (r1.next()) {
					did = r1.getInt("did");
				}
				for (String str : allSymtomsOfDisease) {
					stsmt = conn.prepareStatement("select sid from symptoms where symptom=?");
					stsmt.setString(1, str);
					ResultSet r2 = stsmt.executeQuery();
					int sid = 0;
					if (r2.next()) {
						sid = r2.getInt("sid");
					} else {
						System.out.println("Symptom not found: " + str);
						continue;
					}
					stsmt = conn.prepareStatement("insert into diseasesymptommapping (did,sid) values (?,?)");
					stsmt.setInt(1, did);
					stsmt.setInt(2, sid);
					int val = stsmt.executeUpdate();
					if (val > 0) {
						System.out.println("Mapping sucessefull ");
					} else {
						System.out.println("Mapping unsucessfull ");
					}
				}
				return true;
			}

			throw new DublicateDiseaseFoundException(diseaseModel.getDiseaseName());
		} catch (DublicateDiseaseFoundException ex) {
			System.out.println(ex.getErrorMsg());
		} catch (Exception ex) {
			System.out.println("Exception at time of insert Disease");
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Map<String, List<String>> getAllDiseases() {
		// TODO Auto-generated method stub
		try {
			hm = new HashMap();

			stsmt = conn.prepareStatement("select * from diseases");
			rs = stsmt.executeQuery();
			while (rs.next()) {
				List<String> listSymptoms = new ArrayList<>();
				String str = rs.getString(2);

				int did = rs.getInt(1);
				PreparedStatement st = conn.prepareStatement(
						"select s.symptom from symptoms s join diseasesymptommapping m on s.sid = m.sid where m.did = ?");
				st.setInt(1, did);
				ResultSet r = st.executeQuery();
				while (r.next()) {
					listSymptoms.add(r.getString(1));
				}

				hm.put(str, listSymptoms);
			}

		} catch (Exception ex) {
			System.out.println("Exception from at time of get all diseases");
			ex.printStackTrace();
		}

		return hm;
	}

	@Override
	public DiseaseModel updateDisease(DiseaseModel diseaseModel, Set<String> allSymtomsOfDisease) {
		// TODO Auto-generated method stub
		try {
			stsmt = conn.prepareStatement("select did from diseases where disease=?");
			stsmt.setString(1, diseaseModel.getDiseaseName());
			rs = stsmt.executeQuery();
			if (!rs.next()) {
				throw new DiseaseNotFoundException(diseaseModel.getDiseaseName());
			}

			int did = rs.getInt(1);

			Set<String> exist = new java.util.HashSet<>();

			stsmt = conn.prepareStatement(
					"select s.symptom from symptoms s join diseasesymptommapping m on s.sid = m.sid where m.did = ?");
			stsmt.setInt(1, did);
			rs = stsmt.executeQuery();
			while (rs.next()) {
				exist.add(rs.getString(1));
			}

			for (String str : allSymtomsOfDisease) {
				if (exist.contains(str)) {
					continue;
				}

				stsmt = conn.prepareStatement("select sid from symptoms where symptom=?");
				stsmt.setString(1, str);
				rs = stsmt.executeQuery();
				if (!rs.next()) {
					System.out.println("Symptom Not found ");
				}
				int sid = rs.getInt(1);

				stsmt = conn.prepareStatement("insert into diseasesymptommapping (did,sid) values (?,?)");
				stsmt.setInt(1, did);
				stsmt.setInt(2, sid);
				int value = stsmt.executeUpdate();

				if (value > 0) {
					System.out.println("Updated Sucessfully ");
				} else {
					System.out.println("not updated Sucessfully ");
				}

			}
		} catch (DiseaseNotFoundException ex) {
			System.out.println(ex.getErrorMsg());
		} catch (Exception ex) {
			System.out.print("Exception is present in uodate Method of Disease ");
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SymptomsModel> getSymptomsByDiseaseName(String diseaseName) {
		listSymptoms = new ArrayList<>();
		try {
			stsmt = conn.prepareStatement("select did from diseases where disease = ?");
			stsmt.setString(1, diseaseName);
			rs = stsmt.executeQuery();

			if (!rs.next()) {
				System.out.println("Disease not found: " + diseaseName);
				return listSymptoms; // return empty list
			}

			int did = rs.getInt(1);

			stsmt = conn.prepareStatement(
					"select s.symptom from symptoms s join diseasesymptommapping d on s.sid=d.sid where d.did=?");
			stsmt.setInt(1, did);
			rs = stsmt.executeQuery();
			while (rs.next()) {
				SymptomsModel s = new SymptomsModel();
				s.setSymtomsName(rs.getString(1));
				listSymptoms.add(s);
			}
		} catch (Exception ex) {
			System.out.println("Exception at time fetch symptoms using disease name");
			ex.printStackTrace();
		}
		return listSymptoms;
	}

	@Override
	public boolean isDeleteDisease(String diseaseName) {
		// TODO Auto-generated method stub
		try
		{
			stsmt=conn.prepareStatement("select did from diseases where disease =?");
			stsmt.setString(1, diseaseName);
			rs=stsmt.executeQuery();
			if(!rs.next())
			{
				throw new DiseaseNotFoundException(diseaseName);
			}
			stsmt=conn.prepareStatement("delete from diseases where disease = ?");
			stsmt.setString(1, diseaseName);
			int val=stsmt.executeUpdate();
			if(val>0)
			{
				System.out.println("Disease deleted from table disease");
			}
			else
			{
				System.out.println("disease not deleted from table disease");
			}
		}
		catch(DiseaseNotFoundException d)
		{
			System.out.println(d.getErrorMsg());
		}
		catch(Exception ex)
		{
			System.out.println("Exception from at time of delete disease");
		}
		return false;
	}

	@Override
	public boolean isDiseasePresent(DiseaseModel diseaseModel) {
		// TODO Auto-generated method stub
		try {
			stsmt = conn.prepareStatement("select disease from diseases where disease = ?");
			stsmt.setString(1, diseaseModel.getDiseaseName());
			rs = stsmt.executeQuery();

		
			return rs.next();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
