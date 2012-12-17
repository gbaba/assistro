package com.assistro.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

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
	@Column(name = "blog_topic",nullable = true)
	private String blogTopic;
	@Column(name = "blog_athor",nullable = true)
	private String blogAthor;
	@Column(name = "blog_date",nullable = true)
	private Date blogDate;
	@Column(name = "blog_body",nullable = true)
	private String blogBody;
	@Column(name = "blog_cat",nullable = true)
	private String blogCat;
	
	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
	private List<BlogComment> comments;
	
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
		return blogBody;
	}

	public void setBlogBody(String blogBody) {
		this.blogBody = blogBody;
	}

	public String getBlogCat() {
		return blogCat;
	}

	public void setBlogCat(String blogCat) {
		this.blogCat = blogCat;
	}

	public List<BlogComment> getComments() {
		return comments;
	}

	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}
	
	
	
}
