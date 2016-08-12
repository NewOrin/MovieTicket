package com.etc.movieticket.entity;

import java.io.Serializable;

/**
 * 订票实体
 * 
 * @author long
 *
 */
public class BookTicket implements Serializable {
	//id
	private int id; 
	//电影showId
	private String mvShowId;
	//影院webid
	private String cinemaWebId;
	//开始放映时间
	private String startTime;
	//作为坐标
	private String postion;
	//状态，0未取票，1，取票
	private String status;
	//电影名称
	private String mvName;
	//影院名称
	private String cinemaName;
	//日期
	private String data;
	//放映厅
	private String mvHall;
	//用户id
	private String userPhone;
	//价钱
	private String price;
	public final String getPrice() {
		return price;
	}
	public final void setPrice(String price) {
		this.price = price;
	}
	public final String getUserPhone() {
		return userPhone;
	}
	public final void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public final int getId() {
		return id;
	}
	public final void setId(int id) {
		this.id = id;
	}
	public final String getMvShowId() {
		return mvShowId;
	}
	public final void setMvShowId(String mvShowId) {
		this.mvShowId = mvShowId;
	}
	public final String getCinemaWebId() {
		return cinemaWebId;
	}
	public final void setCinemaWebId(String cinemaWebId) {
		this.cinemaWebId = cinemaWebId;
	}
	public final String getStartTime() {
		return startTime;
	}
	public final void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public final String getPostion() {
		return postion;
	}
	public final void setPostion(String postion) {
		this.postion = postion;
	}
	public final String getStatus() {
		return status;
	}
	public final void setStatus(String status) {
		this.status = status;
	}
	public final String getMvName() {
		return mvName;
	}
	public final void setMvName(String mvName) {
		this.mvName = mvName;
	}
	public final String getCinemaName() {
		return cinemaName;
	}
	public final void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	public final String getData() {
		return data;
	}
	public final void setData(String data) {
		this.data = data;
	}
	public final String getMvHall() {
		return mvHall;
	}
	public final void setMvHall(String mvHall) {
		this.mvHall = mvHall;
	}
	public BookTicket() {
		super();
	}
	
	public BookTicket(String mvShowId, String cinemaWebId, String startTime, String postion, String status, String mvName, String cinemaName, String data, String mvHall, String userPhone, String price) {
		super();
		this.mvShowId = mvShowId;
		this.cinemaWebId = cinemaWebId;
		this.startTime = startTime;
		this.postion = postion;
		this.status = status;
		this.mvName = mvName;
		this.cinemaName = cinemaName;
		this.data = data;
		this.mvHall = mvHall;
		this.userPhone = userPhone;
		this.price = price;
	}
	public BookTicket(int id, String mvShowId, String cinemaWebId, String startTime, String postion, String status, String mvName, String cinemaName, String data, String mvHall, String userPhone) {
		super();
		this.id = id;
		this.mvShowId = mvShowId;
		this.cinemaWebId = cinemaWebId;
		this.startTime = startTime;
		this.postion = postion;
		this.status = status;
		this.mvName = mvName;
		this.cinemaName = cinemaName;
		this.data = data;
		this.mvHall = mvHall;
		this.userPhone = userPhone;
	}

	@Override
	public String toString() {
		return "BookTicket{" +
				"id=" + id +
				", mvShowId='" + mvShowId + '\'' +
				", cinemaWebId='" + cinemaWebId + '\'' +
				", startTime='" + startTime + '\'' +
				", postion='" + postion + '\'' +
				", status='" + status + '\'' +
				", mvName='" + mvName + '\'' +
				", cinemaName='" + cinemaName + '\'' +
				", data='" + data + '\'' +
				", mvHall='" + mvHall + '\'' +
				", userPhone='" + userPhone + '\'' +
				", price='" + price + '\'' +
				'}';
	}
}
