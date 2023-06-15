package com.mindlease.fa.model;

import java.math.BigDecimal;
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
@Table(name = "tbl_part")
public class Part implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PART_NAME")
	private String name;
	
	@Column(name = "PART_NAME_DE")
	private String nameDe;

	@Column(name="PART_VERSION")
	private String part_version;

	@Column(name="PART_CHANNEL_CAT01")
	private String part_channel;

	@Column(name="PART_BDV_CAT02")
	private String part_bdv;

	@Column(name="PART_SUBSTR_CAT03")
	private String part_substr;
	
	@Column(name="PART_GATEOXIDE_CAT07")
	private String part_gateoxide;
	
	@Column(name="PART_PITCH_CAT08")
	private String part_pitch;
	
	@Column(name="PART_METAL_CAT11")
	private String part_metal;
	
	@Column(name="PART_REL_CAT12")
	private String part_rel;
	
	@Column(name="PART_WFR_THICKNESS_CAT15")
	private String part_wfr_thick;
	
	@Column(name="PART_TRENCH_CAT19")
	private String part_trench;
	
	@Column(name="PART_TR_CALC_CAT19")
	private String part_calc;
	
	@Column(name="PART_CLASS_CAT20")
	private String part_class;
	
	@Column(name="PART_CELLS_CAT21")
	private String part_cells;
	
	@Column(name="PART_PACK_CAT22")
	private String part_pack;
	
	@Column(name="PART_PASS_CAT31")
	private String part_pass;
	
	@Column(name="PART_ESD_CAT32")
	private String part_esd;
	
	@Column(name="PART_NUMBER_WFR_CAT38")
	private String part_wfr_no;
	
	@Column(name="PART_NOTE")
	private String part_note;
	
	@Column(name = "EXAM")
	private String exam;

	
}
