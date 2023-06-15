package com.mindlease.fa.model;

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
@Table(name = "tbl_methode_x")
public class MethodX implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "METHX_ID")
	private Long id;
	
	@Column(name = "METHX_DBS_ID")
	private Long order_id;
	
	@Column(name = "METHX_METHOD_ID")
	private Long methodId;
	
	
	@Column(name = "METHX_METHOD")
	private String name;
	
	@Column(name = "METHX_METHOD_DE")
	private String nameDe;
	
	@Column(name = "METHX_GENERAL")
	private boolean general;
	
	@Column(name = "METHX_PACK")
	private boolean pack;
	
	@Column(name = "METHX_WFR")
	private boolean wfr;
	
	@Column(name = "EXAM")
	private String exam;
}
