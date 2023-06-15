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
@Table(name = "tbl_part_criteria")
public class PartCriteria implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PACRI_STEP")
	private String pacri_step;
	
	@Column(name = "PACRI_CLASS")
	private String pacri_class;
	
	@Column(name = "PACRI_CELLS")
	private Long pacri_cells;
	
	@Column(name = "PACRI_NAME")
	private String pacri_name;
	
	@Column(name = "PACRI_BS1")
	private Integer pacri_bs1;
	
	@Column(name = "PACRI_TEXT01")
	private String pacri_text01;
	
	@Column(name = "EXAM")
	private String exam;

}
