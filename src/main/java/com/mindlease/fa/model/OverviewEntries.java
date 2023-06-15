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
@Table(name = "overview_entries")
public class OverviewEntries implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "pers_company")
	private String company;
	
	@Column(name = "switch_board_id")
	private Integer switchboardId;
	
	@Column(name = "item_number")
	private String itemNumber;
	
	@Column(name = "description")
	private String itemText;
	
	@Column(name = "command")
	private Integer command;
	
	@Column(name = "comm_option")
	private String commOption;
	
	@Column(name = "argument")
	private String argument;
	
	@Column(name = "heading")
	private String heading;
	
	@Column(name = "icon")
	private String icon;
	
	@Column(name = "exam")
	private String exam;
	
	
}
