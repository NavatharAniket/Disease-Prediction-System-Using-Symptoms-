package org.diseasePredication.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.diseasePredication.model.DiseaseModel;
import org.diseasePredication.model.SymptomsModel;

public interface DiseaseRepository {
	boolean isAddDisease(DiseaseModel diseaseModel, Set<String> allSymtomsOfDisease);

	Map<String, List<String>> getAllDiseases();

	public DiseaseModel updateDisease(DiseaseModel diseaseModel, Set<String> allSymtomsOfDisease);

	public boolean isDeleteDisease(String diseaseName);

	public boolean isDiseasePresent(DiseaseModel diseaseModel);

	List<SymptomsModel> getSymptomsByDiseaseName(String diseaseName);
}
