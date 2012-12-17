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
@Table(name = "blog_comments")
public class BlogComment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	@Column(name = "com_usr_name",nullable = true)
	private String comUsrName;
	@Column(name = "com_usr_email",nullable = true)
	private String comUsrEmail;
	@Column(name = "com_body",nullable = true)
	private String comBody;
	@Column(name = "com_date",nullable = true)
	private Date comDate;
	@Column(name = "blog_keyVal",nullable = true)
	private String blogKeyVal;
	
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getComUsrName() {
		return comUsrName;
	}

	public void setComUsrName(String comUsrName) {
		this.comUsrName = comUsrName;
	}


	public String getComBody() {
		return comBody;
	}

	public void setComBody(String comBody) {
		this.comBody = comBody;
	}

	public String getComUsrEmail() {
		return comUsrEmail;
	}

	public void setComUsrEmail(String comUsrEmail) {
		this.comUsrEmail = comUsrEmail;
	}

	public Date getComDate() {
		return comDate;
	}

	public void setComDate(Date comDate) {
		this.comDate = comDate;
	}

	public String getBlogKeyVal() {
		return blogKeyVal;
	}

	public void setBlogKeyVal(String blogKeyVal) {
		this.blogKeyVal = blogKeyVal;
	}
	
	
	
}
