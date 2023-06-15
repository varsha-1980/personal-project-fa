package com.mindlease.fa.datatable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@NotNull
	@Min(0)
	private Integer column;
	@NotNull
	@Pattern(regexp = "(desc|asc)")
	private String dir;
}
