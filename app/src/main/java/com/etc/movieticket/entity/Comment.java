package com.etc.movieticket.entity;

import java.io.Serializable;

public class Comment  implements Serializable {
	private Integer cm_id;
	private Integer cm_userId;
	private String cm_userName;
	private String cm_userAvatar;
	private String cm_time;
	private String cm_score;
	private String cm_content;
	private Integer cm_movieId;
	private String cm_showMvId;
	
	public final String getCm_showMvId() {
		return cm_showMvId;
	}
	public final void setCm_showMvId(String cm_showMvId) {
		this.cm_showMvId = cm_showMvId;
	}
	public final Integer getCm_id() {
		return cm_id;
	}
	public final void setCm_id(Integer cm_id) {
		this.cm_id = cm_id;
	}
	public final Integer getCm_userId() {
		return cm_userId;
	}
	public final void setCm_userId(Integer cm_userId) {
		this.cm_userId = cm_userId;
	}
	public final String getCm_userName() {
		return cm_userName;
	}
	public final void setCm_userName(String cm_userName) {
		this.cm_userName = cm_userName;
	}
	public final String getCm_userAvatar() {
		return cm_userAvatar;
	}
	public final void setCm_userAvatar(String cm_userAvatar) {
		this.cm_userAvatar = cm_userAvatar;
	}
	public final String getCm_time() {
		return cm_time;
	}
	public final void setCm_time(String cm_time) {
		this.cm_time = cm_time;
	}
	public final String getCm_score() {
		return cm_score;
	}
	public final void setCm_score(String cm_score) {
		this.cm_score = cm_score;
	}
	public final String getCm_content() {
		return cm_content;
	}
	public final void setCm_content(String cm_content) {
		this.cm_content = cm_content;
	}
	public final Integer getCm_movieId() {
		return cm_movieId;
	}
	public final void setCm_movieId(Integer cm_movieId) {
		this.cm_movieId = cm_movieId;
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(Integer cm_userId, String cm_userName, String cm_userAvatar, String cm_time, String cm_score, String cm_content, String cm_showMvId) {
		super();
		this.cm_userId = cm_userId;
		this.cm_userName = cm_userName;
		this.cm_userAvatar = cm_userAvatar;
		this.cm_time = cm_time;
		this.cm_score = cm_score;
		this.cm_content = cm_content;
		this.cm_showMvId = cm_showMvId;
	}
	@Override
	public String toString() {
		return "Comment [cm_id=" + cm_id + ", cm_userId=" + cm_userId + ", cm_userName=" + cm_userName + ", cm_userAvatar=" + cm_userAvatar + ", cm_time=" + cm_time + ", cm_score=" + cm_score + ", cm_content=" + cm_content + ", cm_movieId=" + cm_movieId + ", cm_showMvId=" + cm_showMvId + "]";
	}
	
	
}