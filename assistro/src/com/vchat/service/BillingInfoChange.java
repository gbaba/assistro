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
public class BillingInfoChange extends HttpServlet {
	private static final Logger log = Logger.getLogger(BillingInfoChange.class
			.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillingInfoChange() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handleBillingInfo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handleBillingInfo(request, response);
	}

	private void handleBillingInfo(HttpServletRequest request,
			HttpServletResponse response) {
		
		String subscriberIds = request.getParameter("subscriber_ids");
		log.severe(subscriberIds);
        try {
        	
        } catch (Exception e) {
        	log.severe(" : "+e);
        }
     }
	
}