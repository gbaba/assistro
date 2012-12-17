package com.assistro.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.assistro.beans.User;
import com.assistro.controller.VchatController;
import com.assistro.util.Util;

public class LicenceService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(QueueService.class
			.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LicenceService() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest();
	}

	private void processRequest() {
		List<User> allUsers = VchatController.getVchatController().getAllUser();
		for (User user : allUsers) {
			spreadlyService(user);
		}
	}

	public void spreadlyService(User user) {
		try {
			String url1 = "https://spreedly.com/api/v4/assistro-test/subscribers/"
					+ DigestUtils.md5Hex(user.getEmail()) + ".xml";
			URL url = new URL(url1);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			setBasicAuth(connection,
					"b939a6536fc6429ab21e36f3888ee69387c70020", "");
			connection.connect();
			connection.setReadTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			if (in != null) {
				SAXReader reader = new SAXReader();
				Document document = reader.read(in);
				Element root = document.getRootElement();
				String uAct = root.element("active").getText();
				if (user.isActive() != Boolean.parseBoolean(uAct)) {
					user.setActive(Boolean.parseBoolean(uAct));
				}
				String uPlan = root.element("subscription-plan-version")
						.element("name").getText();
				user.setBusinessPlan(uPlan);
				int maxAgent = Integer.parseInt(Util.planAgent.get(uPlan));
				user.setMaxAgent(maxAgent);
				VchatController.getVchatController().updateUser(user);
			}
		} catch (Exception e) {
			log.severe("spreadlyService" + e);
		}
	}

	public static void setBasicAuth(HttpURLConnection connection,
			String username, String password) {
		StringBuilder buf = new StringBuilder(username);
		buf.append(':');
		buf.append(password);
		byte[] bytes = null;
		try {
			bytes = buf.toString().getBytes("ISO-8859-1");
		} catch (java.io.UnsupportedEncodingException uee) {
			assert false;
		}
		String header = "Basic " + Base64.encodeBase64String(bytes);
		connection.setRequestProperty("Authorization", header);
	}
}