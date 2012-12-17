package com.assistro.controller;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.assistro.beans.Chat;
import com.assistro.beans.WidgetAgents;
import com.assistro.service.MessageQueue;
import com.assistro.service.Visitor;
import com.assistro.util.MapEntry;
import com.assistro.util.Util;
import com.assistro.util.CustomMap;

public class VMessageController{

	
	private final CustomMap<String, MessageQueue> tempchatqueue = new CustomMap<String, MessageQueue>();
	private final List<String> operators = new ArrayList<String>();
	private final Cache widgetmap = getCache();
	private final Cache chatqueue = getCache();
	private final Cache visitormap = getCache();


	private static VMessageController vchatController;
	private static final Logger log = Logger.getLogger(VMessageController.class
			.getName());

	private VMessageController() {
	}

	public static VMessageController getVMessageController() {
		if (vchatController == null) {
			vchatController = new VMessageController();
		}
		return vchatController;
	}
	
	private static Cache getCache() {
		Cache cache = null;
		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			log.warning(e.getClass().getSimpleName() + ": " + e.getMessage());
			return null;
		}

		return cache;
	}

	public synchronized Visitor getVisitor(String widgetCode, String cookie) {
		Visitor visitor = null;
		try {
			final String widgetKey = widgetCode + cookie;
			if (chatqueue.get(widgetKey) != null)  {
				MessageQueue mq = (MessageQueue)chatqueue.get(widgetKey);
				visitor = mq.getVisitor();
			} else {
				List<WidgetAgents> list = operatorList(widgetCode);
				for (int i = 1; i < 11; i++) {
					String vt = "visitor" + i + "@" + Util.emailUrl;
					visitor = checkAgent(vt,list);
					if (visitor != null) {
						break;
					}
				}
			}
			log.severe("widgetCode"+widgetCode);
			return visitor;
		} catch (Exception e) {
			log.severe("getVisitor : " + e);
		}
		return null;
	}

	public synchronized boolean checkChatQueue(String widgetCode) {
			return chatqueue.get(widgetCode)!=null;
	}

	public synchronized void createChatQueue(String widgetCode,
			Visitor visitor, String widgetKey) {
		try {
			MessageQueue mq = new MessageQueue(visitor, widgetKey, widgetCode);
			chatqueue.put(widgetKey, mq);
			widgetmap.put(visitor.getOperator() + visitor.getVisitor(),widgetKey);
		} catch (Exception e) {
			log.severe("creatQueue : " + e);
		}
	}

	public synchronized int sessionCount(String widgetKey) {
		int i = 0;
		try {
			Iterator<MapEntry<String, MessageQueue>> iterator = tempchatqueue
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry mEntry = (Map.Entry) iterator.next();
				MessageQueue mq = (MessageQueue) mEntry.getValue();
				if (mq.getWidgetCode().equals(widgetKey)) {
					i++;
				}
			}
		} catch (Exception e) {
			log.severe("sessionCount : " + e);
		}
		return i;
	}

	public synchronized boolean putWidgetMessage(String widgetKey, Chat ch) {
		try {
			log.severe("Chat : " + ch.getMessage()+"  "+chatqueue.get(widgetKey));
			if(chatqueue.get(widgetKey) != null){
				MessageQueue mq = (MessageQueue) chatqueue.get(widgetKey);
				mq.putMessage(ch);
				chatqueue.put(widgetKey, mq);
				tempchatqueue.put(widgetKey,mq);
				return true;
			}			
		} catch (Exception e) {
			log.severe("putMessage : " + e);
		}
		return false;
	}

	public synchronized boolean putOperatorMessage(String widgetKey, Chat ch) {
		try {
			return putWidgetMessage(widgetmap.get(widgetKey).toString(), ch);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			log.severe("Enter Operator Message: " + e.getMessage());
			log.severe(stacktrace);
			
		}
		return false;
	}

	public synchronized long lastMessageId(String widgetKey) {
		try {
			if(chatqueue.get(widgetKey) != null){
				MessageQueue mq = (MessageQueue) chatqueue.get(widgetKey);
				return mq.getLastId();
			}
		} catch (Exception e) {
			log.severe("Last Message : " + e);
		}
		return 0;
	}

	public synchronized List<Chat> messageList(String widgetKey, long id) {
		List<Chat> list = new ArrayList<Chat>();
		try {
			if(chatqueue.get(widgetKey) != null){
				MessageQueue mq = (MessageQueue) chatqueue.get(widgetKey);
				list = mq.getChatById(id);
			}
		} catch (Exception e) {
			log.severe("messageList : " + e);
		}
		return list;
	}

	private List<WidgetAgents> operatorList(String widgetid) {
		return VchatController.getVchatController().getOperatorList(widgetid);
	}


	private Visitor checkAgent(String vt, List<WidgetAgents> list) {
		Visitor visitor = null;
		Collections.reverse(list);
		Collections.sort(list, new Comparator<WidgetAgents>(){	
				@Override
				public int compare(WidgetAgents wa0, WidgetAgents wa1) {
					int startComparison = compare(wa0.getAgentpriority(),wa1.getAgentpriority());
					return startComparison;
				}
	 
				private int compare(long a, long b) {
				    return a < b ? -1
				         : a > b ? 1
				         : 0;
				  }
	        });
		for (WidgetAgents widgetAgents : list) {
//			for (int j = 0; j < list.size(); j++) {
//				if (widgetAgents.getAgentpriority() == j + 1) {
					final String key = widgetAgents.getAgentId() + vt;
					log.severe("key : " + key);

					if (visitormap.get(key) == null) {
						visitor = new Visitor();
						visitor.setOperator(widgetAgents.getAgentId());
						visitor.setVisitor(vt);
						visitormap.put(key, visitor);
						return visitor;
					}
				}
//			}
//		}
		return visitor;
	}

	public synchronized void removeMessageQueue() {
		try {
			List<MessageQueue> tempList = new ArrayList<MessageQueue>();
			synchronized (tempchatqueue) {
				for (MessageQueue messagequeue : tempchatqueue.values()) {
					if (messagequeue.getVisitor() != null) {
						List<Chat> chatList = messagequeue.getChatById(0);
						int diffInMin = (int) (new Date().getTime() - messagequeue
								.getLastMessageTime().getTime()) / (1000 * 60);
						if (diffInMin > 5) {
							long id = getSessionID();
							Chat chat1 = new Chat();
							for (Chat chat : chatList) {
								chat.setWidgetId(messagequeue.getWidgetCode());
								chat.setSessionID(id);
								VchatController.getVchatController()
										.insertChat(chat);
								chat1.setSessionID(id);
								chat1.setAgentId(chat.getAgentId());
								chat1.setVistorId(chat.getVistorId());
								chat1.setWidgetId(chat.getWidgetId());
								chat1.setChatTime(chat.getChatTime());
							}
							chat1.setMessage("session");
							if (messagequeue.getConversationTime() == 0) {
								chat1.setMessageCount((long) 1);
							} else {
								chat1.setMessageCount((long) (messagequeue
										.getConversationTime() / 60));
							}

							VchatController.getVchatController().insertChat(
									chat1);
							tempList.add(messagequeue);
						}
					} else {
						tempList.add(messagequeue);

					}

				}
			}
			for (MessageQueue messageQueue : tempList) {
				synchronized (tempchatqueue) {
					tempchatqueue.remove(messageQueue.getWidgetid());
				}
				synchronized (chatqueue) {
					chatqueue.remove(messageQueue.getWidgetid());
				}
				synchronized (widgetmap) {
					widgetmap.remove(messageQueue.getVisitor().getOperator()
							+ messageQueue.getVisitor().getVisitor());
				}
				synchronized (visitormap) {
					visitormap.remove(messageQueue.getVisitor().getOperator()
							+ messageQueue.getVisitor().getVisitor());
					operators.remove(messageQueue.getVisitor().getOperator()
							+ messageQueue.getVisitor().getVisitor());
				}
			}

		} catch (Exception e) {
			log.severe(e.getMessage());
		}

	}

	private long getSessionID() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmmss");
		return Long.parseLong(sdf.format(new Date()));
	}
}
