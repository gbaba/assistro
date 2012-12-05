package com.vchat.service;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vchat.beans.WidgetAgents;
import com.vchat.beans.WidgetContents;
import com.vchat.controller.VchatController;

/**
 * Servlet implementation class TestFile
 */
@SuppressWarnings("serial")
public class SendEmail extends HttpServlet {
	private static final Logger log = Logger.getLogger(SendEmail.class
			.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendEmail() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		sendMessage(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		sendMessage(request, response);
	}

	private void sendMessage(HttpServletRequest request,
			HttpServletResponse response) {
		
		String subject = request.getParameter("subject");
        String email = request.getParameter("email");
        String msgBody = request.getParameter("message");
        String code = request.getParameter("code");
        String operator ="";
        long priority = 0;
        for (WidgetAgents element : VchatController.getVchatController().getAgent(code)) {
        	if(priority == 0 || priority >= element.getAgentpriority()){
        		operator = element.getAgentId();
        		priority = element.getAgentpriority();
        	}
    	}
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
    
 
        try {
        	WidgetContents wc = VchatController.getVchatController().getWidgetData(code);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("customerservice@assistro.com",
            		"Admin"));
            if(wc.getOfflineEmailAdmin()!= null && !wc.getOfflineEmailAdmin().isEmpty()){
            	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(wc.getOfflineEmailAdmin(), "Agent"));
            }
            else {
            	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(operator, "Agent"));
            }                       
            msg.setSubject(email);
            msg.setText(msgBody);
            Transport.send(msg);
 
        } catch (Exception e) {
        	log.severe("Error in sending email : "+e);
        }
     }
	
}