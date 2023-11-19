package com.mindlease.fa.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindlease.fa.util.TabValues;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tbl_Order_Details")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OrderDetails implements java.io.Serializable {
	//(id, dbs_pos_01, dbs_pos_02, dbs_pos_03, dbs_pos_04, dbs_pos_05, dbs_pos_06, dbs_pos_07, dbs_pos_08, dbs_pos_09, dbs_pos_10, dbs_pos_11, dbs_pos_12, dbs_pos_13, dbs_pos_14, dbs_pos_15, dbs_pos_16, dbs_pos_17, dbs_pos_18, dbs_pos_19, dbs_pos_20, dbs_pos_21, dbs_pos_text, dbs_fa_reason, dbs_car, dbs_cost, dbs_step, dbs_res_stop, date_dbs_res_start, dbs_fa_text, dbs_res_start, dbs_fa_descr, dbs_lotid, dbs_fa_date, id, dbs_fa_name, dbs_fa_start, dbs_fa_stop, dbs_res_text, dbs_pos_xcmap, dbs_res_time, dbs_wfr, dbs_wait_time1, dbs_wait_time2, dbs_remain, dbs_fa_archiv_ps, company_id, dbs_cost, dbs_ag_name, dbs_elee, dbs_famo, dbs_location, dbs_material, dbs_part, dbs_prio, dbs_fa_textr, dbs_status, dbs_res_name, dbs_fa_archiv_wf, dbs_link)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DBS_ID")
	private Long id;

	@Column(name = "DBS_FA_DATE")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dbs_fa_date;

	@Column(name = "DBS_AG_NAME")
	private String dbs_ag_name;

	@Column(name = "DBS_MATERIAL")
	private String dbs_material;

	@Column(name = "DBS_LOTID")
	private String dbs_lotid;

	@Column(name = "DBS_WFR")
	private String dbs_wfr;

	@Column(name = "DBS_PART")
	private String dbs_part;

	@Column(name = "DBS_PRIO")
	private String dbs_prio;

	@Column(name = "DBS_LOCATION")
	private String dbs_location;

	@Column(name = "DBS_STEP")
	private String dbs_step;

	@Column(name = "DBS_STATUS")
	private String dbs_status;

	@Column(name = "DBS_ELEE")
	private String dbs_elee;

	@Column(name = "DBS_FAMO")
	private String dbs_famo;

	@Column(name = "DBS_CAR")
	private String dbs_car;

	@Column(name = "DBS_FA_REASON")
	private String dbs_fa_reason;

	@Column(name = "DBS_FA_DESCR")
	private String dbs_fa_descr;

	@Column(name = "DBS_LINK")
	private String dbs_link;

	@Column(name = "DBS_METHOD")
	private String dbs_method;

	@Column(name = "DBS_POS_XCMAP")
	private boolean dbs_pos_xcmap;

	@Column(name = "DBS_POS_TEXT")
	private String dbs_pos_text;

	@Column(name = "DBS_POS_01")
	private boolean dbs_pos_01;

	@Column(name = "DBS_POS_02")
	private boolean dbs_pos_02;

	@Column(name = "DBS_POS_03")
	private boolean dbs_pos_03;

	@Column(name = "DBS_POS_04")
	private boolean dbs_pos_04;

	@Column(name = "DBS_POS_05")
	private boolean dbs_pos_05;

	@Column(name = "DBS_POS_06")
	private boolean dbs_pos_06;

	@Column(name = "DBS_POS_07")
	private boolean dbs_pos_07;

	@Column(name = "DBS_POS_08")
	private boolean dbs_pos_08;

	@Column(name = "DBS_POS_09")
	private boolean dbs_pos_09;

	@Column(name = "DBS_POS_10")
	private boolean dbs_pos_10;

	@Column(name = "DBS_POS_11")
	private boolean dbs_pos_11;

	@Column(name = "DBS_POS_12")
	private boolean dbs_pos_12;

	@Column(name = "DBS_POS_13")
	private boolean dbs_pos_13;

	@Column(name = "DBS_POS_14")
	private boolean dbs_pos_14;

	@Column(name = "DBS_POS_15")
	private boolean dbs_pos_15;

	@Column(name = "DBS_POS_16")
	private boolean dbs_pos_16;

	@Column(name = "DBS_POS_17")
	private boolean dbs_pos_17;

	@Column(name = "DBS_POS_18")
	private boolean dbs_pos_18;

	@Column(name = "DBS_POS_19")
	private boolean dbs_pos_19;

	@Column(name = "DBS_POS_20")
	private boolean dbs_pos_20;

	@Column(name = "DBS_POS_21")
	private boolean dbs_pos_21;
	
	@Column(name = "DBS_REMAIN")
	private String dbs_remain;
	
	@Column(name = "DBS_FA_NAME")
	private String dbs_fa_name;

	@Column(name = "DBS_FA_TEXT")
	private String dbs_fa_text;

	@Column(name = "DBS_FA_START")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dbs_fa_start;

	@Column(name = "DBS_FA_STOP")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dbs_fa_stop;

	@Column(name = "DBS_FA_ARCHIV_WF")
	private String dbs_fa_archiv_wf;

	@Column(name = "DBS_FA_ARCHIV_PS")
	private String dbs_fa_archiv_ps;

	@Column(name = "DBS_RES_NAME")
	private String dbs_res_name;

	@Column(name = "DBS_RES_TEXT")
	private String dbs_res_text;

	@Column(name = "DBS_RES_START")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dbs_res_start;

	@Column(name = "DBS_RES_STOP")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dbs_res_stop;

	@Column(name = "DBS_COST")
	private BigDecimal dbs_cost;

	@Column(name = "DBS_WAIT_TIME1")
	private Float dbs_wait_time1;

	@Column(name = "DBS_FA_TIME")
	private Float dbs_fa_time;
	
	@Column(name = "DBS_WAIT_TIME2")
	private Float dbs_wait_time2;

	@Column(name = "DBS_RES_TIME")
	private Float dbs_res_time;

	@Column(name = "DBS_CPL_TIME")
	private Float dbs_cpl_time;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;


	/** 4. Analysis Methods tab related data */

	@Transient
	private List<Method> generalInvestigationMethods;
	@Transient
	private List<Method> housedExaminationMethods;
	@Transient
	private List<Method> wafersExaminationMethods;

	
	@Transient
	private TabValues currentTab;
	@Transient
	private TabValues previousTab;
	@Transient
	private TabValues nextTab;

	@Transient
	private TabValues tab;

	@Transient
	private String action;

	@Transient
	private String ln;

	
	@Transient 
	private Part part;

	@Transient
	private String dbs_ag_name_temp;
	
	@Transient
	private String dbs_prio_temp;
	
	@Transient
	private String dbs_material_temp;
	
	@Transient
	private String dbs_lotid_temp;
	
	@Transient
	private String dbs_part_temp;
	
	@Transient
	private String dbs_fa_reason_temp;
	
	@Transient
	private String dbs_elee_temp;
	
	@Transient
	private String dbs_famo_temp;

	@Transient
	private String dbs_location_temp;

	@Transient
	private String dbs_status_temp;

	@Transient
	private String dbs_method_temp;

	@Transient
	private String createOrEdit;
}
