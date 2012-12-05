package com.vchat.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

@Entity
@Table(name = "user_chart")
public class Chart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	@Column(name = "visitor_on_site")
	private int visitorOnSite;
	@Column(name = "saw_online")
	private int sawOnline;
	@Column(name = "chated")
	private int chated;
	@Column(name = "widget_id", nullable = true)
	private String widgetId;
	@Column(name = "day", nullable = true)
	private String day;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public int getVisitorOnSite() {
		return visitorOnSite;
	}
	public void setVisitorOnSite(int visitorOnSite) {
		this.visitorOnSite = visitorOnSite;
	}
	public int getSawOnline() {
		return sawOnline;
	}
	public void setSawOnline(int sawOnline) {
		this.sawOnline = sawOnline;
	}
	public int getChated() {
		return chated;
	}
	public void setChated(int chated) {
		this.chated = chated;
	}
	public String getWidgetId() {
		return widgetId;
	}
	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
}
