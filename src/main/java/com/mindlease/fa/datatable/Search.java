package com.mindlease.fa.datatable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
	@NotNull
	private String value;
	@NotNull
	private Boolean regex;
//	@NotNull
//	private String columnName;
}
