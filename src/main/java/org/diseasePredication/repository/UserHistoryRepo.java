package org.diseasePredication.repository;

import org.diseasePredication.model.HistoryModel;

public interface UserHistoryRepo {
	public boolean storeUserHistory(HistoryModel history);
}
