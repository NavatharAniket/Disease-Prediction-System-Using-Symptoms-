package org.diseasePredication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryModel {
	String Email;
	String userSymtoms;
	String predictedDisease;
	double confidence;
}
