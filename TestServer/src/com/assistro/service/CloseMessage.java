package com.assistro.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.assistro.beans.Chart;
import com.assistro.beans.Chat;
import com.assistro.controller.VMessageController;
import com.assistro.controller.VchatController;

/**
 * Servlet implementation class TestFile
 */
@SuppressWarnings("serial")
public class CloseMessage extends HttpServlet {
	private static final XMPPService xmppService = XMPPServiceFactory
			.getXMPPService();
	private static final Logger log = Logger.getLogger(CloseMessage.class
			.getName());
	private String widgetCode;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CloseMessage() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		sendMessage(request, response);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(new String(
				"{\"code\":1,\"message\":\"ok sent!\",\"data\":\"ok\"}"));
		printWriter.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		sendMessage(request, response);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(new String(
				"{\"code\":1,\"message\":\"ok sent!\",\"data\":\"ok\"}"));
		printWriter.flush();
	}



	private void sendMessage(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final String chat = request.getParameter("refresh");
			final String widgetid = request.getParameter("widget_code");
			final String cookie = request.getParameter("widgetid");

			
			widgetCode = widgetid;
			final String widgetKey = widgetid + cookie;
			if (!chat.isEmpty() && !widgetid.isEmpty() && !cookie.isEmpty()) {
				Visitor visitor = VMessageController.getVMessageController()
						.getVisitor(widgetid, cookie);
				if (visitor != null) {
					if (!VMessageController.getVMessageController()
							.checkChatQueue(widgetKey)) {
						VMessageController
								.getVMessageController()
								.createChatQueue(widgetCode, visitor, widgetKey);
					}
					

					Chat ch = new Chat();
					ch.setWidgetId(widgetKey);
					ch.setAgentId(visitor.getOperator());
					ch.setVistorId(visitor.getVisitor());
					ch.setMessage(chat);
					ch.setChatTime(new Date());
					ch.setMessageTime(new Date());
					ch.setMessageType("send");
					sendMesage(widgetKey, ch, visitor);
				}
			}

		} catch (Exception e) {
			log.severe("Error in sendMessage : " + e);
			e.printStackTrace();
		}
	}

	private void sendMesage(final String widgetKey, Chat ch, Visitor visitor) {
		try {
			JID sender = new JID(visitor.getVisitor());
			JID reciever = new JID(visitor.getOperator());


			Message msg = new MessageBuilder().withFromJid(sender)
					.withRecipientJids(reciever).withBody(ch.getMessage())
					.build();
			//SendResponse success = xmppService.sendMessage(msg);
			xmppService.sendMessage(msg);

//			if (success.getStatusMap().get(reciever) == SendResponse.Status.SUCCESS) {
//				VMessageController.getVMessageController().putWidgetMessage(
//						widgetKey, ch);
//			}

		} catch (Exception e) {
			log.severe("Error in chat : " + e);
		}
	}

}