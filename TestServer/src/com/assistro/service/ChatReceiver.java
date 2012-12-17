package com.assistro.service;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.assistro.beans.Chat;
import com.assistro.controller.VMessageController;
import com.assistro.util.Util;

/**
 * Servlet implementation class TestFile
 */
@SuppressWarnings("serial")
public class ChatReceiver extends HttpServlet {

	private static final XMPPService xmppService = XMPPServiceFactory
			.getXMPPService();
	private static final Logger log = Logger.getLogger(ChatReceiver.class
			.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChatReceiver() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			boolean check = true;
			Message message = xmppService.parseMessage(request);
			JID sender = message.getFromJid();
			JID reciever[] = message.getRecipientJids();

			for (JID jid : reciever) {
				Chat chat = new Chat();
				chat.setAgentId(sender.getId().split("/")[0]);
				chat.setVistorId(jid.getId().split("/")[0]);
				chat.setMessage(message.getBody());
				chat.setChatTime(new Date());
				chat.setMessageTime(new Date());
				chat.setMessageType("receive");
				final String key = chat.getAgentId() + chat.getVistorId();

				if (!VMessageController.getVMessageController()
						.putOperatorMessage(key, chat)) {
					Message msg = new MessageBuilder().withFromJid(jid)
							.withRecipientJids(sender)
							.withBody("Sorry recipient is busy pleas wait...")
							.build();
					xmppService.sendMessage(msg);

				}
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

	}
}