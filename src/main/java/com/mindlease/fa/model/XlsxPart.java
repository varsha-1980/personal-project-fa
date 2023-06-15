package com.mindlease.fa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the xlsx_part database table.
 * 
 */

@NoArgsConstructor
@Data
@Entity
@Table(name = "xlsx_part")
@NamedQuery(name = "XlsxPart.findAll", query = "SELECT x FROM XlsxPart x")
public class XlsxPart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	//INSERT INTO xlsx_part (id, PART_BDV_CAT02, PART_CELLS_CAT21, PART_CHANNEL_CAT01, PART_CLASS_CAT20, PART_ESD_CAT32, PART_GATEOXIDE_CAT07, PART_METAL_CAT11, PART_NAME, PART_NUMBER_WFR_CAT38, PART_PACK_CAT22, PART_NOTE, PART_VERSION, PART_PASS_CAT31, PART_PITCH_CAT08, PART_REL_CAT12, PART_SUBSTR_CAT03, PART_TR_CALC_CAT19, PART_TRENCH_CAT19, exam, PART_WFR_THICKNESS_CAT15) VALUES (1, '460', '0', 'N-CHANNEL', 'GEN', 'NOESD', '100', 'AC28', '566085FSLIZ', '1350', 'BW', NULL, '.04', 'L2P12', 'HEXFET6', 'COMMERCIAL', 'A0305N8-HVM', NULL, '0', 0, ' ')
	@Column(name = "PART_NAME")
	private String name;

	@Column(name="PART_VERSION")
	private BigDecimal part_version;

	@Column(name="PART_CHANNEL_CAT01")
	private String part_channel;

	@Column(name="PART_BDV_CAT02")
	private Integer part_bdv;

	@Column(name="PART_SUBSTR_CAT03")
	private String part_substr;
	
	@Column(name="PART_GATEOXIDE_CAT07")
	private Long part_gateoxide;
	
	@Column(name="PART_PITCH_CAT08")
	private String part_pitch;
	
	@Column(name="PART_METAL_CAT11")
	private String part_metal;
	
	@Column(name="PART_REL_CAT12")
	private String part_rel;
	
	@Column(name="PART_WFR_THICKNESS_CAT15")
	private Integer part_wfr_thick;
	
	@Column(name="PART_TRENCH_CAT19")
	private Long part_trench;
	
	@Column(name="PART_TR_CALC_CAT19")
	private BigDecimal part_calc;
	
	@Column(name="PART_CLASS_CAT20")
	private String part_class;
	
	@Column(name="PART_CELLS_CAT21")
	private Integer part_cells;
	
	@Column(name="PART_PACK_CAT22")
	private String part_pack;
	
	@Column(name="PART_PASS_CAT31")
	private String part_pass;
	
	@Column(name="PART_ESD_CAT32")
	private String part_esd;
	
	@Column(name="PART_NUMBER_WFR_CAT38")
	private Long part_wfr_no;
	
	@Column(name="PART_NOTE")
	private String part_note;
	
	@Column(name = "EXAM")
	private String exam;

}