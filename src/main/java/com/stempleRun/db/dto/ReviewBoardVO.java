package com.stempleRun.db.dto;

import java.sql.Date;

public class ReviewBoardVO {
	private int bno;
	private String title;
	private String writer;
	private String type;
	private Date nal;
	private int a_chu;
	private int c_num;
	private String memo;
	private String filename;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getNal() {
		return nal;
	}
	public void setNal(Date nal) {
		this.nal = nal;
	}
	public int getA_chu() {
		return a_chu;
	}
	public void setA_chu(int a_chu) {
		this.a_chu = a_chu;
	}
	public int getC_num() {
		return c_num;
	}
	public void setC_num(int c_num) {
		this.c_num = c_num;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
