package com.vchat.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Blob;

@Entity
@Table(name = "widget_contents")
public class WidgetContents implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getOnlineHeader() {
		return onlineHeader;
	}

	public void setOnlineHeader(String onlineHeader) {
		this.onlineHeader = onlineHeader;
	}

	public String getOfflineHeader() {
		return offlineHeader;
	}

	public void setOfflineHeader(String offlineHeader) {
		this.offlineHeader = offlineHeader;
	}

	public Blob getHeaderLogo() {
		return headerLogo;
	}

	public void setHeaderLogo(Blob headerLogo) {
		this.headerLogo = headerLogo;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getmPanel() {
		return mPanel;
	}

	public void setmPanel(String mPanel) {
		this.mPanel = mPanel;
	}

	public String getOffLinePanel() {
		return offLinePanel;
	}

	public void setOffLinePanel(String offLinePanel) {
		this.offLinePanel = offLinePanel;
	}

	public String getColstate() {
		return colstate;
	}

	public void setColstate(String colstate) {
		this.colstate = colstate;
	}

	public String getErrorState() {
		return errorState;
	}

	public void setErrorState(String errorState) {
		this.errorState = errorState;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public boolean isShowMessage() {
		return showMessage;
	}

	public void setShowMessage(boolean showMessage) {
		this.showMessage = showMessage;
	}

	public String getInPanel() {
		return inPanel;
	}

	public void setInPanel(String inPanel) {
		this.inPanel = inPanel;
	}

	public boolean isProActiveChat() {
		return proActiveChat;
	}

	public void setProActiveChat(boolean proActiveChat) {
		this.proActiveChat = proActiveChat;
	}

	public String getOfflineEmailAdmin() {
		return offlineEmailAdmin;
	}

	public void setOfflineEmailAdmin(String offlineEmailAdmin) {
		this.offlineEmailAdmin = offlineEmailAdmin;
	}

	public String getWidgetStyle() {
		return widgetStyle;
	}

	public void setWidgetStyle(String widgetStyle) {
		this.widgetStyle = widgetStyle;
	}





	@Column(name = "online_header", nullable = true)
	private String onlineHeader;
	@Column(name = "offline_header", nullable = true)
	private String offlineHeader;
	@Column(name = "header_logo", nullable = true)
	private Blob headerLogo;
	@Column(name = "footer_text", nullable = true)
	private String footer;
	@Column(name = "main_panel", nullable = true)
	private String mPanel;
	@Column(name = "inside_panel", nullable = true)
	private String inPanel;
	@Column(name = "offline_panel", nullable = true)
	private String offLinePanel;
	@Column(name = "colapsed_state", nullable = true)
	private String colstate;
	@Column(name = "error_state", nullable = true)
	private String errorState;
	@Column(name = "rules", nullable = true)
	private String rules;
	@Column(name = "widget_id", nullable = true)
	private String widgetId;
	@Column(name = "show_Message", nullable = true)
	private boolean showMessage;
	@Column(name = "proActive_chat", nullable = true)
	private boolean proActiveChat;
	@Column(name = "offline_email", nullable = true)
	private String offlineEmailAdmin;
	@Column(name = "widget_style", nullable = true)
	private String widgetStyle;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

}
