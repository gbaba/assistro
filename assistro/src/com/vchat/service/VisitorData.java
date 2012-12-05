package com.vchat.service;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vchat.beans.VisitorRecord;
import com.vchat.controller.VchatController;

/**
 * Servlet implementation class TestFile
 */
@SuppressWarnings("serial")
public class VisitorData extends HttpServlet {
	private static final Logger log = Logger.getLogger(VisitorData.class
			.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VisitorData() {
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
		
		String username = request.getParameter("beforeName");
        String email = request.getParameter("beforeEmail");
        String code = request.getParameter("code");
 
        try {
        	VisitorRecord vr = new VisitorRecord();
        	vr.setVisitorName(username);
        	vr.setVisitorEmail(email);
        	vr.setWidgetId(code);    	
        	VchatController.getVchatController().addVisitorRecord(vr);
 
        } catch (Exception e) {
        	log.severe("Error in adding Visitor Record : "+e);
        }
     }
	
}