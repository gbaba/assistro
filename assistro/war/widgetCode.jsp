<%@page import="com.vchat.util.Util"%>
<%@ page import="com.vchat.util.Widget"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.codec.digest.DigestUtils"%>

<%
	
%>

<%
	String id = request.getParameter("widget_code");
	Widget wc = new Widget(id);
	
	if (wc.getUser().getMaxAgent() == 1 && wc.getCount() > 10
	|| !wc.getUser().isActive()) {
		if (session.isNew()) {
	try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
		}
		String str1 = wc.getExpireWidgetData();
		wc.updateWid();
		out.print(str1);
	}

	else if (wc.getWa().size() == 0) {
		if (session.isNew()) {
	try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
		}
		String str2 = wc.getOffflineData();

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
		wc.updateWid();
		out.print(str2);

	} else {
		if (session.isNew()) {
	try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
		}
		String str = wc.getData();
		if (wc.getWc() != null) {

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
		str = str.replace("Welcome_To_Customer_Help", wc
				.getWc().getInPanel())
				.replace(
						"collapsed.setAttribute ('style','display:block');",
						"collapsed.setAttribute ('style','display:none');")
				.replace(
						"expanded.setAttribute ('style','display:none');",
						"expanded.setAttribute ('style','display:block');");
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
				.getmPanel());
	}

	if (wc.getWc().isShowMessage()) {
		if (wc.getWc().isProActiveChat()){
		str = str
				.replace(
						"expanded.setAttribute ('style','display:block');",
						"expanded.setAttribute ('style','display:none');")
				.replace(
						"before_chat.setAttribute ('style','display:none');",
						"before_chat.setAttribute ('style','display:block');");
		}else{
			str = str
					.replace(
							"collapsed.setAttribute ('style','display:block');",
							"collapsed.setAttribute ('style','display:none');")
					.replace(
							"before_chat.setAttribute ('style','display:none');",
							"before_chat.setAttribute ('style','display:block');");
		}

		if (!session.isNew()) {
			String cookieName = "username";
			Cookie cookies[] = request.getCookies();
			Cookie cookie = null;
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					if (cookie.getName().equals(cookieName)) {
						if(wc.getWc().isProActiveChat()){
							str = str
									.replace(
											"before_chat.setAttribute ('style','display:block');",
											"before_chat.setAttribute ('style','display:none');")
									.replace(
											"expanded.setAttribute ('style','display:none');",
											"expanded.setAttribute ('style','display:block');");
						}else{
							str = str
									.replace(
											"before_chat.setAttribute ('style','display:block');",
											"before_chat.setAttribute ('style','display:none');")
									.replace(
											"collapsed.setAttribute ('style','display:none');",
											"collapsed.setAttribute ('style','display:block');");
						}
						
					} else {
						if(wc.getWc().isProActiveChat()){
							str = str
									.replace(
											"before_chat.setAttribute ('style','display:none');",
											"before_chat.setAttribute ('style','display:block');")
									.replace(
											"expanded.setAttribute ('style','display:block');",
											"expanded.setAttribute ('style','display:none');");
						}else{
							str = str
									.replace(
											"before_chat.setAttribute ('style','display:none');",
											"before_chat.setAttribute ('style','display:block');")
									.replace(
											"collapsed.setAttribute ('style','display:block');",
											"collapsed.setAttribute ('style','display:none');");
						}
						

					}
				}

			}

		}
	}

	wc.updateWid();

		}
		out.print(str);
	}
%>