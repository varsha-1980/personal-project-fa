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
@Table(name = "tbl_news")
public class News implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "News_Timestamp")
	private String news_timestamp;

	@Column(name = "News_An_MA")
	private String news_an_ma;
	
	@Column(name = "News_Von_MA")
	private String news_von_ma;
	
	@Column(name = "News_Message")
	private String news_message;
	
	@Column(name = "News_Done")
	private Integer news_done;
	
	@Column(name = "News_Clear")
	private Integer news_clear;
	
	@Column(name = "EXAM")
	private String exam;

}
