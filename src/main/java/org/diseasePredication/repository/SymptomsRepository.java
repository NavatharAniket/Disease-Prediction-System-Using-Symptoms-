package org.diseasePredication.repository;

import java.util.List;

import org.diseasePredication.model.SymptomsModel;

public interface SymptomsRepository {
	boolean isAddSymptoms(SymptomsModel symptomsModel);

	public List<SymptomsModel> getAllSymptoms();

	public boolean isDeleteSymptoms(String symptomsName);
}
