package com.vchat.managedbeans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.FacesEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.vchat.beans.Blog;
import com.vchat.beans.BlogComment;
import com.vchat.beans.Chat;
import com.vchat.beans.User;
import com.vchat.beans.WidgetAgents;
import com.vchat.beans.WidgetContents;
import com.vchat.controller.VchatController;
import com.vchat.util.MyAgents;
import com.vchat.util.Util;

@ManagedBean(name = "userBean")
@RequestScoped
public class UserBean {
	private static final Logger log = Logger
			.getLogger(UserBean.class.getName());

	private User user;
	private User loggedInUser;
	private boolean rememberUser;
	private WidgetAgents operator;
	private WidgetAgents opView;
	// used to update business plan
	private Map<String, String> userPlans;
	private String billingPlan;
	private String planInfo;

	private List<MyAgents> userOperator;
	private List<String> agentPriority;
	private List<String> updatePriority;
	private List<Chat> userChatHistory;
	private List<Chat> userChatMessages;
	private Chat chatSession;

	// used for forgetPassword
	private String forgetEmail;
	private boolean chatLimit;
	private String password;
	private String conPass;

	// user message values
	private String fullName;
	private String emailAddress;
	private String phoneNo;
	private String message;
	
	private boolean termsConditions;
	
	private Blog blog;
	private String tmpVal;
	private Blog detailPost;
	private boolean checkComment;
	private BlogComment blogCom;
	private List<Blog> blogPosts;
	private List<Blog> topPosts;
	private List<BlogComment> blogComments;
	///////////////////////////////// getter and setter/////////////////////////////

	public String getBillingPlan() {
		return billingPlan;
	}

	public void setBillingPlan(String billingPlan) {
		this.billingPlan = billingPlan;
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isRememberUser() {
		return rememberUser;
	}

	public void setRememberUser(boolean rememberUser) {
		this.rememberUser = rememberUser;
	}

	public User getLoggedInUser() {
		if (loggedInUser == null) {
			loggedInUser = getFromSession();
		}
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public void clearProperties() {
		user = new User();
	}

	public Map<String, String> getUserPlans() {
		this.userPlans = Util.plan;
		return userPlans;
	}

	public void setUserPlans(Map<String, String> userPlans) {
		this.userPlans = userPlans;
	}

	public WidgetAgents getOperator() {
		if (operator == null) {
			operator = new WidgetAgents();
		}
		return operator;
	}

	public void setOperator(WidgetAgents operator) {
		this.operator = operator;
	}

	public List<String> getAgentPriority() {
		if (this.agentPriority == null) {
			creatPriority();
		}
		return agentPriority;
	}

	public Chat getChatSession() {
		return chatSession;
	}

	public void setChatSession(Chat chatSession) {
		this.chatSession = chatSession;
	}

	public void setAgentPriority(List<String> agentPriority) {
		this.agentPriority = agentPriority;
	}

	public List<String> getUpdatePriority() {
		if (this.agentPriority == null) {
			verifyPriority();
		}
		return updatePriority;
	}

	public void setUpdatePriority(List<String> updatePriority) {
		this.updatePriority = updatePriority;
	}

	public boolean isChatLimit() {
		user = getFromSession();
		if (user.getMaxAgent() == 1
				&& VchatController.getVchatController()
						.getChatSessions(DigestUtils.md5Hex(user.getEmail()))
						.size() > 10) {
			chatLimit = true;
		}
		return chatLimit;
	}

	public void setChatLimit(boolean chatLimit) {
		this.chatLimit = chatLimit;
	}

	public String getForgetEmail() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		Map<String, String> params = fCtx.getExternalContext()
				.getRequestParameterMap();
		forgetEmail = (String) params.get("email");
		return forgetEmail;
	}

	public void setForgetEmail(String forgetEmail) {
		this.forgetEmail = forgetEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConPass() {
		return conPass;
	}

	public void setConPass(String conPass) {
		this.conPass = conPass;
	}

	public void setPlanInfo(String planInfo) {
		this.planInfo = planInfo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Chat> getUserChatHistory() {
		this.userChatHistory = VchatController.getVchatController()
				.getChatSessions(
						DigestUtils.md5Hex(getFromSession().getEmail()));
		
		 Collections.sort(userChatHistory, new Comparator<Chat>(){	
				@Override
				public int compare(Chat c0, Chat c1) {
					int startComparison = compare(c0.getChatTime(),c1.getChatTime());
					return startComparison;
				}
	 
				private int compare(Date a, Date b) {
				    return a.compareTo(b)<0 ? 1
				         : a.compareTo(b)>0 ? -1
				         : 0;
				  }
	        });
		
		return this.userChatHistory;
	}

	public void setUserChatHistory(List<Chat> userChatHistory) {
		this.userChatHistory = userChatHistory;
	}
	
	public List<Chat> getUserChatMessages() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		chatSession = (Chat) session.getAttribute("chatSess");
		
		this.userChatMessages = VchatController.getVchatController()
				.getUserChats(
						DigestUtils.md5Hex(getFromSession().getEmail()),chatSession.getSessionID());
		 Collections.sort(userChatMessages, new Comparator<Chat>(){	
				@Override
				public int compare(Chat c0, Chat c1) {
					int startComparison = compare(c0.getChatTime(),c1.getChatTime());
					return startComparison;
				}
	 
				private int compare(Date a, Date b) {
				    return a.compareTo(b)<0 ? -1
				         : a.compareTo(b)>0 ? 1
				         : 0;
				  }
	        });
		return this.userChatMessages;
	}

	public void setUserChatMessages(List<Chat> userChatMessages) {
		this.userChatMessages = userChatMessages;
	}
	
	public List<Blog> getBlogPosts() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		String blogVal = null;
		blogVal = (String) session.getAttribute("postBlogVal");
		if (blogVal == null) {
			blogVal = "News";			
		}
		this.blogPosts = VchatController.getVchatController().getBlogPosts(blogVal);
		return blogPosts;
	}

	public void setBlogPosts(List<Blog> blogPosts) {
		this.blogPosts = blogPosts;
	}

	public List<Blog> getTopPosts() {
		this.topPosts = VchatController.getVchatController().getTowTopBlogPosts();
		return topPosts;
	}

	public void setTopPosts(List<Blog> topPosts) {
		this.topPosts = topPosts;
	}

	public List<BlogComment> getBlogComments() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		detailPost = (Blog) session.getAttribute("detailP");
		this.blogComments = VchatController.getVchatController().getBlogComments(detailPost);
		return blogComments;
	}

	public void setBlogComments(List<BlogComment> blogComments) {
		this.blogComments = blogComments;
	}

	public boolean isTermsConditions() {
		return termsConditions;
	}

	public void setTermsConditions(boolean termsConditions) {
		this.termsConditions = termsConditions;
	}
	
	public WidgetAgents getOpView() {
		if(VchatController.getVchatController()
				.getAgent(DigestUtils.md5Hex(getFromSession().getEmail())).size() != 0){
		 this.opView = VchatController.getVchatController()
		.getAgent(DigestUtils.md5Hex(getFromSession().getEmail())).get(0);
		}
		return opView;
	}

	public void setOpView(WidgetAgents opView) {
		this.opView = opView;
	}
	
	public String getTmpVal() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		this.tmpVal = (String) session.getAttribute("postBlogVal");
		if (tmpVal == null) {
			tmpVal = "News";			
		}
		return tmpVal;
	}

	public void setTmpVal(String tmpVal) {
		this.tmpVal = tmpVal;
	}
	
	public Blog getDetailPost() {
		return detailPost;
	}

	public void setDetailPost(Blog detailPost) {
		this.detailPost = detailPost;
	}

	public Blog getBlog() {
		if (blog == null) {
			blog = new Blog();
		}
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	
	public BlogComment getBlogCom() {
		if(blogCom ==null){
			blogCom = new BlogComment();
		}
		return blogCom;
	}

	public void setBlogCom(BlogComment blogCom) {
		this.blogCom = blogCom;
	}
	
	public boolean isCheckComment() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
			.getSession(false);
		String comName = (String) session.getAttribute("logName");
		if(comName!=null){
			this.checkComment = true;
		}
		return checkComment;
	}
	public void setCheckComment(boolean checkComment) {
		this.checkComment = checkComment;
	}
	///////////////////////////////////////////Operations/////////////////////////////////

	// user session handling

	private void putInToSession(User user) {
		try {
			FacesContext fCtx = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fCtx.getExternalContext()
					.getSession(false);
			String sessionId = session.getId();
			session.setAttribute(sessionId, user);
		} catch (Exception e) {
			log.severe("Error in putting user in session: " + e.getMessage());
		}
	}

	private User getFromSession() {
		try {
			FacesContext fCtx = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fCtx.getExternalContext()
					.getSession(false);
			String sessionId = session.getId();
			user = (User) session.getAttribute(sessionId);
		} catch (Exception e) {
			log.severe("Error in getting user from session: " + e.getMessage());
		}
		return user;
	}

	public void assignUser() {
		user = getFromSession();
	}

	//////////////////////////////////////////user login handling///////////////////////////////////

	public String login() {
		String url2 = null;
		try {
			String password = DigestUtils.md5Hex(user.getPassword());
			user.setPassword(password);
			user = VchatController.getVchatController().login(user);
			if (user != null) {
				putInToSession(user);
				if (rememberUser) {
					setRememberedUser(user.getEmail(), user.getPassword(),
							10000 * 24, "/");
				}
				if (user.getBusinessPlan() == null) {
					spreadlyService();
					if (user.getBusinessPlan() != null) {
						VchatController.getVchatController().updateUser(user);
					}
				}
				if (user.isActive()) {
					url2 = "/dashboard.xhtml";
				} else {

					url2 = "/dashboard.xhtml";
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Login Error:",
								"Username or password not correct!"));
				url2 = "/welcome";
			}
		} catch (Exception e) {
			log.severe("Erro in login" + e.getMessage());
		}
		return url2;
	}

	public void addUser() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		String bplan = null;
		bplan = (String) session.getAttribute("pricingVal");
		if (bplan == null) {
			bplan = "19487";			
		}
		String userFullName = user.getEmail().substring(0,
				user.getEmail().indexOf("@"));
		user.setFullName(userFullName);
		user.setWidgetId(DigestUtils.md5Hex(user.getEmail()).toString());
		String password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(password);
		if (VchatController.getVchatController().signUpUser(user)) {
			if (bplan != null) {
			WidgetContents wc = new WidgetContents();
			wc.setColstate("Live Chat : Online");
			wc.setErrorState("Live Chat : Offline");
			wc.setFooter("assistro");
			wc.setmPanel("Welcome , How can I help you today?");
			wc.setInPanel("Hello, thank you for visiting our website. How can I help you?");
			wc.setOfflineHeader("Live Chat: Offline");
			wc.setOffLinePanel("Sorry we are not available, please leave a message");
			wc.setOnlineHeader("Talking to Support");
			wc.setProOperator("Helpdesk");
			wc.setWidgetId(DigestUtils.md5Hex(user.getEmail()).toString());
			VchatController.getVchatController().addWidgetContents(wc);
			doRedirect("https://spreedly.com/assistro/subscribers/"
					+ wc.getWidgetId() + "/subscribe/" + bplan + "/"
					+ user.getEmail() + "?return_url=" + Util.url
					+ "/dashboard.html");
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"User Already Exist:",
							"Please Login with your Id.!"));
		}
	 }
	
	
	public void signUp() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		if(!termsConditions){
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Terms and conditions:",
							"You have to accept terms and condition to signup.!"));
		}
		else{
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		String bplan = null;
		bplan = (String) session.getAttribute("pricingVal");
		if (bplan == null) {
			bplan = "19487";			
		}
		String userFullName = user.getEmail().substring(0,
				user.getEmail().indexOf("@"));
		user.setFullName(userFullName);
		user.setWidgetId(DigestUtils.md5Hex(user.getEmail()).toString());
		String password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(password);
		if (VchatController.getVchatController().signUpUser(user)) {
			putInToSession(user);
			WidgetContents wc = new WidgetContents();
			wc.setColstate("Live Chat : Online");
			wc.setErrorState("Live Chat : Offline");
			wc.setFooter("assistro");
			wc.setmPanel("Welcome , How can I help you today?");
			wc.setInPanel("Hello, thank you for visiting our website. How can I help you?");
			wc.setOfflineHeader("Live Chat: Offline");
			wc.setOffLinePanel("Sorry we are not available, please leave a message");
			wc.setOnlineHeader("Talking to Support");
			wc.setWidgetId(DigestUtils.md5Hex(user.getEmail()).toString());
			VchatController.getVchatController().addWidgetContents(wc);
				putInToSession(user);
				doRedirect("https://spreedly.com/assistro/subscribers/"
						+ wc.getWidgetId()
						+ "/subscribe/"
						+ bplan
						+ "/"
						+ user.getEmail()
						+ "?return_url="
						+ Util.url
						+ "/dashboard.html");
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"User Already Exist:",
							"Please Login with your Id.!"));
		}
	 }
	}

	public void logoff() throws ServletException, IOException {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) fCtx.getExternalContext()
				.getRequest();
		HttpServletResponse res = (HttpServletResponse) fCtx
				.getExternalContext().getResponse();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		String sessionId = session.getId();
		session.removeAttribute(sessionId);
		session.invalidate();
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().contains("@")) {
				cookie.setMaxAge(0);
				// cookie.setValue("");
				cookie.setPath("/");
				res.addCookie(cookie);
			}
		}
		doRedirect("/welcome.html");
	}

	// user account update

	public String update() {
		VchatController.getVchatController().updateUser(user);
		return "/dashboard.xhtml";
	}

	// verify user on admin pages for login
	public void verifyUseLogin(ComponentSystemEvent event) {
		user = getFromSession();
		if (user == null) {
			doRedirect("/welcome.html");
		}
	}

	private void doRedirect(String url) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// check for save password user
	@SuppressWarnings("rawtypes")
	public void doRememberMe(ComponentSystemEvent event) throws IOException {
		try {
//			if(getFromSession() != null){
//				doRedirect("/admin/dashboard.html");
//			}
//			else{
			FacesContext fCtx = FacesContext.getCurrentInstance();
			Map<String, Object> c = fCtx.getExternalContext()
					.getRequestCookieMap();
			Iterator<Entry<String, Object>> i = c.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry mEntry = (Map.Entry) i.next();
				Cookie cookie = (Cookie) mEntry.getValue();
				if (cookie.getName().contains("@")) {
					User usr = new User();
					usr.setEmail(cookie.getName());
					usr.setPassword(cookie.getValue());
					usr = VchatController.getVchatController().login(usr);
					if (usr != null) {
						HttpSession session = (HttpSession) fCtx
								.getExternalContext().getSession(true);
						String sessionId = session.getId();
						session.setAttribute(sessionId, usr);
						//doRedirect("/admin/dashboard.html");
					}					
				}
			}
//		 }
		} catch (Exception e) {
			log.severe("Error in getting/putting user from cookie: "
					+ e.getMessage());
		}
	}

	// setting the cookie for remember me user
	private void setRememberedUser(String username, String password, int age,
			String path) {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fCtx
				.getExternalContext().getResponse();
		Cookie c = new Cookie(username, password);
		c.setMaxAge(age);
		c.setPath(path);
		response.addCookie(c);
	}

	// related to forget password

	public void forgetPassword() {
		if (VchatController.getVchatController().findById(forgetEmail) != null) {
			VchatController.getVchatController().sendEmail(forgetEmail);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Check:",
							"Your Email.!"));
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Please Signup:", "Id not exists.!"));
		}
	}

	public String passwordMatch() {
		String page = "";
		if (!password.equals(conPass)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Password Error:", "password not match....!"));
		} else {
			page = resetPassword();
		}
		return page;
	}

	private String resetPassword() {
		String forgetPassword = DigestUtils.md5Hex(password);
		VchatController.getVchatController().updateUserPassword(forgetEmail,
				forgetPassword);
		return "/welcome.html";
	}
	
	public void clearPasswordField(){
		this.setForgetEmail("");
	}

	///////////////////////////////////////// user license handling///////////////////////////////

	public void spreadlyService() {
		try {
			String url1 = "https://spreedly.com/api/v4/assistro/subscribers/"
					+ DigestUtils.md5Hex(getFromSession().getEmail()) + ".xml";
			URL url = new URL(url1);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
//			setBasicAuth(connection,
//					"b939a6536fc6429ab21e36f3888ee69387c70020", "");
			setBasicAuth(connection,
					"fcec4dbb4b6097470118a20d82cb9d0fc85652a5", "");
			
			connection.connect();
			connection.setReadTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			if (in != null) {
				SAXReader reader = new SAXReader();
				Document document = reader.read(in);
				Element root = document.getRootElement();
				String uAct = root.element("active").getText();
				user.setActive(Boolean.parseBoolean(uAct));
				if (uAct.equals("true")) {
					String uPlan = root.element("subscription-plan-version")
							.element("name").getText();
					user.setBusinessPlan(uPlan);
					int maxAgent = Integer.parseInt(Util.planAgent.get(uPlan));
					user.setMaxAgent(maxAgent);
				}
			}
		} catch (Exception e) {
			log.severe("spreadlyService" + e);
		}
	}
	
	public void changePlanInfo() {
		try {
			String url1 = "https://spreedly.com/api/v4/assistro/subscribers/"
					+ DigestUtils.md5Hex(getFromSession().getEmail()) + ".xml";
			URL url = new URL(url1);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
//			setBasicAuth(connection,
//					"b939a6536fc6429ab21e36f3888ee69387c70020", "");
			setBasicAuth(connection,
					"fcec4dbb4b6097470118a20d82cb9d0fc85652a5", "");
			connection.connect();
			connection.setReadTimeout(10000);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			if (in != null) {
				SAXReader reader = new SAXReader();
				Document document = reader.read(in);
				Element root = document.getRootElement();
				String userToken = root.element("token").getText();
				doRedirect("https://spreedly.com/assistro/subscriber_accounts/"+userToken);
			}
		} catch (Exception e) {
			log.severe("spreadlyService" + e);
		}
	}
	
	public void updateSpreedlyAccount(){
		try {
			String url1 = "https://spreedly.com/api/v4/assistro/subscribers/"
					+ DigestUtils.md5Hex(getFromSession().getEmail()) + "/change_subscription_plan.xml";
			URL url = new URL(url1);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
//			setBasicAuth(connection,
//					"b939a6536fc6429ab21e36f3888ee69387c70020", "");
			setBasicAuth(connection,
					"fcec4dbb4b6097470118a20d82cb9d0fc85652a5", "");
			connection.setDoOutput(true);
			connection.setRequestMethod("PUT");
//			connection.connect();			
			connection.setReadTimeout(10000);
			System.out.println(connection.getResponseCode());

//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					connection.getInputStream()));
//			if (in != null) {
//				SAXReader reader = new SAXReader();
//				Document document = reader.read(in);
//				Element root = document.getRootElement();
//				String uAct = root.element("active").getText();
//				user.setActive(Boolean.parseBoolean(uAct));
//				if (uAct.equals("true")) {
//					String uPlan = root.element("subscription-plan-version")
//							.element("name").getText();
//					user.setBusinessPlan(uPlan);
//					int maxAgent = Integer.parseInt(Util.planAgent.get(uPlan));
//					user.setMaxAgent(maxAgent);
//				}
//				else{
//					String uPlan = root.element("subscription-plan-version")
//							.element("name").getText();
//					user.setBusinessPlan(uPlan);
//				}
//			}
		} catch (Exception e) {
			log.severe("spreadlyService" + e);
		}
	}

	// helping method for above spreedly service method
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

	// get planInfo for updating user plan
	public String getPlanInfo() {
		user = getFromSession();
		if (user.getBusinessPlan() == null) {
			spreadlyService();
			if (user.getBusinessPlan() != null) {
				VchatController.getVchatController().updateUser(user);
			}
		} else {
			spreadlyService();
			if (user.getBusinessPlan() != null) {
				VchatController.getVchatController().updateUser(user);
			}
		}
		planInfo = "";
		return planInfo;
	}

	// update user plan
	public void updatePakege() {
		user = getFromSession();
//		if (user.getBusinessPlan().equals(loggedInUser.getBusinessPlan())) {
//			FacesContext
//					.getCurrentInstance()
//					.addMessage(
//							null,
//							new FacesMessage(FacesMessage.SEVERITY_WARN,
//									"Plan Update:",
//									"Please Select a plan from dropdown and then press update!"));
//		} else {
			doRedirect("https://spreedly.com/assistro/subscribers/"
					+ DigestUtils.md5Hex(user.getEmail()).toString()
					+ "/subscribe/" + loggedInUser.getBusinessPlan() + "/"
					+ user.getEmail() + "?return_url=" + Util.url
					+ "/dashboard.html");
//		}
	}

	// get plan info for signup
	public void getPricingInfo(ActionEvent event) {
		String id = event.getComponent().getId();

		if (Util.plan.containsKey(id)) {
			id = Util.plan.get(id);
			FacesContext fCtx = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fCtx.getExternalContext()
					.getSession(false);
			session.setAttribute("pricingVal", id);
			
		}
	}

	////////////////////////////////////////////// related to User Agent////////////////////////////////

	public List<MyAgents> getUserOperator() {
		if (getFromSession() != null) {
			List<WidgetAgents> list = VchatController.getVchatController()
					.getAgent(DigestUtils.md5Hex(getFromSession().getEmail()));
			if (list.size() == 0) {
				userOperator = new ArrayList<MyAgents>();
				return userOperator;
			} else {
				userOperator = new ArrayList<MyAgents>();

				int j = 0;
				for (int i = 0; i < (list.size() / 2) + 1; i++) {
					MyAgents myagent = null;
					if (list.size() > j && list.get(j) != null) {
						myagent = new MyAgents();
						myagent.setAgent1(list.get(j));
						j++;
					}
					if (list.size() > j && list.get(j) != null) {
						myagent.setAgent2(list.get(j));
						j++;
					}

					if (myagent != null) {
						userOperator.add(myagent);
					}
				}
			}
		}
		return userOperator;
	}

	public String addAgent() {
		try {
			XMPPService xmppService = XMPPServiceFactory.getXMPPService();
			String wId = DigestUtils.md5Hex(getFromSession().getEmail());
			operator.setWidgetId(wId);
			//operator.setAgentStatus(true);
			if (VchatController.getVchatController().addAgent(operator,
					getFromSession().getMaxAgent())) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Accept Requests:",
										"You will receive 10 friend requests on Gtalk...!"));
				for (int i = 1; i < 11; i++) {
					String visitor = "visitor" + i;
					JID sender = new JID(visitor + "@" + Util.emailUrl);
					JID reciever = new JID(operator.getAgentId());
					xmppService.sendInvitation(reciever, sender);
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Agent Exist Already:",
								"Please try with different id.!"));
			}
		} catch (Exception e) {
			log.severe("Error in addAgent : " + e);
		}

		return "/admin/operator.html";
	}

	public String removeEntry(final ActionEvent actionEvent) {
		operator = this.getEventParameter(actionEvent, "entryToRemoveIndex");
		VchatController.getVchatController().delAgent(operator);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Agent Deleted:",
						"done"));
		operator = new WidgetAgents();
		return "/admin/operator.html";
	}
	
	public void chkEntry(final ActionEvent actionEvent) {
		operator = this.getEventParameter(actionEvent, "entryToCheckIndex");

		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		session.setAttribute("AgentInfo", operator);

	}

	public void editEntry(final ActionEvent actionEvent) {
		operator = this.getEventParameter(actionEvent, "entryToRemoveIndex");
	}

	@SuppressWarnings("unchecked")
	private <E> E getEventParameter(final FacesEvent event,
			final String paramName) {
		final List<UIComponent> children = event.getComponent().getChildren();
		for (final UIComponent component : children) {
			if (component instanceof UIParameter) {
				final UIParameter uiParameter = (UIParameter) component;
				if (paramName.equals(uiParameter.getName())) {
					return (E) uiParameter.getValue();
				}
			}
		}
		return null;
	}

	public void updateAgent() {
		operator.setWidgetId(DigestUtils.md5Hex(getFromSession().getEmail()));
		VchatController.getVchatController().updatePriorityAgent(operator);
	}

	private void creatPriority() {
		this.agentPriority = new ArrayList<String>();
		User usr = getFromSession();
		for (int i = 0; i < usr.getMaxAgent(); i++) {
			agentPriority.add((i + 1) + "");
		}
	}

	private void verifyPriority() {
		this.updatePriority = new ArrayList<String>();
		User usr = getFromSession();
		List<String> list = VchatController.getVchatController()
				.getPriorityList(
						DigestUtils.md5Hex(getFromSession().getEmail()));
		for (int i = 1; i <= usr.getMaxAgent(); i++) {
			if (list.isEmpty() || !list.contains(i + "")) {
				updatePriority.add(i + "");
			}

		}
	}

	public void clearagentPopUp() {
		operator = new WidgetAgents();

	}

	// related to user chat history
	public void sethistory(final ActionEvent actionEvent) {
		chatSession = this.getEventParameter(actionEvent, "entryToRemoveIndex");
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		session.setAttribute("chatSess", chatSession);
	}

	public void getChatObj() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		chatSession = (Chat) session.getAttribute("chatSess");
	}

	// check for valid email address
	public void emailValidator(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		String email = (String) value;

		if (!email.contains("@")) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Invalid Email Address Please use eg. user@domain", null));
		}
	}
	

	// sending email to website Admin from non-registered user
	public void sendEmailToAdmin() {
		VchatController.getVchatController().sendtoAdmin(emailAddress,
				message, fullName);
		this.setFullName("");
		this.setEmailAddress("");
		this.setPhoneNo("");
		this.setMessage("");
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Message sent:",
						"Your message sent to admin....!"));
	}

	
	//term and conditions method
	public void termsCheck() {  
        String summary = termsConditions ? "Checked" : "Unchecked";   
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
    }
	
	public void excelService(){
		try {
			doRedirect(Util.url+"/ShowExcelData?widgetcode="
					+ getFromSession().getWidgetId());			
		} catch (Exception e) {
			log.severe("Error:" + e);
		}
	}
	
	public void passwordValidator(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIInput passwordComponent = (UIInput) component.getAttributes().get("passwordComponent");
        String password = (String) passwordComponent.getValue();
        String confirmPassword = (String) value;

        if (confirmPassword != null && !confirmPassword.equals(password)) {
            throw new ValidatorException(new FacesMessage(
                "Password are not matched"));
        }
    }
	
		public void getAgentInfo(ActionEvent event) {
			String id = event.getComponent().getId();
		}
		
	//blog related method
		public void addBlogPost() {
			String blogId = RandomStringUtils.randomAscii(8);
			this.blog.setBlogDate(new Date());
			this.blog.setBlogKey(blogId);
			if (VchatController.getVchatController().publishPost(blog)) {
				VchatController.getVchatController().publishPost(blog);
				this.blog = new Blog();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Post Publish:",
								"Your post published successfully...!"));
			} 
		 }
		
		// get post info for blog
		public void getPostInfo(ActionEvent event) {
			String id = event.getComponent().getId();

				FacesContext fCtx = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fCtx.getExternalContext()
						.getSession(false);
				session.setAttribute("postBlogVal", id);
		}
		
		// get the detail post
		public void showDetailPost(final ActionEvent actionEvent) {
			detailPost = this.getEventParameter(actionEvent, "detailShow");
			FacesContext fCtx = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fCtx.getExternalContext()
					.getSession(false);
			session.setAttribute("detailP", detailPost);
		}
		

		public void addBlogComment() {
				FacesContext fCtx = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fCtx.getExternalContext()
					.getSession(false);				
				if(!this.checkComment){
					session.setAttribute("logName", this.blogCom.getComUsrName());
					session.setAttribute("logEmail", this.blogCom.getComUsrEmail());
				}				
				detailPost = (Blog) session.getAttribute("detailP");
				if(loggedInUser != null){
					System.out.println("Logged in user"+loggedInUser.getFullName());
					this.blogCom.setComUsrName(loggedInUser.getFullName());
					this.blogCom.setComUsrEmail(loggedInUser.getEmail());
				}
							
				if(this.checkComment){
					String comName = (String) session.getAttribute("logName");
					String comEmail = (String) session.getAttribute("logEmail");
					System.out.println("Logged in"+ comName);		
					this.blogCom.setComUsrName(comName);
					this.blogCom.setComUsrEmail(comEmail);
				}
				this.blogCom.setComDate(new Date());
				this.blogCom.setBlogKeyVal(detailPost.getBlogKey());
				VchatController.getVchatController().enterComment(blogCom);
				this.blogCom = new BlogComment();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Comment:",
								"successfully...!"));
		 }
	
}
