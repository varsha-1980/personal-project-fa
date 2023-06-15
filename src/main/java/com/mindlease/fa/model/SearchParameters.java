package com.mindlease.fa.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class SearchParameters implements Serializable {
	private String sMaterial;
	private String sCustomername;
	private String sPriority;
	private String sStatus;
	private String sElectricalError;
	private String sFailureMode;
	private String sArchWaferBox;
	private String sArchPolyBox;
}
