package com.mindlease.fa.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the tbl_samples_remain database table.
 * 
 */

@NoArgsConstructor
@Data
@Entity
@Table(name="tbl_samples_remain")
@NamedQuery(name="SamplesRemain.findAll", query="SELECT s FROM SamplesRemain s")
public class SamplesRemain implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SAMPLR_ID")
	private Long id;
	
	@Column(name = "SAMPLR_TEXT")
	private String name;
	
	
	@Column(name = "SAMPLR_TEXT_DE")
	private String nameDe;
	
	@Column(name = "EXAM")
	private String exam;

}