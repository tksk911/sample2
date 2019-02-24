package com.sample2.model;

public class MovieRegistModel {
	private String title;
	private String thumbnails;
	private String comment;
	private final String USER_ID = "1";
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title=title;
	}
	
	
	public String getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(String thumbnails) {
		this.thumbnails=thumbnails;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment=comment;
	}
	
	public String getUserId() {
		return USER_ID;
	}
}
