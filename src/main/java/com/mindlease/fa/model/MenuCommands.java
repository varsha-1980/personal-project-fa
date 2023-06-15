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
@Table(name = "tbl_menu_commands")
public class MenuCommands implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MenCom_No")
	private String menbef_no;
	
	@Column(name = "MenCom_Description")
	private String menbef_descrip;
	
	@Column(name = "MenCom_Exam")
	private String menbef_exam;
	
	@Column(name = "EXAM")
	private String exam;
}
