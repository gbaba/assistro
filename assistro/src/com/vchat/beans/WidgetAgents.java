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
@Table(name = "widget_agents")
public class WidgetAgents implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "wiget_id", nullable = true)
	private String widgetId;
	
	@Column(name = "agent_name", nullable = true)
	private String agentName;
	
	@Column(name = "agent_id", nullable = true)
	private String agentId;

	@Column(name = "agent_message", nullable = true)
	private String agentMessage;
	
	@Column(name = "agent_priority", nullable = true)
	private long agentpriority;
	
	@Column(name = "agent_status", nullable = true)
	private boolean agentStatus;
	
	@Column(name = "agent_imageurl", nullable = true)
	private String agentImageURL;
	
	
	
	
	public String getAgentImageURL() {
		return agentImageURL;
	}

	public void setAgentImageURL(String agentImageURL) {
		this.agentImageURL = agentImageURL;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentMessage() {
		return agentMessage;
	}

	public void setAgentMessage(String agentMessage) {
		this.agentMessage = agentMessage;
	}

	public long getAgentpriority() {
		return agentpriority;
	}

	public void setAgentpriority(long agentpriority) {
		this.agentpriority = agentpriority;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public boolean isAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(boolean agentStatus) {
		this.agentStatus = agentStatus;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
}
