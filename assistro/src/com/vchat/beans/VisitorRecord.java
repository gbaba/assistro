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
@Table(name = "visitor_record")
public class VisitorRecord implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	
	@Column(name = "widget_id", nullable = true)
	private String widgetId;
	
	@Column(name = "visitor_name", nullable = true)
	private String visitorName;
	
	@Column(name = "visitor_email", nullable = true)
	private String visitorEmail;
	
	
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getWidgetId() {
		return widgetId;
	}
	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	public String getVisitorEmail() {
		return visitorEmail;
	}
	public void setVisitorEmail(String visitorEmail) {
		this.visitorEmail = visitorEmail;
	}
	
	
}
