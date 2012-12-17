package com.assistro.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.assistro.beans.Chat;

public class MessageQueue implements Serializable{
	private static final long serialVersionUID = 1L;
	private long lastId;
	private Visitor visitor;
	private String widgetid;
	private String widgetCode;
	private Date lastMessageTime;
	private Date startTime;
	private int conversationtime;
	private String operatorName;
	private List<Chat> chatList = new ArrayList<Chat>();

	public MessageQueue(Visitor visitor, String widgetid, String widgetCode) {
		this.visitor = visitor;
		this.widgetid = widgetid;
		this.widgetCode = widgetCode;
	}

	public long getLastId() {
		return lastId;
	}

	public Date getLastMessageTime() {
		return lastMessageTime;
	}

	public int getConversationTime() {
		return conversationtime;
	}

	public boolean putMessage(Chat chat) {
		if (startTime == null) {
			startTime = new Date();
		}
		lastMessageTime = new Date();
		conversationtime = (int) (lastMessageTime.getTime() - startTime.getTime()) / 1000;
		chat.setMessageCount(lastId++);
		chatList.add(chat);
		return true;
	}

	public List<Chat> getChatById(long id) {
		List<Chat> list = new ArrayList<Chat>();
		if (id == 0) {
			list.addAll(chatList);
		} else {
			for (;id < chatList.size(); id++) {
				Chat chat = chatList.get((int) id);
				list.add(chat);
			}

		}
		return list;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	public String getWidgetid() {
		return widgetid;
	}

	public void setWidgetid(String widgetid) {
		this.widgetid = widgetid;
	}

	public String getWidgetCode() {
		return widgetCode;
	}

	public void setWidgetCode(String widgetCode) {
		this.widgetCode = widgetCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
}
