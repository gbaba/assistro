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
import com.assistro.beans.WidgetContents;
import com.assistro.controller.VMessageController;
import com.assistro.controller.VchatController;

/**
 * Servlet implementation class TestFile
 */
@SuppressWarnings("serial")
public class SendMessage extends HttpServlet {
	private static final XMPPService xmppService = XMPPServiceFactory
			.getXMPPService();
	private static final Logger log = Logger.getLogger(SendMessage.class
			.getName());
	private String widgetCode;
	private String infoString = "";
	private boolean first = false;
	private boolean premtive = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMessage() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		setUserInfo(request, response);
		sendMessage(request, response);
		response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Content-Type", "application/x-www-form-urlencoded");
		response.setHeader("Accept", "application/json, text/javascript");
		
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
		setUserInfo(request, response);
		sendMessage(request, response);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Content-Type", "application/x-www-form-urlencoded");
		response.setHeader("Accept", "application/json, text/javascript");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(new String(
				"{\"code\":1,\"message\":\"ok sent!\",\"data\":\"ok\"}"));
		printWriter.flush();
	}

	private void setUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String browser = request.getHeader("user-agent");
		String browsername = "Other";
		String browserversion = "";
		if (browser != null) {
			if (browser.contains("MSIE")) {
				String subsString = browser.substring(browser.indexOf("MSIE"));
				String Info[] = (subsString.split(";")[0]).split(" ");
				browsername = Info[0];
				browserversion = Info[1];
			} else if (browser.contains("Firefox")) {

				String subsString = browser.substring(browser
						.indexOf("Firefox"));
				String Info[] = (subsString.split(" ")[0]).split("/");
				browsername = Info[0];
				browserversion = Info[1];
			} else if (browser.contains("Chrome")) {

				String subsString = browser
						.substring(browser.indexOf("Chrome"));
				String Info[] = (subsString.split(" ")[0]).split("/");
				browsername = Info[0];
				browserversion = Info[1];
			} else if (browser.contains("Opera")) {

				String subsString = browser.substring(browser.indexOf("Opera"));
				String Info[] = (subsString.split(" ")[0]).split("/");
				browsername = Info[0];
				browserversion = Info[1];
			} else if (browser.contains("Safari")) {

				String subsString = browser
						.substring(browser.indexOf("Safari"));
				String Info[] = (subsString.split(" ")[0]).split("/");
				browsername = Info[0];
				browserversion = Info[1];

			}
		}
		infoString = " Browser : " + browsername + " " + browserversion + "\n"
				+ "IP : " + (String) request.getRemoteAddr() + "\n"
				+ " URL: " + (String) request.getHeader("Origin") + "\n"+ " Country: "
				+ (String) request.getHeader("X-AppEngine-Country")+ "\n"+ " City: "
						+ (String) request.getHeader("X-AppEngine-City");
	}

	private void sendMessage(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			final String chat = request.getParameter("message");
			final String widgetid = request.getParameter("widget_code");
			final String cookie = request.getParameter("widgetid");
			String countryName = request.getHeader("X-AppEngine-Country");
			String cityName = request.getHeader("X-AppEngine-City");
			System.out.println("Widget_Code: "+widgetid);
			System.out.println("Widget_Id: "+cookie);
			
			widgetCode = widgetid;
			final String widgetKey = widgetid + cookie;			
			if(!chat.isEmpty() && !widgetid.isEmpty() && !cookie.isEmpty()){
				Visitor visitor = VMessageController.getVMessageController()
						.getVisitor(widgetid, cookie);
				if (visitor != null) {
					if (!VMessageController.getVMessageController()
							.checkChatQueue(widgetKey)) {
						VMessageController
								.getVMessageController()
								.createChatQueue(widgetCode, visitor, widgetKey);
						first = true;
						if (isPremtiveActive()) {
							premtive = true;
						}						
						
						updateWid(VchatController.getVchatController()
								.getChartData(
										widgetCode,
										new SimpleDateFormat("ddMMyyyy")
												.format(new Date())));
					}
					
					
					Chat ch = new Chat();
					ch.setWidgetId(widgetKey);
					ch.setAgentId(visitor.getOperator());
					ch.setVistorId(visitor.getVisitor());
					ch.setMessage(chat);
					ch.setChatTime(new Date());
					ch.setMessageTime(new Date());
					ch.setCountryName(countryName);
					ch.setCityName(cityName);
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

			if (first) {
				Message msg = new MessageBuilder().withFromJid(sender)
						.withRecipientJids(reciever).withBody(infoString)
						.build();
				

				xmppService.sendMessage(msg);
				first = false;

			}
			if (premtive) {
				Message msg = new MessageBuilder().withFromJid(sender)
						.withRecipientJids(reciever)
						.withBody("You have a visitor for chat...").build();
				xmppService.sendMessage(msg);

				premtive = false;
				// return;
			}

			Message msg = new MessageBuilder().withFromJid(sender)
					.withRecipientJids(reciever).withBody(ch.getMessage())
					.build();
			SendResponse success = xmppService.sendMessage(msg);

			if (success.getStatusMap().get(reciever) == SendResponse.Status.SUCCESS) {
				VMessageController.getVMessageController().putWidgetMessage(
						widgetKey, ch);
			}

		} catch (Exception e) {
			log.severe("Error in chat : " + e);
		}
	}

	public void updateWid(Chart chart) {
		if (chart != null) {
			chart.setChated(chart.getChated() + 1);
			VchatController.getVchatController().updateChart(chart);
		}

	}

	private boolean isPremtiveActive() {
		WidgetContents wc = VchatController.getVchatController().getWidgetData(
				widgetCode);
		if (wc.isProActiveChat()) {
			return wc.isProActiveChat();
		}
		return false;
	}
}