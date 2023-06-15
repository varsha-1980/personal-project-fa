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
@Table(name = "tbl_change_tracking")
public class ChangeTracking implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EDIT_ID")
	private Long id;
	
	@Column(name = "EDIT_VERSION")
	private String version;
	
	@Column(name = "EDIT_DATE")
	private String date;
	
	@Column(name = "EDIT_TEXT")
	private String name;
	
	@Column(name = "EXAM")
	private String exam;
}
