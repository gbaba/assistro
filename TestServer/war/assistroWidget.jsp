<%@page import="com.assistro.util.Util"%>
<%@ page import="com.assistro.util.AssistroWidget"%>
<%@ page import="com.assistro.beans.WidgetAgents"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.codec.digest.DigestUtils"%>


<%
	String id = request.getParameter("widget_code");

AssistroWidget wc = new AssistroWidget(id);

 	if (wc.getUser().getMaxAgent() == 1 && wc.getCount() > 10 && wc.getUser().getBusinessPlan().equals("Flying Solo")
	|| !wc.getUser().isActive()) {

		String str1 = wc.getExpireWidgetData();
		if(session.isNew()){
		 wc.updateWid();
		}
		out.print(str1);
	}  

	if (wc.getWa().size() == 0) {

		String str2 = wc.getOffflineData();
		
		if(wc.getWc().getWidgetStyle() != null){
			str2 = str2.replace("assistroStandard", wc.getWc()
					.getWidgetStyle());
		}

		if (wc.getWc().getOfflineHeader() != null
		&& !wc.getWc().getOfflineHeader().isEmpty()) {
	str2 = str2.replace("Offline_Title", wc.getWc()
			.getOfflineHeader());
		}
		if (wc.getWc().getErrorState() != null
		&& !wc.getWc().getErrorState().isEmpty()) {
	str2 = str2.replace("Live_Chat_offline", wc.getWc()
			.getErrorState());
		}
		if (wc.getWc().getOffLinePanel() != null
		&& !wc.getWc().getOffLinePanel().isEmpty()) {
	str2 = str2.replace("Not_There", wc.getWc()
			.getOffLinePanel());
		}
		if (wc.getWc().getFooter() != null
		&& !wc.getWc().getFooter().isEmpty()) {
	str2 = str2.replace("VIRTISM", wc.getWc().getFooter());
		}
		if(session.isNew()){
		wc.updateWid();
		}
		out.print(str2);

	} else {
		String str = wc.getData();
		if (wc.getWc() != null) {
	
	if(wc.getWc().getWidgetStyle() != null){
		str = str.replace("assistroStandard", wc.getWc()
						.getWidgetStyle());
	}
			
	if (wc.getWc().getOnlineHeader() != null
			&& !wc.getWc().getOnlineHeader().isEmpty()) {
		str = str.replace("Widget_Virtism_Chat_Title", wc
				.getWc().getOnlineHeader());
	}

	if (wc.getWc().getFooter() != null
			&& !wc.getWc().getFooter().isEmpty()) {
		str = str.replace("VIRTISM", wc.getWc().getFooter());
	}

	if (wc.getWc().isProActiveChat()) {
/* 		Cookie procookie = new Cookie("proactive","proChat");
		procookie.setPath("/");
		procookie.setMaxAge(24 * 60 * 60 * 1000);
	    response.addCookie(procookie); */
		String close = (String)session.getAttribute("closeEve");
		if(close != null){
			str = str
					.replace(
							"collapsed.setAttribute ('style','display:none');",
							"collapsed.setAttribute ('style','display:block');").replace(
									"expanded.setAttribute ('style','display:block');",
									"expanded.setAttribute ('style','display:none');");
		}
		else{
		str = str
				.replace("Welcome_To_Customer_Help",
						wc.getWc().getInPanel())
				.replace(
						"collapsed.setAttribute ('style','display:block');",
						"collapsed.setAttribute ('style','display:none');")
				.replace(
						"expanded.setAttribute ('style','display:none');",
						"expanded.setAttribute ('style','display:block');");
		}
	}
	if (!wc.getWc().isProActiveChat()) {
		str = str.replace(
				"message_panel.appendChild(host_name);", " ");
	}
	if (wc.getWc().getColstate() != null
			&& !wc.getWc().getColstate().isEmpty()) {
		str = str
				.replace("Live_Chat", wc.getWc().getColstate());
	}
	if (wc.getWc().getmPanel() != null
			&& !wc.getWc().getmPanel().isEmpty()) {
		str = str.replace("Welcome_To_Assistro", wc.getWc()
				.getmPanel()).replace("Agent_Designation",wc.getWc().getmPanel());
	}

	if (wc.getWc().isShowMessage()) {
/* 		Cookie beforecookie = new Cookie("beforeWidget","BeforeChatWidget");
		beforecookie.setPath("/");
		beforecookie.setMaxAge(24 * 60 * 60 * 1000);
	    response.addCookie(beforecookie); */
			Cookie cookies[] = request.getCookies();
			Cookie cookie = null;
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("username")){
						cookie = cookies[i];
						break;
					}
				}	
			}
					if (cookie == null) {
						if (wc.getWc().isProActiveChat()) {
							str = str
									.replace(
											"expanded.setAttribute ('style','display:block');",
											"expanded.setAttribute ('style','display:none');")
									.replace(
											"before_chat.setAttribute ('style','display:none');",
											"before_chat.setAttribute ('style','display:block');");
						} else {
							str = str
									.replace(
											"collapsed.setAttribute ('style','display:block');",
											"collapsed.setAttribute ('style','display:none');")
									.replace(
											"before_chat.setAttribute ('style','display:none');",
											"before_chat.setAttribute ('style','display:block');");
						} 
						//out.print("cookieval"+cookie);
					} 
/* 					else{
						if (wc.getWc().isProActiveChat()) {
							str = str
									.replace("Welcome_To_Customer_Help",
											wc.getWc().getInPanel())
									.replace(
											"collapsed.setAttribute ('style','display:block');",
											"collapsed.setAttribute ('style','display:none');")
									.replace(
											"expanded.setAttribute ('style','display:none');",
											"expanded.setAttribute ('style','display:block');");
						} else {
							String close = (String)session.getAttribute("closeEve");
							if(close != null){
								str = str
										.replace(
												"collapsed.setAttribute ('style','display:none');",
												"collapsed.setAttribute ('style','display:block');").replace(
														"expanded.setAttribute ('style','display:block');",
														"expanded.setAttribute ('style','display:none');");
							}
						}
					} */

	}
	if(session.isNew()){
	wc.updateWid();
	}
		}
		out.print(str);
	}
%>