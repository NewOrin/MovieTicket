package com.etc.movieticket.entity;

import java.io.Serializable;

public class Actor  implements Serializable {
	private Integer ac_id;
	private String ac_cname;
	private String ac_ename;
	private String ac_avatar;
	private String ac_album;
	private String ac_birthday;
	private String ac_bornPlace;
	private String ac_identity;
	private String ac_introduce;
	private String ac_work;
	private String ac_webId;
	private String ac_sex;
	
	
	public final String getAc_sex() {
		return ac_sex;
	}
	public final void setAc_sex(String ac_sex) {
		this.ac_sex = ac_sex;
	}
	public final String getAc_webId() {
		return ac_webId;
	}
	public final void setAc_webId(String ac_webId) {
		this.ac_webId = ac_webId;
	}
	public final Integer getAc_id() {
		return ac_id;
	}
	public final void setAc_id(Integer ac_id) {
		this.ac_id = ac_id;
	}
	public final String getAc_cname() {
		return ac_cname;
	}
	public final void setAc_cname(String ac_cname) {
		this.ac_cname = ac_cname;
	}
	public final String getAc_ename() {
		return ac_ename;
	}
	public final void setAc_ename(String ac_ename) {
		this.ac_ename = ac_ename;
	}
	public final String getAc_avatar() {
		return ac_avatar;
	}
	public final void setAc_avatar(String ac_avatar) {
		this.ac_avatar = ac_avatar;
	}
	public final String getAc_album() {
		return ac_album;
	}
	public final void setAc_album(String ac_album) {
		this.ac_album = ac_album;
	}
	public final String getAc_birthday() {
		return ac_birthday;
	}
	public final void setAc_birthday(String ac_birthday) {
		this.ac_birthday = ac_birthday;
	}
	public final String getAc_bornPlace() {
		return ac_bornPlace;
	}
	public final void setAc_bornPlace(String ac_bornPlace) {
		this.ac_bornPlace = ac_bornPlace;
	}
	public final String getAc_identity() {
		return ac_identity;
	}
	public final void setAc_identity(String ac_identity) {
		this.ac_identity = ac_identity;
	}
	public final String getAc_introduce() {
		return ac_introduce;
	}
	public final void setAc_introduce(String ac_introduce) {
		this.ac_introduce = ac_introduce;
	}
	public final String getAc_work() {
		return ac_work;
	}
	public final void setAc_work(String ac_work) {
		this.ac_work = ac_work;
	}
	public Actor() {
		super();
	}
	@Override
	public String toString() {
		return "Actor [ac_cname=" + ac_cname + ", ac_ename=" + ac_ename + ", ac_avatar=" + ac_avatar + ", ac_album=" + ac_album + ", ac_birthday=" + ac_birthday + ", ac_bornPlace=" + ac_bornPlace + ", ac_identity=" + ac_identity + ", ac_introduce=" + ac_introduce + ", ac_work=" + ac_work
				+ ", ac_webId=" + ac_webId + ", ac_sex=" + ac_sex + "]";
	}
	
	
}