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
@Table(name = "tbl_archive")
public class Archive implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ARCH_ID")
	private Long id;
	
	@Column(name = "ARCH_TEXT")
	private String name;
	
	@Column(name = "ARCH_TEXT_DE")
	private String nameDe;
	
	@Column(name = "EXAM")
	private String exam;
	
}
