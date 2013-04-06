package com.model;

import java.util.Date;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A {@link Video} is the result of processing the upload of a {@link User} or
 * the raw upload
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.5
 */
@XmlRootElement
public class Video {

	private String id;
	private Map<String, String> headline;
	private Map<String, String> description;
	private Map<String, Vector<String>> tags;
	private String uploadedBy;
	private Date uploadDate;
	private Date date;
	private Double lat;
	private Double lon;

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public Map<String, String> getHeadline() {
		return headline;
	}

	public void setHeadline(Map<String, String> headline) {
		this.headline = headline;
	}

	@XmlElement
	public Map<String, Vector<String>> getTags() {
		return tags;
	}

	public void setTags(Map<String, Vector<String>> tags) {
		this.tags = tags;
	}

	@XmlElement
	public Map<String, String> getDescription() {
		return description;
	}

	public void setDescription(Map<String, String> description) {
		this.description = description;
	}

	@XmlAttribute
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@XmlAttribute
	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	@XmlAttribute
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@XmlAttribute
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	@XmlAttribute
	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

}
