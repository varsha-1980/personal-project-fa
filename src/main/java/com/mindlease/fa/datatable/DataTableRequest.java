package com.mindlease.fa.datatable;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DataTableRequest {

	@NotNull
	@Min(0)
	private Integer draw = 1;

	@NotNull
	@Min(0)
	private Integer start = 0;

	@NotNull
	@Min(-1)
	private Integer length = 10;

	@NotNull
	private Search search;

	@NotEmpty
	private List<Order> order;

	@NotEmpty
	private List<Column> columns;
	
	private Map<String, String> externalFilter;

}
