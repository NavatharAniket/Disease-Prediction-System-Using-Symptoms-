package org.diseasePredication.repository;

import java.util.List;

import org.diseasePredication.model.HistoryModel;

public interface UserHistoryRepo {
	public boolean storeUserHistory(HistoryModel history);

	public List<HistoryModel> getUserHistory(String email);

	public List<HistoryModel> getAllUsersHistory();
}
