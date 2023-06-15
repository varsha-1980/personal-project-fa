package com.mindlease.fa.dto;

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
import com.mindlease.fa.model.Part;
import com.mindlease.fa.util.TabValues;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OrderDetailsDto implements java.io.Serializable {
	private Long id;

//	@Column(name = "DBS_FA_DATE")
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//	@Temporal(TemporalType.DATE)
	private Date dbs_fa_date;

	//(name = "DBS_AG_NAME")
	private String dbs_ag_name;

	//(name = "DBS_MATERIAL")
	private String dbs_material;

	//(name = "DBS_LOTID")
	private String dbs_lotid;

	//(name = "DBS_WFR")
	private String dbs_wfr;

	//(name = "DBS_PART")
	private String dbs_part;

	//(name = "DBS_PRIO")
	private String dbs_prio;

	//(name = "DBS_LOCATION")
	private String dbs_location;

	//(name = "DBS_STEP")
	private String dbs_step;

	//(name = "DBS_STATUS")
	private String dbs_status;

	//(name = "DBS_ELEE")
	private String dbs_elee;

	//(name = "DBS_FAMO")
	private String dbs_famo;

	//(name = "DBS_CAR")
	private String dbs_car;

	//(name = "DBS_FA_REASON")
	private String dbs_fa_reason;

	//(name = "DBS_FA_DESCR")
	private String dbs_fa_descr;

	//(name = "DBS_LINK")
	private String dbs_link;

	//(name = "DBS_METHOD")
	private String dbs_method;

	//(name = "DBS_POS_XCMAP")
	private boolean dbs_pos_xcmap;

	//(name = "DBS_POS_TEXT")
	private String dbs_pos_text;

	//(name = "DBS_POS_01")
	private boolean dbs_pos_01;

	//(name = "DBS_POS_02")
	private boolean dbs_pos_02;

	//(name = "DBS_POS_03")
	private boolean dbs_pos_03;

	//(name = "DBS_POS_04")
	private boolean dbs_pos_04;

	//(name = "DBS_POS_05")
	private boolean dbs_pos_05;

	//(name = "DBS_POS_06")
	private boolean dbs_pos_06;

	//(name = "DBS_POS_07")
	private boolean dbs_pos_07;

	//(name = "DBS_POS_08")
	private boolean dbs_pos_08;

	//(name = "DBS_POS_09")
	private boolean dbs_pos_09;

	//(name = "DBS_POS_10")
	private boolean dbs_pos_10;

	//(name = "DBS_POS_11")
	private boolean dbs_pos_11;

	//(name = "DBS_POS_12")
	private boolean dbs_pos_12;

	//(name = "DBS_POS_13")
	private boolean dbs_pos_13;

	//(name = "DBS_POS_14")
	private boolean dbs_pos_14;

	//(name = "DBS_POS_15")
	private boolean dbs_pos_15;

	//(name = "DBS_POS_16")
	private boolean dbs_pos_16;

	//(name = "DBS_POS_17")
	private boolean dbs_pos_17;

	//(name = "DBS_POS_18")
	private boolean dbs_pos_18;

	//(name = "DBS_POS_19")
	private boolean dbs_pos_19;

	//(name = "DBS_POS_20")
	private boolean dbs_pos_20;

	//(name = "DBS_POS_21")
	private boolean dbs_pos_21;

	//(name = "DBS_REMAIN")
	private String dbs_remain;

	//(name = "DBS_FA_NAME")
	private String dbs_fa_name;

	//(name = "DBS_FA_TEXT")
	private String dbs_fa_text;

	//(name = "DBS_FA_START")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dbs_fa_start;

	//(name = "DBS_FA_STOP")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dbs_fa_stop;

	//(name = "DBS_FA_ARCHIV_WF")
	private String dbs_fa_archiv_wf;

	//(name = "DBS_FA_ARCHIV_PS")
	private String dbs_fa_archiv_ps;

	//(name = "DBS_RES_NAME")
	private String dbs_res_name;

	//(name = "DBS_RES_TEXT")
	private String dbs_res_text;

	//(name = "DBS_RES_START")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dbs_res_start;

	//(name = "DBS_RES_STOP")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dbs_res_stop;

	//(name = "DBS_COST")
	private BigDecimal dbs_cost;

	//(name = "DBS_WAIT_TIME1")
	private Float dbs_wait_time1;

	//(name = "DBS_FA_TIME")
	private Float dbs_fa_time;

	//(name = "DBS_WAIT_TIME2")
	private Float dbs_wait_time2;

	//(name = "DBS_RES_TIME")
	private Float dbs_res_time;

	//(name = "DBS_CPL_TIME")
	private Float dbs_cpl_time;

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

	
	private String dbs_ag_name_temp;

	
	private String dbs_prio_temp;

	
	private String dbs_material_temp;

	
	private String dbs_lotid_temp;

	
	private String dbs_part_temp;

	
	private String dbs_fa_reason_temp;

	
	private String dbs_elee_temp;

	
	private String dbs_famo_temp;

	
	private String dbs_location_temp;

	
	private String dbs_status_temp;

	
	private String dbs_method_temp;
}
