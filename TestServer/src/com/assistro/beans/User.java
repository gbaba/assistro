package com.assistro.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

@Entity
@Table(name = "user_detail")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	@Column(name = "full_name", nullable = true)
	private String fullName;

	@Column(name = "company", nullable = true)
	private String company;

	@Column(name = "address", nullable = true)
	private String address;

	@Column(name = "email", nullable = true)
	private String email;

	@Column(name = "password", nullable = true)
	private String password;

	@Column(name = "city", nullable = true)
	private String city;

	@Column(name = "state", nullable = true)
	private String state;

	@Column(name = "phone", nullable = true)
	private String phone;

	@Column(name = "country", nullable = true)
	private String country;

	@Column(name = "bussiness_plan", nullable = true)
	private String businessPlan;
	
	@Column(name = "active", nullable = true)
	private boolean active;
		
	@Column(name = "time_Stamp", nullable = true)
	private Date timestamp;
	
	@Column(name = "max_Agent", nullable = true)
	private int maxAgent;
	
	@Column(name = "widget_id", nullable = true)
	private String widgetId;

	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getBusinessPlan() {
		return businessPlan;
	}

	public void setBusinessPlan(String businessPlan) {
		this.businessPlan = businessPlan;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getMaxAgent() {
		return maxAgent;
	}

	public void setMaxAgent(int maxAgent) {
		this.maxAgent = maxAgent;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}

}
