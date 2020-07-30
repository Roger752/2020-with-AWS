package com.stempleRun.db.dto;

import java.util.Date;

public class PictureVO {
	
	private int pic_num;
	private String pic_file;
	private String pic_latitude;
	private String pic_longitude;
	private String pic_tag;
	private int m_num;
	private String pic_content;
	private String pic_sns;
	private Date pic_date;
	private String pic_place;
	private String pic_title;
	
	public int getPic_num() {
		return pic_num;
	}
	public void setPic_num(int pic_num) {
		this.pic_num = pic_num;
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
	public String getPic_tag() {
		return pic_tag;
	}
	public void setPic_tag(String pic_tag) {
		this.pic_tag = pic_tag;
	}
	public int getM_num() {
		return m_num;
	}
	public void setM_num(int m_num) {
		this.m_num = m_num;
	}
	
	public String getPic_content() {
		return pic_content;
	}
	public void setPic_content(String pic_content) {
		this.pic_content = pic_content;
	}
	public String getPic_sns() {
		return pic_sns;
	}
	public void setPic_sns(String pic_sns) {
		this.pic_sns = pic_sns;
	}
	public Date getPic_date() {
		return pic_date;
	}
	public void setPic_date(Date pic_date) {
		this.pic_date = pic_date;
	}
	public String getPic_place() {
		return pic_place;
	}
	public void setPic_place(String pic_place) {
		this.pic_place = pic_place;
	}
	public String getPic_title() {
		return pic_title;
	}
	public void setPic_title(String pic_title) {
		this.pic_title = pic_title;
	}
	
}
