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
import com.google.appengine.api.datastore.Text;

@Entity
@Table(name = "assistro_blog")
public class Blog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	@Column(name = "blog_key",nullable = true)
	private String blogKey;
	@Column(name = "blog_topic",nullable = true)
	private String blogTopic;
	@Column(name = "blog_athor",nullable = true)
	private String blogAthor;
	@Column(name = "blog_date",nullable = true)
	private Date blogDate;
	@Column(name = "intro_line",nullable = true)
	private String introLine;
	@Column(name = "blog_body",nullable = true)
	private String blogBody;
	@Column(name = "long_body",nullable = true)
	private Text longBody;
	@Column(name = "blog_cat",nullable = true)
	private String blogCat;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getBlogTopic() {
		return blogTopic;
	}

	public void setBlogTopic(String blogTopic) {
		this.blogTopic = blogTopic;
	}

	public String getBlogAthor() {
		return blogAthor;
	}

	public void setBlogAthor(String blogAthor) {
		this.blogAthor = blogAthor;
	}

	public Date getBlogDate() {
		return blogDate;
	}

	public void setBlogDate(Date blogDate) {
		this.blogDate = blogDate;
	}

	public String getBlogBody() {
		if (this.longBody == null)
			return "";
		return this.longBody.getValue();
	}

	public void setBlogBody(String blogBody) {
		this.longBody = new Text(blogBody);
	}

	public String getBlogCat() {
		return blogCat;
	}

	public void setBlogCat(String blogCat) {
		this.blogCat = blogCat;
	}

	public String getBlogKey() {
		return blogKey;
	}

	public void setBlogKey(String blogKey) {
		this.blogKey = blogKey;
	}

	public Text getLongBody() {
		return longBody;
	}

	public void setLongBody(Text longBody) {
		this.longBody = longBody;
	}

	public String getIntroLine() {
		return introLine;
	}

	public void setIntroLine(String introLine) {
		this.introLine = introLine;
	}
	
	
	
	
}
