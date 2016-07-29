package com.etc.movieticket.entity;

import java.io.Serializable;

public class MovieActor implements Serializable {
	private Integer ma_id;
	private Integer mv_id;
	private Integer ac_id;
	private String mv_showId;
	private String  ac_webId;
	private String ac_name;
	private String ac_img;
	public final Integer getMa_id() {
		return ma_id;
	}
	public final void setMa_id(Integer ma_id) {
		this.ma_id = ma_id;
	}
	public final Integer getMv_id() {
		return mv_id;
	}
	public final void setMv_id(Integer mv_id) {
		this.mv_id = mv_id;
	}
	public final Integer getAc_id() {
		return ac_id;
	}
	public final void setAc_id(Integer ac_id) {
		this.ac_id = ac_id;
	}
	public final String getMv_showId() {
		return mv_showId;
	}
	public final void setMv_showId(String mv_showId) {
		this.mv_showId = mv_showId;
	}
	public final String getAc_webId() {
		return ac_webId;
	}
	public final void setAc_webId(String ac_webId) {
		this.ac_webId = ac_webId;
	}
	public final String getAc_name() {
		return ac_name;
	}
	public final void setAc_name(String ac_name) {
		this.ac_name = ac_name;
	}
	public final String getAc_img() {
		return ac_img;
	}
	public final void setAc_img(String ac_img) {
		this.ac_img = ac_img;
	}
	public MovieActor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MovieActor(String mv_showId, String ac_webId, String ac_name, String ac_img) {
		super();
		this.mv_showId = mv_showId;
		this.ac_webId = ac_webId;
		this.ac_name = ac_name;
		this.ac_img = ac_img;
	}
	@Override
	public String toString() {
		return "MovieActor [mv_showId=" + mv_showId + ", ac_webId=" + ac_webId + ", ac_name=" + ac_name + ", ac_img=" + ac_img + "]";
	}
	
	
}