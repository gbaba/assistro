package com.vchat.service;

import java.io.Serializable;


public class Visitor implements Serializable {
	private static final long serialVersionUID = 1L;
	private String operator;
	private String visitor;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

}
