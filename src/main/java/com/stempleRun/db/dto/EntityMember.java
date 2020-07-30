package com.stempleRun.db.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Member")
public class EntityMember {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
	@SequenceGenerator(sequenceName = "MEMBER_SEQ", allocationSize = 1, name = "CUST_SEQ")
	private int m_num;
	
	@Column(name = "m_id", nullable = false)
	private String userId;

	@Column
	private String m_pw;

	@Column
	private String m_name;

	@Column
	private String m_email;
	
	private int type_num;

	public int getType_num() {
		return type_num;
	}

	public void setType_num(int type_num) {
		this.type_num = type_num;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getM_id() {
		return userId;
	}

	public void setM_id(String m_id) {
		this.userId = m_id;
	}

	public String getM_pw() {
		return m_pw;
	}

	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_email() {
		return m_email;
	}

	public void setM_email(String m_email) {
		this.m_email = m_email;
	}

	public int getM_num() {
		return m_num;
	}

	public void setM_num(int m_num) {
		this.m_num = m_num;
	}
	

}
