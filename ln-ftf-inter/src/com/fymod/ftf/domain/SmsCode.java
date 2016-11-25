package com.fymod.ftf.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 发送短信记录
 * 
 * @author guochao
 *
 */
@Entity
@Table(name = "sms_code")
public class SmsCode extends BaseSqlModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "code_id")
	private Long codeid;

	@Column(name = "mobile", updatable = false)
	private String mobile;

	@Column(name = "type", updatable = false)
	private String type;

	@Column(name = "code", updatable = false)
	private String code;

	@Column(name = "state")
	private String state;

	@Column(name = "add_time")
	private Date addTime;

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCodeid() {
		return codeid;
	}

	public void setCodeid(Long codeid) {
		this.codeid = codeid;
	}

}
