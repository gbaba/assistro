package com.assistro.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.assistro.beans.Chat;
import com.assistro.controller.VMessageController;
import com.assistro.controller.VchatController;
import com.assistro.util.Util;

/**
 * Servlet implementation class TestFile
 */
@SuppressWarnings("serial")
public class QueueService extends HttpServlet {

	private static final Logger log = Logger.getLogger(QueueService.class
			.getName());

	public QueueService() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest();
	}

	private void processRequest() {
		VMessageController.getVMessageController().removeMessageQueue();
	}

}