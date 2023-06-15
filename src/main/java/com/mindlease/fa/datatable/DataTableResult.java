package com.mindlease.fa.datatable;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class DataTableResult<T> {
	
	private int draw;;
	private long recordsTotal = 0L;
	private long recordsFiltered = 0L;
	private List<T> data = Collections.emptyList();
	private String error;

}
