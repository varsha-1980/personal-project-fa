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
@Table(name = "tbl_parameter")
public class Parameter implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PAR_User")
	private String par_user;

	@Column(name = "PAR_Typ")
	private String par_typ;
	
	@Column(name = "PAR_Alpha")
	private String par_alpha;
	
	@Column(name = "PAR_DESCRIPTION")
	private String par_description;
	
	@Column(name = "PAR_LNG")
	private Long par_lng;
	
	@Column(name = "PAR_DBL")
	private Double par_dbl;
	
	@Column(name = "PAR_STR")
	private String par_str;
	
	@Column(name = "EXAM")
	private String exam;

}
