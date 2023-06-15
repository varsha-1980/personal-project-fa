package com.mindlease.fa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tbl_company")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Company implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "NAME_DE")
	private String nameDe;

	@Column(name = "F_CANCEL", nullable = false)
	private String FCancel;

	@Column(name = "F_ACTIVE", nullable = false)
	private String FActive;

	@Version
	@Column(name = "VERSION", nullable = false)
	private int version;

	@Column(name = "CREATED_BY", nullable = false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 0)
	@NotNull
	private Date createdDate;

	@Column(name = "MODIFIED_BY", nullable = false)
	private Integer modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIED_DATE", nullable = false, length = 0)
	@NotNull
	private Date modifiedDate;
}
