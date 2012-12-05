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
public class CheckVisitor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	@Column(name = "visitor")
	private String visitor;
	@Column(name = "operator")
	private String operator;
	@Column(name = "widgetcode")
	private String widgetCode;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getWidgetCode() {
		return widgetCode;
	}

	public void setWidgetCode(String widgetCode) {
		this.widgetCode = widgetCode;
	}

}
