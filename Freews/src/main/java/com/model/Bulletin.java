package com.model;

import java.util.Vector;

/**
 * A {@link Bulletin} is the union of a collection of {@link Video}s created by
 * a {@link User} to distribute
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 07/09/2012
 * @version 1.0
 */
public class Bulletin {

	private String id;
	private String title;
	private String user;
	private Vector<String> videoIDs;
	private String category;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Vector<String> getVideoIDs() {
		return videoIDs;
	}

	public void setVideoIDs(Vector<String> videoIDs) {
		this.videoIDs = videoIDs;
	}

}
