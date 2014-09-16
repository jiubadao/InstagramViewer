package com.codepath.instagramviewer;

public class InstagramPhoto {

	private String username;
	private String userProfilePicUrl;
	private String caption;
	private String imageUrl;
	private int imageHeight;
	private String likesCount;
	private String lastCommentUsername;
	private String lastComment;
	private String secondLastCommentUsername;
	private String secondLastComment;
		
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserProfilePicUrl() {
		return userProfilePicUrl;
	}
	public void setUserProfilePicUrl(String userProfilePicUrl) {
		this.userProfilePicUrl = userProfilePicUrl;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	public String getLikesCount() {
		return likesCount;
	}
	public void setLikesCount(String likesCount) {
		this.likesCount = likesCount;
	}
	public String getLastCommentUsername() {
		return lastCommentUsername;
	}
	public void setLastCommentUsername(String lastCommentUsername) {
		this.lastCommentUsername = lastCommentUsername;
	}
	public String getLastComment() {
		return lastComment;
	}
	public void setLastComment(String lastComment) {
		this.lastComment = lastComment;
	}
	public String getSecondLastCommentUsername() {
		return secondLastCommentUsername;
	}
	public void setSecondLastCommentUsername(String secondLastCommentUsername) {
		this.secondLastCommentUsername = secondLastCommentUsername;
	}
	public String getSecondLastComment() {
		return secondLastComment;
	}
	public void setSecondLastComment(String secondLastComment) {
		this.secondLastComment = secondLastComment;
	}	
	
	
}
