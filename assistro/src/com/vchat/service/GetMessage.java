package com.vchat.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vchat.beans.Chat;
import com.vchat.beans.WidgetAgents;
import com.vchat.controller.VMessageController;
import com.vchat.controller.VchatController;


/**
 * Servlet implementation class TestFile
 */
@SuppressWarnings("serial")
public class GetMessage extends HttpServlet {
	private static final Logger log = Logger.getLogger(GetMessage.class
			.getName());
	WidgetAgents wa = null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMessage() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		sendResponse(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		sendResponse(request, response);
	}

	private void sendResponse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Content-Type", "application/x-www-form-urlencoded");
		response.setHeader("Accept", "application/json, text/javascript");
		
		try {
			String widgetid = request.getParameter("widget_code");
			String cookie = request.getParameter("widgetid");
			long lastId = Long.parseLong(request.getParameter("last_id"));
			String str6 = null;
			final String widgetKey = widgetid + cookie;
			
				if (VMessageController.getVMessageController().checkChatQueue(widgetKey)) {
					str6 = chatReponse(widgetid + cookie, lastId);
				}
		
			if (str6 == null) {
				str6 = new String(
						"{\"code\": 0,\"message\": \"No message available!\",\"data\": {\"session\": \""
								+ widgetid
								+ "\",\""
								+ "last_id"
								+ "\": "
								+ lastId + ",\"timestamp\": 0,\"data\": null}}");
			}
			PrintWriter printWriter;
			printWriter = response.getWriter();
			printWriter.write(str6);
			printWriter.flush();
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
	}

	private String chatReponse(final String widgetId, long lastId) {
		String str6 = "";
		boolean check = true;
		String data = "";
		List<Chat> list = new ArrayList<Chat>();
	
			if (lastId > VMessageController.getVMessageController().lastMessageId(widgetId)) {
				lastId = 0;
			}

			list = VMessageController.getVMessageController().messageList(widgetId, lastId);
	
		if (list.size() == 0) {
			return null;
		} else {
			for (Chat chat : list) {
				String name = "User";
				String agentPhoto = "Agent";
				wa = VchatController.getVchatController()
						.getSingleAgent(chat.getAgentId());
				if (chat.getMessageType().equalsIgnoreCase("send")) {
					name = chat.getVistorId().split("@")[0].toString();
					//agentPhoto = wa.getAgentImageURL();
				} else {					 
					 name =wa.getAgentName();
					 agentPhoto = wa.getAgentImageURL();
					 if(name==null){
						name = chat.getAgentId().split("@")[0].toString();
					 }
				}
				if (check) {
					data = "{\"message_id\": \"chatmessage_id\",\"message_text\": \""
						+ chat.getMessage()
						+ "\",\"message_timestamp\": \""
						+ getLastMessageTime(chat.getMessageTime())
						+ "\",\"l_id\": \""
						+ VMessageController.getVMessageController().lastMessageId(widgetId)
						+ "\",\"message_sender\": \"" + name
						+ "\",\"sender_photo\": \"" + agentPhoto + "\"}";
					check = false;
				} else {
					data = data
							+ ","
							+ "{\"message_id\": \"chatmessage_id\",\"message_text\": \""
							+ chat.getMessage()
							+ "\",\"message_timestamp\": \""
							+ getLastMessageTime(chat.getMessageTime())
							+ "\",\"l_id\": \""
							+ VMessageController.getVMessageController().lastMessageId(widgetId)
							+ "\",\"message_sender\": \"" + name
							+ "\",\"sender_photo\": \"" + agentPhoto + "\"}";
				}
			}
			str6 = new String(
					"{\"code\": 1,\"message\": \"Message found!\",\"data\": {\"session\": \"6faa3a86b62ba89f8eb545d449546815\",\"last_id\":\""
							+ VMessageController.getVMessageController().lastMessageId(widgetId)
							+ "\",\"timestamp\": 0,\"data\": [" + data + "]}}");
		}
		return str6;
	}

	private String getLastMessageTime(Date date) {
		String str = new SimpleDateFormat("ddMMyyyyhhmmssSSS").format(date);
		return str;
	}
}