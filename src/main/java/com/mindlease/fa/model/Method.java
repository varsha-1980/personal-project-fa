package com.mindlease.fa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tbl_methode")
public class Method implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "METH_ID")
	private Long id;
	
	@Column(name = "METH_METHOD")
	private String name;
	
	@Column(name = "METH_METHOD_DE")
	private String nameDe;
	
	@Column(name = "METH_GENERAL")
	private boolean general;
	
	@Column(name = "METH_PACK")
	private boolean pack;
	
	@Column(name = "METH_WFR")
	private boolean wfr;
	
	@Column(name = "EXAM")
	private String exam;
}
