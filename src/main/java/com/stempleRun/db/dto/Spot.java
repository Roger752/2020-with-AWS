package com.stempleRun.db.dto;

import java.util.Date;

public class Spot {
//	p_num	number(10)	NOT NULL,
//	p_title	varchar2(50)	NULL,
//	p_content	varchar2(1000)	NULL,
//	p_day	timestamp	NULL,
//	p_recommend	number(7)	NULL,
//	p_file	varchar2(1000)	NULL,
//	b_num	number(1)	NOT NULL,
//	m_num	number(6)	NOT NULL,
//	pic_num	number(10)	NOT NULL

	private int p_num;
	private String p_title;
	private String p_content;
	private Date p_day;
	private int p_recommend;
	private String p_file;
	private int b_num;
	private int m_num;
	private int pic_num;
	private String pic_tag;
	private String pic_file;
	private String pic_latitude;
	private String pic_longitude;
	private int rk;

	


	public int getRk() {
		return rk;
	}

	public void setRk(int rk) {
		this.rk = rk;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	private String m_name;
	
	public int getP_num() {
		return p_num;
	}

	public void setP_num(int p_num) {
		this.p_num = p_num;
	}


	public String getP_title() {
		return p_title;
	}

	public void setP_title(String p_title) {
		this.p_title = p_title;
	}

	public String getP_content() {
		return p_content;
	}

	public void setP_content(String p_content) {
		this.p_content = p_content;
	}

	public Date getP_day() {
		return p_day;
	}

	public void setP_day(Date p_day) {
		this.p_day = p_day;
	}

	public int getP_recommend() {
		return p_recommend;
	}

	public void setP_recommend(int p_recommend) {
		this.p_recommend = p_recommend;
	}

	public String getP_file() {
		return p_file;
	}

	public void setP_file(String p_file) {
		this.p_file = p_file;
	}

	public int getB_num() {
		return b_num;
	}

	public void setB_num(int b_num) {
		this.b_num = b_num;
	}

	public int getM_num() {
		return m_num;
	}

	public void setM_num(int m_num) {
		this.m_num = m_num;
	}

	public int getPic_num() {
		return pic_num;
	}

	public void setPic_num(int pic_num) {
		this.pic_num = pic_num;
	}
	
	public String getPic_tag() {
		return pic_tag;
	}

	public void setPic_tag(String pic_tag) {
		this.pic_tag = pic_tag;
	}
	
	public String getPic_file() {
		return pic_file;
	}

	public void setPic_file(String pic_file) {
		this.pic_file = pic_file;
	}
	
	public String getPic_latitude() {
		return pic_latitude;
	}

	public void setPic_latitude(String pic_latitude) {
		this.pic_latitude = pic_latitude;
	}

	public String getPic_longitude() {
		return pic_longitude;
	}

	public void setPic_longitude(String pic_longitude) {
		this.pic_longitude = pic_longitude;
	}

}
