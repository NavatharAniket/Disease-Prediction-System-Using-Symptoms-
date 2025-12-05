package org.diseasePredication.repository;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.diseasePredication.dbConfig.DBInitilize;

public class ArefFileGeneratorImpl extends DBInitilize implements ArefFileGenerator {

	@Override
	public void generateARFF() {
		// TODO Auto-generated method stub
		try {
			File f = new File("");
			String rootPath = f.getAbsolutePath() + "\\src\\main\\resources\\disease_dataset.arff";

			FileWriter fw = new FileWriter(rootPath);
			fw.write("@relation disease_prediction\n\n");
			fw.write("@attribute symptoms string\n");

			stsmt = conn.prepareStatement("select distinct disease from mldataset");
			rs = stsmt.executeQuery();
			List<String> diseases = new ArrayList<>();

			while (rs.next()) {
				String d = rs.getString("disease").replace("'", "\\'");
				diseases.add("'" + d + "'");
			}

			fw.write("@attribute disease {" + String.join(",", diseases) + "}\n\n");
			fw.write("@data\n");

			stsmt = conn.prepareStatement("select symptoms, disease from mldataset");
			rs = stsmt.executeQuery();

			while (rs.next()) {
				String symptoms = rs.getString("symptoms").replace("\"", "\\\"");
				String disease = rs.getString("disease").replace("'", "\\'");
				fw.write("\"" + symptoms + "\", " + disease + "\n");
			}

			fw.close();
			System.out.println("ARFF file created successfully.");
		} catch (Exception ex) {
			System.out.println("Exception at time of ARFF file creation");
			ex.printStackTrace();
		}
	}

}
