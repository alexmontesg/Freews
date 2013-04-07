package com.model;

import java.util.Date;

/**
 * A {@link Rating} is the score given to a {@link Video} by a {@link User}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 07/04/2013
 * @version 1.0
 */
public class Rating {

	private String id;
	private String user_id, item_id;
	private Double preference;
	private Date created_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public Double getPreference() {
		return preference;
	}

	public void setPreference(Double preference) {
		this.preference = preference;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

}
