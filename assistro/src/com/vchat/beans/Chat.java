package com.vchat.beans;

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
@Table(name = "user_chat")
public class Chat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	@Column(name = "message", nullable = true)
	private String message;

	@Column(name = "vistor_id", nullable = true)
	private String vistorId;

	@Column(name = "agent_id", nullable = true)
	private String agentId;

	@Column(name = "widget_id", nullable = true)
	private String widgetId;
	
	@Column(name = "message_count", nullable = true)
	private Long messageCount;
	
	@Column(name = "message_time", nullable = true)
	private Date messageTime;

	@Column(name = "chat_time", nullable = true)
	private Date chatTime;
	
	@Column(name = "message_type", nullable = true)
	private String messageType;
	
	@Column(name = "session_id", nullable = true)
	private Long sessionID;
	
	@Column(name = "country_name", nullable = true)
	private String countryName;
	
	@Column(name = "city_name", nullable = true)
	private String cityName;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVistorId() {
		return vistorId;
	}

	public void setVistorId(String vistorId) {
		this.vistorId = vistorId;
	}

	

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

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

	public Long getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Long messageCount) {
		this.messageCount = messageCount;
	}

	public Date getChatTime() {
		return chatTime;
	}

	public void setChatTime(Date chatTime) {
		this.chatTime = chatTime;
	}

	public Long getSessionID() {
		return sessionID;
	}

	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
	}

	public Date getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
		
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}	
	
}
