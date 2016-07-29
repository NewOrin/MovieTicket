package com.etc.movieticket.entity;

import java.io.Serializable;

public class Movie  implements Serializable {
	private Integer mv_id;
	private String mv_cname;
	private String mv_ename;
	private String mv_category;
	private String mv_imax;
	private String mv_3d;
	private String mv_placeTime;
	private String mv_releaseTime;
	private String mv_score;
	private String mv_commentCount;
	private String mv_introduce;
	private String mv_imageUrl;
	private String mv_photos;
	private Integer mv_isReleased;
	private String mv_showId;
	public final Integer getMv_id() {
		return mv_id;
	}
	public final void setMv_id(Integer mv_id) {
		this.mv_id = mv_id;
	}
	public final String getMv_cname() {
		return mv_cname;
	}
	public final void setMv_cname(String mv_cname) {
		this.mv_cname = mv_cname;
	}
	public final String getMv_ename() {
		return mv_ename;
	}
	public final void setMv_ename(String mv_ename) {
		this.mv_ename = mv_ename;
	}
	public final String getMv_category() {
		return mv_category;
	}
	public final void setMv_category(String mv_category) {
		this.mv_category = mv_category;
	}
	
	public final String getMv_imax() {
		return mv_imax;
	}
	public final void setMv_imax(String mv_imax) {
		this.mv_imax = mv_imax;
	}
	public final String getMv_3d() {
		return mv_3d;
	}
	public final void setMv_3d(String mv_3d) {
		this.mv_3d = mv_3d;
	}
	public final String getMv_placeTime() {
		return mv_placeTime;
	}
	public final void setMv_placeTime(String mv_placeTime) {
		this.mv_placeTime = mv_placeTime;
	}
	public final String getMv_releaseTime() {
		return mv_releaseTime;
	}
	public final void setMv_releaseTime(String mv_releaseTime) {
		this.mv_releaseTime = mv_releaseTime;
	}
	public final String getMv_score() {
		return mv_score;
	}
	public final void setMv_score(String mv_score) {
		this.mv_score = mv_score;
	}
	public final String getMv_commentCount() {
		return mv_commentCount;
	}
	public final void setMv_commentCount(String mv_commentCount) {
		this.mv_commentCount = mv_commentCount;
	}
	
	public final String getMv_introduce() {
		return mv_introduce;
	}
	public final void setMv_introduce(String mv_introduce) {
		this.mv_introduce = mv_introduce;
	}
	public final String getMv_imageUrl() {
		return mv_imageUrl;
	}
	public final void setMv_imageUrl(String mv_imageUrl) {
		this.mv_imageUrl = mv_imageUrl;
	}
	public final String getMv_photos() {
		return mv_photos;
	}
	public final void setMv_photos(String mv_photos) {
		this.mv_photos = mv_photos;
	}
	public final Integer getMv_isReleased() {
		return mv_isReleased;
	}
	public final void setMv_isReleased(Integer mv_isReleased) {
		this.mv_isReleased = mv_isReleased;
	}
	public final String getMv_showId() {
		return mv_showId;
	}
	public final void setMv_showId(String mv_showId) {
		this.mv_showId = mv_showId;
	}

	public Movie(String mv_cname) {
		this.mv_cname = mv_cname;
	}

	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Movie [mv_id=" + mv_id + ", mv_cname=" + mv_cname + ", mv_ename=" + mv_ename + ", mv_category=" + mv_category + ", mv_imax=" + mv_imax + ", mv_3d=" + mv_3d + ", mv_placeTime=" + mv_placeTime + ", mv_releaseTime=" + mv_releaseTime + ", mv_score=" + mv_score + ", mv_commentCount="
				+ mv_commentCount + ", mv_introduse=" + mv_introduce + ", mv_imageUrl=" + mv_imageUrl + ", mv_photos=" + mv_photos + ", mv_isReleased=" + mv_isReleased + ", mv_showId=" + mv_showId + "]";
	}
	
	
}