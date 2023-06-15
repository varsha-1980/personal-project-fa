package com.mindlease.fa.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tbl_costs")
public class Costs implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CST_ANALYSIS")
	private String cst_analysis;

	@Column(name = "CST_COMPANY")
	private String cst_company;

	@Column(name = "CST_PRICE")
	private BigDecimal cst_price;

	@Column(name = "EXAM")
	private String exam;
}
