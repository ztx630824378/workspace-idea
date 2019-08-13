package com.jk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LogModel implements Serializable {

	private String id;
	//属性名 必须和  mongo 的key 一致才能取到值
	private String logname;
	
	private String logip;
	
	private String logis;
	
	private String requerpath;
	
	private String parame;
	
    private String userid;
    
    private Object returningValue;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 处理从	前端到后端的时间
	private Date stadate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 处理从	前端到后端的时间
	private Date enddate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 处理从	前端到后端的时间
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date logtime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogname() {
		return logname;
	}

	public void setLogname(String logname) {
		this.logname = logname;
	}

	public String getLogip() {
		return logip;
	}

	public void setLogip(String logip) {
		this.logip = logip;
	}

	public String getLogis() {
		return logis;
	}

	public void setLogis(String logis) {
		this.logis = logis;
	}

	public String getRequerpath() {
		return requerpath;
	}

	public void setRequerpath(String requerpath) {
		this.requerpath = requerpath;
	}

	public String getParame() {
		return parame;
	}

	public void setParame(String parame) {
		this.parame = parame;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}


	public Date getStadate() {
		return stadate;
	}

	public void setStadate(Date stadate) {
		this.stadate = stadate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}

	public Object getReturningValue() {
		return returningValue;
	}

	public void setReturningValue(Object returningValue) {
		this.returningValue = returningValue;
	}

	@Override
	public String toString() {
		return "LogModel [id=" + id + ", logname=" + logname + ", logip=" + logip + ", logis=" + logis + ", requerpath="
				+ requerpath + ", parame=" + parame + ", userid=" + userid + ", returningValue=" + returningValue
				+ ", stadate=" + stadate + ", enddate=" + enddate + ", logtime=" + logtime + "]";
	}

	
	
	
}
