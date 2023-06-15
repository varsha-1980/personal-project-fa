package com.mindlease.fa.datatable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column {

	@NotBlank
	private String data;
	private String name;
	@NotNull
	private Boolean searchable;
	@NotNull
	private Boolean orderable;
	@NotNull
	private Search search;

}
