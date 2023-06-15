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
@Table(name = "tbl_personal")
public class Personal implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PERS_SHORT")
	private String pers_short;
	
	@Column(name = "PERS_FIRSTNAME")
	private String pers_firstname;
	
	@Column(name = "PERS_SURNAME")
	private String pers_surname;
	
	@Column(name = "PERS_COMPANY")
	private String pers_company;
	
	@Column(name = "PERS_ORDER")
	private Integer pers_order;
	
	@Column(name = "PERS_EDITOR")
	private boolean pers_editor;
	
	@Column(name = "PERS_ADMIN")
	private boolean pers_admin;
	
	@Column(name = "PERS_ACTIVE")
	private boolean pers_active;
	
	@Column(name = "PERS_PHONE")
	private Long pers_phone;
	
	@Column(name = "PERS_MAIL")
	private String pers_mail;
	
	@Column(name = "PERS_SPEECH")
	private String pers_speech;
	
	@Column(name = "EXAM")
	private String exam;

}
