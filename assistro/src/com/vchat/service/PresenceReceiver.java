package com.vchat.service;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.Presence;
import com.google.appengine.api.xmpp.PresenceType;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.vchat.controller.VchatController;
import com.vchat.util.Util;

/**
 * Servlet implementation class TestFile
 */
@SuppressWarnings("serial")
public class PresenceReceiver extends HttpServlet {


	private static final XMPPService xmppService = XMPPServiceFactory
			.getXMPPService();
	private static final Logger log = Logger.getLogger(PresenceReceiver.class
			.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PresenceReceiver() {
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
			Presence presence = xmppService.parsePresence(request);
			String from = presence.getFromJid().getId().split("/")[0];
				if (presence.getPresenceType() == PresenceType.AVAILABLE) {
						updatePresenceCheck(from, "true", true);
				} else if (presence.getPresenceType() == PresenceType.UNAVAILABLE) {
						updatePresenceCheck(from, "false", false);
				}

		} catch (Exception e) {
			log.severe(" processRequest " + e.getMessage());
		}
	}

	private void updatePresenceCheck(String from, String status, boolean status1) {
		VchatController.getVchatController().updateAgentPresence(from, status1);
	}

}