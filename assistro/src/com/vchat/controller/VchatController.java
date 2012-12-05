package com.vchat.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.vchat.beans.Chart;
import com.vchat.beans.Chat;
import com.vchat.beans.User;
import com.vchat.beans.VisitorRecord;
import com.vchat.beans.WidgetAgents;
import com.vchat.beans.WidgetContents;

public class VchatController {

	private static VchatController vchatController;
	private static final Logger log = Logger.getLogger(VchatController.class
			.getName());
	
	private VchatController() {
	}

	public static VchatController getVchatController() {
		if (vchatController == null) {
			vchatController = new VchatController();
		}
		return vchatController;
	}

	// Related to User

	@SuppressWarnings("unchecked")
	public boolean signUpUser(User usr) {
		boolean check = false;
		EntityManager em = VchatService.get().createEntityManager();

		try {
			Query query = em
					.createQuery("select user from User as user where user.email = email");
			query.setParameter("email", usr.getEmail());
			List<User> userList = query.getResultList();
			if (userList.size() == 0) {
				em.persist(usr);
				check = true;
				String subject = "assistro subscription - Welcome!";
				String message = "<!DOCTYPE HTML PUBLIC -//W3C//DTD HTML 4.0 Transitional//EN><HTML><HEAD>"
						+ "<META HTTP-EQUIV='CONTENT-TYPE' CONTENT='text/html'; charset='utf-8'><TITLE></TITLE>"
						+ "</HEAD>"
						+ "<BODY LANG='en-PH' TEXT='#000000' LINK='#0000ff' DIR='LTR'>"
						+ "<P STYLE='margin-bottom: 0.14in'>Hello and thank you for choosing assistro!</P>"
						+ "<P STYLE='margin-bottom: 0.14in'>Once installed, assistro enables you"
						+ " to chat live with your website visitors and answer questions in real"
						+ "time.  We firmly believe that your website will benefit from improved"
						+ " customer service, better quality leads, and higher conversion rates.</P>"
						+ "<P STYLE='margin-bottom: 0.14in'><BR><BR>"
						+ "</P>"
						+ "<P STYLE='margin-bottom: 0.14in'>Wondering what to do next?"
						+ "</P>"
						+ "<P STYLE='margin-bottom: 0.14in'>Log in to the dashboard <A HREF='http://www.assistro.com'>here</A>"
						+ " and then copy our code to your Website.  It’s FAST and EASY.  All"
						+ " you have to do is COPY and PASTE! "
						+ "</P>"
						+ "<P STYLE='margin-bottom: 0.14in'>You’ll be up and running in under 60 seconds.</P>"
						+ "<P STYLE='margin-bottom: 0.14in'><BR><BR></P>"
						+ "<P STYLE='margin-bottom: 0.14in'>For tips and ideas on getting the"
						+ " most from assistro, please connect with us on "
						+ "<A HREF='http://www.twitter.com/iamassistro'>Twitter</A>,</SPAN>"
						+ "<A HREF='http://www.facebook.com/assistro'> Facebook </A></SPAN>"
						+ " or LinkedIn. </P>"
						+ "<P STYLE='margin-bottom: 0.14in'>All the best!</P>"
						+ "<P STYLE='margin-bottom: 0.14in'><BR><BR>"
						+ "</P>"
						+ "<P STYLE='margin-bottom: 0.14in'>Gavin</P>"
						+ "<P STYLE='margin-bottom: 0.14in'><A NAME='_GoBack'></A>customerservice@assistro.com</P>"
						+ "<P STYLE='margin-bottom: 0.14in'><BR><BR>"
						+ "</P>"
						+ "</BODY>" + "</HTML>";
				sendEmailMessage(usr.getEmail(), message, subject);
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in signUpUser: " + e.getMessage());
		} finally {
			em.close();
		}
		return check;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		List<User> userList = new ArrayList<User>();
		EntityManager em = VchatService.get().createEntityManager();
		try {
			Query query = em.createQuery("select user from User as user");
			userList = query.getResultList();
			for (User user : userList) {
				user.isActive();
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getAllUser: " + e.getMessage());
		} finally {
			em.close();
		}
		return userList;
	}

	public User login(User user) {
		User usr = null;
		EntityManager em = VchatService.get().createEntityManager();
		try {
			Query query = em
					.createQuery("select user from User as user where user.email = email AND user.password = password");
			query.setParameter("email", user.getEmail());
			query.setParameter("password", user.getPassword());
			usr = (User) query.getSingleResult();
			if (usr != null) {
				usr.setTimestamp(new Date());
				usr = em.merge(usr);
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in login: " + e.getMessage());
		} finally {
			em.close();
		}

		return usr;
	}

	public User findById(User user) {
		EntityManager em = VchatService.get().createEntityManager();
		try {
			Query query = em
					.createQuery("select user from User as user where user.email = email ");
			query.setParameter("email", user.getEmail());
			user = (User) query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in findById: " + e.getMessage());
		} finally {
			em.close();
		}
		return user;
	}

	public User findById(String email) {
		EntityManager em = VchatService.get().createEntityManager();
		User user = null;
		try {
			Query query = em
					.createQuery("select user from User as user where user.email = email ");
			query.setParameter("email", email);
			user = (User) query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in findById: " + e.getMessage());
		} finally {
			em.close();
		}
		return user;
	}

	public User updateUser(User user) {
		User usr = findById(user);
		if (usr != null) {
			EntityManager em = VchatService.get().createEntityManager();
			em.getTransaction().begin();
			try {
				usr.setFullName(user.getFullName());
				usr.setAddress(user.getAddress());
				usr.setCity(user.getCity());
				usr.setCompany(user.getCompany());
				usr.setCountry(user.getCountry());
				usr.setState(user.getState());
				usr.setPhone(user.getPhone());
				usr.setBusinessPlan(user.getBusinessPlan());
				usr.setActive(user.isActive());
				usr.setMaxAgent(user.getMaxAgent());
				user = em.merge(usr);
				em.getTransaction().commit();
			} catch (Exception e) {
				log.severe("Error in updateUser: " + e.getMessage());
			} finally {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
		}

		return user;
	}

	public User updateUserPassword(String emailId, String password) {
		User usr = findById(emailId);
		if (usr != null) {
			EntityManager em = VchatService.get().createEntityManager();
			em.getTransaction().begin();
			try {
				usr.setPassword(password);
				usr = em.merge(usr);
				em.getTransaction().commit();
			} catch (Exception e) {
				log.severe("Error in updateUserPassword: " + e.getMessage());
			} finally {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
		}

		return usr;
	}

	// Related to Widget

	public WidgetContents findWidgetById(WidgetContents wc) {
		EntityManager em = VchatService.get().createEntityManager();
		try {
			Query query = em
					.createQuery("select wc from WidgetContents as wc where wc.widgetId = widgetId");
			query.setParameter("widgetId", wc.getWidgetId());
			wc = (WidgetContents) query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in findWidgetById: " + e.getMessage());
		} finally {
			em.close();
		}

		return wc;
	}

	public void addWidgetContents(WidgetContents wc) {
		EntityManager em = VchatService.get().createEntityManager();
		try {
			em.persist(wc);
		} catch (Exception e) {
			log.severe("Error in addWidgetContents: " + e.getMessage());
		} finally {
			em.close();
		}

	}

	public WidgetContents updateWidget(WidgetContents wc) {
		EntityManager em = VchatService.get().createEntityManager();
		WidgetContents wc1 = findWidgetById(wc);
		if (wc1 != null) {
			em.getTransaction().begin();
			try {
				wc1.setColstate(wc.getColstate());
				wc1.setErrorState(wc.getErrorState());
				wc1.setFooter(wc.getFooter());
				wc1.setHeaderLogo(wc.getHeaderLogo());
				wc1.setmPanel(wc.getmPanel());
				wc1.setOfflineHeader(wc.getOfflineHeader());
				wc1.setOffLinePanel(wc.getOffLinePanel());
				wc1.setOnlineHeader(wc.getOnlineHeader());
				wc1.setRules(wc.getRules());
				wc1.setShowMessage(wc.isShowMessage());
				wc1.setInPanel(wc.getInPanel());
				wc1.setProActiveChat(wc.isProActiveChat());
				wc1.setOfflineEmailAdmin(wc.getOfflineEmailAdmin());
				wc1.setWidgetStyle(wc.getWidgetStyle());
				wc = em.merge(wc1);
				em.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				em.close();
			}
		}
		return wc;
	}

	public WidgetContents getWidgetData(String id) {
		EntityManager em = VchatService.get().createEntityManager();
		WidgetContents wc = null;
		try {
			Query query = em
					.createQuery("select wc from WidgetContents as wc where wc.widgetId = widgetId ");
			query.setParameter("widgetId", id);
			wc = (WidgetContents) query.getSingleResult();
			wc.getFooter();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getWidgetData: " + e.getMessage());
		} finally {
			em.close();
		}

		return wc;
	}

	// Related To Chat

	public void insertChat(final Chat chat) {

		EntityManager em = VchatService.get().createEntityManager();
		try {
			em.persist(chat);
		} catch (Exception e) {
			log.severe("Error in insertChat: " + e.getMessage());
		} finally {
			em.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Chat> getChatSessions(String id) {
		EntityManager em = VchatService.get().createEntityManager();
		List<Chat> chatList = new ArrayList<Chat>();
		try {
			Query query = em
					.createQuery("select c from Chat as c where c.widgetId = widgetId AND c.message = message");
			query.setParameter("widgetId", id);
			query.setParameter("message", "session");
			chatList = (List<Chat>) query.getResultList();
			for (Chat chat : chatList) {
				chat.getMessage();
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getChatSessions: " + e.getMessage());
		} finally {
			em.close();
		}
		return chatList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Chat> getUserChats(String id,long sessionId) {
		EntityManager em = VchatService.get().createEntityManager();
		List<Chat> chatList = new ArrayList<Chat>();
		try {
			Query query = em
					.createQuery("select c from Chat as c where c.widgetId = widgetId AND c.sessionID = sessionID");
			query.setParameter("widgetId", id);
			query.setParameter("sessionID", sessionId);
			chatList = (List<Chat>) query.getResultList();
			for (Chat chat : chatList) {
				chat.getSessionID();
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getChatSessions: " + e.getMessage());
		} finally {
			em.close();
		}
		return chatList;
	}
	
	
	public Chat getChatObj(String id) {
		EntityManager em = VchatService.get().createEntityManager();
		Chat chat = new Chat();
		try {
			Query query = em
					.createQuery("select c from Chat as c where c.widgetId = widgetId");
			query.setParameter("widgetId", id);
			chat = (Chat) query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getting chat object: " + e.getMessage());
		} finally {
			em.close();
		}
		return chat;
	}

	// Related To Widget Agents/Operators
	@SuppressWarnings("unchecked")
	public List<WidgetAgents> getAgent(String widId) {
		EntityManager em = VchatService.get().createEntityManager();
		List<WidgetAgents> agentList = null;
		try {
			Query query = em
					.createQuery("select operator from WidgetAgents as operator where operator.widgetId = widgetId");
			query.setParameter("widgetId", widId);
			agentList = query.getResultList();
			for (WidgetAgents widgetAgents : agentList) {
				widgetAgents.getAgentName();
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getAgent: " + e.getMessage());
		} finally {
			em.close();
		}
		return agentList;
	}

	public WidgetAgents getAgent(WidgetAgents wa) {
		EntityManager em = VchatService.get().createEntityManager();
		WidgetAgents operator = null;
		try {
			Query query = em
					.createQuery("select operator from WidgetAgents as operator where operator.widgetId = widgetId and operator.agentId = agentId");
			query.setParameter("widgetId", wa.getWidgetId());
			query.setParameter("agentId", wa.getAgentId());
			operator = (WidgetAgents) query.getSingleResult();

		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getAgent: " + e.getMessage());
		} finally {
			em.close();
		}
		return operator;
	}

	public boolean getAgentById(String widId) {
		EntityManager em = VchatService.get().createEntityManager();
		boolean check = false;
		try {
			Query query = em
					.createQuery("select operator from WidgetAgents as operator where operator.agentId = agentId");
			query.setParameter("agentId", widId);
			if (query.getResultList().size() > 0) {
				check = true;
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getAgentById: " + e.getMessage());
		} finally {
			em.close();
		}
		return check;
	}

	public WidgetAgents getSingleAgent(String widId) {
		EntityManager em = VchatService.get().createEntityManager();
		WidgetAgents wa = null;
		try {
			Query query = em
					.createQuery("select operator from WidgetAgents as operator where operator.agentId = agentId");
			query.setParameter("agentId", widId);
			wa = (WidgetAgents) query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getAgentById: " + e.getMessage());
		} finally {
			em.close();
		}
		return wa;
	}
	
	public WidgetAgents getChatAgent(String widId) {
		EntityManager em = VchatService.get().createEntityManager();
		WidgetAgents wa = new WidgetAgents();
		try {
			Query query = em
					.createQuery("select operator from WidgetAgents as operator where operator.widgetId = widgetId and operator.agentId = agentId");
			query.setParameter("widgetId", widId);
			query.setParameter("agentId", wa.getAgentId());
			wa = (WidgetAgents) query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getAgentById: " + e.getMessage());
		} finally {
			em.close();
		}
		return wa;
	}

	public WidgetAgents getAgentByPriority(String widId, long priority) {
		EntityManager em = VchatService.get().createEntityManager();
		WidgetAgents wa = null;
		try {
			Query query = em
					.createQuery("select wa from WidgetAgents as wa where wa.widgetId = widgetId and wa.agentpriority = agentpriority");
			query.setParameter("widgetId", widId);
			query.setParameter("agentpriority", priority);
			wa = (WidgetAgents) query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getAgentById: " + e.getMessage());
		} finally {
			em.close();
		}
		return wa;
	}

	@SuppressWarnings("unchecked")
	public boolean addAgent(WidgetAgents wa, int limit) {
		boolean check = false;
		boolean persist = getAgentById(wa.getAgentId());

		EntityManager em = VchatService.get().createEntityManager();
		try {
			Query query = em
					.createQuery("select wa from WidgetAgents as wa where wa.widgetId = widgetId ");
			query.setParameter("widgetId", wa.getWidgetId());
			List<WidgetAgents> agentList = query.getResultList();
			if (agentList.size() < limit && !persist) {
				em.persist(wa);
				check = true;
			}

		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in addAgent: " + e.getMessage());
		} finally {
			em.close();
		}

		return check;
	}

	
	public void updatePriorityAgent(WidgetAgents wa) {
		WidgetAgents operator2 = getAgent(wa);
		WidgetAgents operator1 = getAgentByPriority(wa.getWidgetId(),
				wa.getAgentpriority());
		if (operator2 != null
				&& operator2.getAgentpriority() != wa.getAgentpriority()) {
			if (operator1 != null) {
				operator1.setAgentpriority(operator2.getAgentpriority());
				operator1.setAgentName(operator2.getAgentName());
				updateBothAgents(operator1);
			}
			operator2.setAgentName(wa.getAgentName());
			operator2.setAgentpriority(wa.getAgentpriority());
			updateBothAgents(operator2);
		}
		else{
		operator2.setAgentName(wa.getAgentName());
		updateBothAgents(operator2);
		}
	}

	private void updateBothAgents(WidgetAgents wa) {
		EntityManager em = VchatService.get().createEntityManager();
		try {
			em.merge(wa);
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in addAgent: " + e.getMessage());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public boolean updateAgentPresence(String agentId, boolean status) {
		EntityManager em = VchatService.get().createEntityManager();
		try {
			Query query = em
					.createQuery("select wa from WidgetAgents as wa where wa.agentId = agentId ");
			query.setParameter("agentId", agentId);
			List<WidgetAgents> list = (List<WidgetAgents>) query
					.getResultList();
			for (WidgetAgents widgetAgents : list) {
			
				if (widgetAgents.isAgentStatus() != status) {
					widgetAgents.setAgentStatus(status);
					em.merge(widgetAgents);
				}
			}
		} catch (NoResultException nre) {
			status = false;
		} catch (Exception e) {
			log.severe("Error in updateAgentPresence: " + e.getMessage());
			status = false;
		} finally {
			em.close();
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public List<WidgetAgents> getOperatorList(String wid) {
		EntityManager em = VchatService.get().createEntityManager();
		List<WidgetAgents> list = new ArrayList<WidgetAgents>();
		try {
			Query query = em
					.createQuery("select wa from WidgetAgents as wa where wa.widgetId = widgetId");
			query.setParameter("widgetId", wid);
			List<WidgetAgents> list1 = (List<WidgetAgents>) query
					.getResultList();
			for (WidgetAgents widgetAgents : list1) {
				if (widgetAgents.isAgentStatus()) {
					list.add(widgetAgents);
				}
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getOperatorList: " + e.getMessage());
		} finally {
			em.close();
		}
		return list;
	}

	public void delAgent(WidgetAgents wa) {
		EntityManager em = VchatService.get().createEntityManager();
		try {
			Query query = em
					.createQuery("DELETE FROM WidgetAgents as wa WHERE wa.agentId = agentId");
			query.setParameter("agentId", wa.getAgentId());
			query.executeUpdate();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in delAgent: " + e.getMessage());
		} finally {
			em.close();
		}
	}
	
	public WidgetAgents updateAgent(WidgetAgents wa) {
		WidgetAgents wa1 = findAgentById(wa);
		if (wa1 != null) {
			EntityManager em = VchatService.get().createEntityManager();
			em.getTransaction().begin();
			try {
				wa1.setAgentId(wa.getAgentId());
				wa1.setAgentImageURL(wa.getAgentImageURL());
				wa1.setAgentName(wa.getAgentName());
				wa1.setAgentpriority(wa.getAgentpriority());
				wa1.setWidgetId(wa.getWidgetId());
				wa = em.merge(wa1);
				em.getTransaction().commit();
			} catch (Exception e) {
				log.severe("Error in updateUser: " + e.getMessage());
			} finally {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
		}

		return wa;
	}
	
	public WidgetAgents findAgentById(WidgetAgents wa) {
		EntityManager em = VchatService.get().createEntityManager();
		try {
			Query query = em
					.createQuery("select wa from WidgetAgents as wa where wa.getAgentId = getAgentId ");
			query.setParameter("getAgentId", wa.getAgentId());
			wa = (WidgetAgents) query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in findById: " + e.getMessage());
		} finally {
			em.close();
		}
		return wa;
	}

	
	@SuppressWarnings("unchecked")
	public List<String> getPriorityList(String wid) {
		EntityManager em = VchatService.get().createEntityManager();
		List<String> list = new ArrayList<String>();
		try {
			Query query = em
					.createQuery("select wa from WidgetAgents as wa where wa.widgetId = widgetId");
			query.setParameter("widgetId", wid);
			List<WidgetAgents> list1 = (List<WidgetAgents>) query
					.getResultList();
			for (WidgetAgents widgetAgents : list1) {
				list.add(widgetAgents.getAgentpriority() + "");
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getOperatorList: " + e.getMessage());
		} finally {
			em.close();
		}
		return list;
	}

	// Related to Chart

	public Chart updateChart(Chart chart) {
		EntityManager em = VchatService.get().createEntityManager();
		Chart chart1 = getChartData(chart.getWidgetId(), chart.getDay());
		try {
			em.getTransaction().begin();
			if (chart1 != null) {
				chart1.setChated(chart.getChated());
				chart1.setSawOnline(chart.getSawOnline());
				chart1.setVisitorOnSite(chart.getVisitorOnSite());
				chart1 = em.merge(chart1);
			} else {
				em.persist(chart);
				chart1 = chart;
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			log.severe("Error in updateChart: " + e.getMessage());
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}

		return chart1;
	}

	public Chart getChartData(String id, String day) {
		EntityManager em = VchatService.get().createEntityManager();
		Chart chart = null;
		try {
			Query query = em
					.createQuery("select ch from Chart as ch where ch.widgetId = widgetId and ch.day = day");
			query.setParameter("widgetId", id);
			query.setParameter("day", day);
			chart = (Chart) query.getSingleResult();
			chart.getWidgetId();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getChartData: " + e.getMessage());
		} finally {
			em.close();
		}

		return chart;
	}

	@SuppressWarnings("unchecked")
	public List<Chart> getChartData(String id) {
		EntityManager em = VchatService.get().createEntityManager();
		List<Chart> list = null;
		try {
			Query query = em
					.createQuery("select ch from Chart as ch where ch.widgetId = widgetId");
			query.setParameter("widgetId", id);
			list = (List<Chart>) query.getResultList();
			for (Chart chart : list) {
				chart.getChated();
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getChartData: " + e.getMessage());
		} finally {
			em.close();
		}

		return list;
	}

	public User getUserWidget(String id) {
		EntityManager em = VchatService.get().createEntityManager();
		User usr = new User();
		try {
			Query query = em
					.createQuery("select user from User as user where user.widgetId = widgetId");
			query.setParameter("widgetId", id);
			usr = (User) query.getSingleResult();
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getting user data: " + e.getMessage());
		} finally {
			em.close();
		}
		return usr;
	}

	// related to email

	public void sendEmail(String emailId) {
		String msgBody = "Hi iamassistro user! Someone has requested that the password for the for the account with the username below be reset."
				+ "If you didn't request this, no further action is needed. If you did, follow the link below (or copy the URL into your browser window) to reset the password:"
				+ " http://www.assistro.com/pages/passwordRec.html?email="
				+ emailId + " Have a nice day," + "Support";
		String subject = "Forget Password";
		sendEmailMessage(emailId, msgBody, subject);
	}

	public void sendEmailMessage(String email, String message, String subject) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("customerservice@assistro.com", "assistro"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email, "Your admin"));
			msg.setSubject(subject);			
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(message, "text/html");
			mp.addBodyPart(htmlPart);
			msg.setContent(mp);
		    	 
			Transport.send(msg);

		} catch (Exception e) {
			log.severe("Error in sending email : " + e);
		}
	}
	
	
	public void sendtoAdmin(String email, String message, String subject) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("customerservice@assistro.com", "assistro"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"customerservice@assistro.com", "Your admin"));
			msg.setSubject(email +":"+ subject);			
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(message, "text/html");
			mp.addBodyPart(htmlPart);
			msg.setContent(mp);
		    	 
			Transport.send(msg);

		} catch (Exception e) {
			log.severe("Error in sending email : " + e);
		}
	}
	
	public void addVisitorRecord(VisitorRecord vc) {
//		VisitorRecord vc1 = null;
		EntityManager em = VchatService.get().createEntityManager();
		try {
//			Query query = em
//					.createQuery("select vc from VisitorRecord as vc where vc.visitorEmail = visitorEmail");
//			query.setParameter("visitorEmail", vc.getVisitorEmail());
//			vc1 = (VisitorRecord) query.getSingleResult();
			if (!vc.getVisitorName().isEmpty() || !vc.getVisitorEmail().isEmpty()) {
				vc = em.merge(vc);
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in adding visitor record	: " + e.getMessage());
		} finally {
			em.close();
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<VisitorRecord> getUserRecord(String wId){
		EntityManager em = VchatService.get().createEntityManager();
		List<VisitorRecord> visRec = null;
		try {			
			Query query = em
					.createQuery("select vc from VisitorRecord as vc where vc.widgetId = widgetId");
			query.setParameter("widgetId", wId);
			visRec =  query.getResultList();
			for (VisitorRecord vr : visRec) {
				vr.getVisitorName();
			}
		} catch (NoResultException nre) {

		} catch (Exception e) {
			log.severe("Error in getting visitor record	: " + e.getMessage());
		} finally {
			em.close();
		}
		return visRec;
	}

}
