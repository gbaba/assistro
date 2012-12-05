package com.vchat.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




import com.vchat.beans.Chart;
import com.vchat.beans.Chat;
import com.vchat.beans.User;
import com.vchat.beans.WidgetAgents;
import com.vchat.beans.WidgetContents;
import com.vchat.controller.VMessageController;
import com.vchat.controller.VchatController;


public class Widget {
	WidgetContents wc;
	List<WidgetAgents> wa;
	Chart chart;
	List<Chat> sessionList;
	User user;
	//Chat chat;
	private int count;

	public Widget(String id) {
		wc = VchatController.getVchatController().getWidgetData(id);
		wa = VchatController.getVchatController().getOperatorList(id);
		chart = VchatController.getVchatController().getChartData(id,
				new SimpleDateFormat("ddMMyyyy").format(new Date()));
		//chat = VchatController.getVchatController().getChatObj(id);
		sessionList = VchatController.getVchatController().getChatSessions(id);
		count = sessionList.size()+VMessageController.getVMessageController().sessionCount(id);
		
		
		user = VchatController.getVchatController().getUserWidget(id);
		if(user.getMaxAgent()==1 && count > 10){
			VchatController.getVchatController().sendEmailMessage(user.getEmail()," Dear customer your chat limit has been exceeded.Please update your package to get more chats.","Package Expire");	

		}
		
	}

	public void updateWid() {
		if (chart != null) {
			if (wa.size() > 0) {
				chart.setSawOnline(chart.getSawOnline() + 1);
			}
			chart.setVisitorOnSite(chart.getVisitorOnSite() + 1);
		} else {
			chart = new Chart();
			chart.setDay(new SimpleDateFormat("ddMMyyyy").format(new Date()));
			chart.setWidgetId(wc.getWidgetId());
			if (wa.size() > 0) {
				chart.setSawOnline(1);
			}
			chart.setVisitorOnSite(1);
		}
		VchatController.getVchatController().updateChart(chart);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public WidgetContents getWc() {
		return wc;
	}

	public void setWc(WidgetContents wc) {
		this.wc = wc;
	}

	public List<WidgetAgents> getWa() {

		return wa;
	}

	public void setWa(List<WidgetAgents> wa) {
		this.wa = wa;
	}
	
	public List<Chat> getSessionList() {
		return sessionList;
	}

	public void setSessionList(List<Chat> sessionList) {
		this.sessionList = sessionList;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

//	public Chat getChat() {
//		return chat;
//	}
//
//	public void setChat(Chat chat) {
//		this.chat = chat;
//	}

	public String getData() {
		String data = "jQuery.noConflict();jQuery(function(jQuery){jQuery(document).ready(function createWidget(){"
				+ "var  widget_jquery,s12,widgetjs;var widget_css,help_me;var divTag,supmsg,before_chat_header_text,before_chat_collapsed,before_collapsed_header,before_chat_content,before_chat_header,before_chat_footer,collapsed,username,usernamelabel,expanded,welMes,host_name,minimizeBut,crossBut,header,buttons, content, footer,message_panel,chat_area,send_button,before_chat,user_email,user_question,email_label,message_label,chat_but;var myhead;"
					
				+ "widget_jquery = document.createElement('script');"
				+ "widgetjs = document.createElement('script');"
				+ "widget_css = document.createElement('link');"
				+ "var headId = document.getElementsByTagName('head')[0];"
				+ "widget_css.href='"
				+ Util.url
				+ "/css/assistroWidget.css';"
				+ "widget_css.type='text/css';"
				+ "widget_css.rel='stylesheet';"				
				+ "widget_jquery.src = 'http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js';"

				+ "widgetjs.src = '"
				+ Util.url
				+ "/js/assistroOld.js';"


				+ "headId.appendChild(widgetjs);"
				+ "headId.appendChild(widget_css);"
				
				+ "divTag = document.createElement('div');"	
				+ "divTag.className = 'chat_container';"

				// before chat widget
				+ "before_chat = document.createElement('div');"
				+ "before_chat.setAttribute ('style','display:none');"
				+ "before_chat.className = 'before_chat';"
				
				// before Chat Header
				+ "before_chat_header = document.createElement('div');"
				+ "before_chat_header.className = 'header';"
				
				+ "before_chat_header_text = document.createElement('p');"
				+ "before_chat_header_text.innerHTML='Need Help';"
				
				//+ "before_chat_header.innerHTML='Start Chat';"

				+ "buttons = document.createElement('div');"
				+ "buttons.className = 'buttons';"

				+ "minimizeBut = document.createElement('a');"
				+ "minimizeBut.className = 'minimize';"
				+ "buttons.appendChild(minimizeBut);"

				+ "crossBut = document.createElement('a');"
				+ "crossBut.className = 'cross';"
				+ "buttons.appendChild(crossBut);"

				+ "before_chat_header.appendChild(before_chat_header_text);"
				+ "before_chat_header.appendChild(buttons);"
				
				// Before Chat Footer
				+ "before_chat_footer = document.createElement('div');"
				+ "link1 = document.createElement('a');"
				+ "link1.className = 'host';"
				+ "link1.innerHTML='VIRTISM';"
				+ "link1.href='http://www.assistro.com';"
				+ "before_chat_footer.className = 'footer';"
				+ "before_chat_footer.innerHTML='i am ';"
				+ "before_chat_footer.appendChild(link1);"

				//before Chat Content
				+ "help_me = document.createElement('p');"
				+ "help_me.innerHTML = 'Enter your details below to chat with our customer service team';"
				
				+ "usernamelabel = document.createElement('p');"
				+ "usernamelabel.innerHTML = 'Name';"
				+ "usernamelabel.id = 'bname';"
				+ "username = document.createElement('input');"
				+ "username.type = 'text';"
				+ "username.className = 'before_user';"

				+ "email_label = document.createElement('p');"
				+ "email_label.innerHTML = 'Email';"
				+ "email_label.id = 'bmail';"
				+ "user_email = document.createElement('input');"
				+ "user_email.type = 'text';"
				+ "user_email.className = 'before_email';"
				+ "user_email.id = 'beforeEmail';"

				+ "chat_but= document.createElement('input');"
				+ "chat_but.value = 'Start Chat';"
				+ "chat_but.type='button';"
				+ "chat_but.className='start_chat';"

				+ "before_chat_content = document.createElement('div');"
				+ "before_chat_content.className = 'content';"
				+ "before_chat_content.setAttribute ('style','height:231px');"

				+ "before_chat_content.appendChild(help_me);"
				+ "before_chat_content.appendChild(usernamelabel);"
				+ "before_chat_content.appendChild(username);"
				+ "before_chat_content.appendChild(email_label);"
				+ "before_chat_content.appendChild(user_email);"
				+ "before_chat_content.appendChild(chat_but);"

				+ "before_chat.appendChild(before_chat_header);"
				+ "before_chat.appendChild(before_chat_content);"
				+ "before_chat.appendChild(before_chat_footer);"
				
				// expanded widget
				+ "expanded = document.createElement('div');"
				+ "expanded.className = 'live-chat';"
				+ "expanded.setAttribute ('style','display:none');"
				
				// Expanded Widget header
				+ "header = document.createElement('div');"
				+ "header.className = 'header';"
				+ "supmsg = document.createElement('p');"
				+ "supmsg.innerHTML='Widget_Virtism_Chat_Title';"

//				+ "header.innerHTML='Widget_Virtism_Chat_Title';"

				+ "buttons = document.createElement('div');"
				+ "buttons.className = 'buttons';"

				+ "minimizeBut = document.createElement('a');"
				+ "minimizeBut.className = 'minimize';"
				+ "buttons.appendChild(minimizeBut);"

				+ "crossBut = document.createElement('a');"
				+ "crossBut.className = 'cross';"
				+ "buttons.appendChild(crossBut);"
				
				+ "header.appendChild(supmsg);"
				+ "header.appendChild(buttons);"

				// Expanded widget footer
				+ "footer = document.createElement('div');"
				+ "link1 = document.createElement('a');"
				+ "link1.className = 'host';"
				+ "link1.innerHTML='VIRTISM';"
				+ "link1.href='http://www.assistro.com';"
				+ "footer.className = 'footer';"
				+ "footer.innerHTML='i am ';"
				+ "footer.appendChild(link1);"
	
				//expanded Widget Content
				+ "content = document.createElement('div');"
				+ "content.className = 'content';"

				+ "welMes = document.createElement('p');"
				+ "welMes.innerHTML = 'Welcome_To_Assistro';"

				+ "host_name = document.createElement('p');"
				+ "host_name.className = 'host_name';"
				
				+ "message_panel = document.createElement('div');"
				+ "message_panel.className = 'chat';"
				+ "message_panel.id = 'mess';"
				+ "host_name.innerHTML = 'Welcome_To_Customer_Help';"
				+ "message_panel.appendChild(host_name);"
				
				
				+ "chat_area = document.createElement('input');"
				+ "chat_area.className = 'type_text';"
				+ "chat_area.type = 'text';"
				+ "chat_area.id = 'sendmess';"
			
				+ "send_button= document.createElement('input');"
				+ "send_button.className = 'send';"
				+ "send_button.value = 'send';"
				+ "send_button.type='button';"
				
				+ "content.appendChild(welMes);"
				+ "content.appendChild(message_panel);"
				+ "content.appendChild(chat_area);"
				+ "content.appendChild(send_button);"
				
				+ "expanded.appendChild(header);"
				+ "expanded.appendChild(content);"
				+ "expanded.appendChild(footer);"
				
				//Online collaspsed state

				+ "collapsed = document.createElement('div');"
				+ "collapsed.className = 'collapsed';"
				+ "collapsed.setAttribute ('style','display:block');"
				
				+ "myhead = document.createElement('div');"
				+ "myhead.className = 'header';"
				+ "myhead.innerHTML = 'Live_Chat';"

				+ "buttons = document.createElement('div');"
				+ "buttons.className = 'buttons';"

				+ "crossBut = document.createElement('a');"
				+ "crossBut.className = 'cross';"
				+ "buttons.appendChild(crossBut);"

				+ "myhead.appendChild(buttons);"

							
				+ "collapsed.appendChild(myhead);"
				
								
				//before Chat Collpased State
				+ "before_chat_collapsed = document.createElement('div');"
				+ "before_chat_collapsed.className = 'before_chat_collapsed';"
				+ "before_chat_collapsed.setAttribute ('style','display:none');"
				
				+ "before_collapsed_header = document.createElement('div');"
				+ "before_collapsed_header.className = 'header';"
				+ "before_collapsed_header.innerHTML = 'Start_Chat';"

				+ "buttons = document.createElement('div');"
				+ "buttons.className = 'buttons';"

				+ "crossBut = document.createElement('a');"
				+ "crossBut.className = 'cross';"
				+ "buttons.appendChild(crossBut);"

				+ "before_collapsed_header.appendChild(buttons);"
				
				+ "before_chat_collapsed.appendChild(before_collapsed_header);"
				
				+ "divTag.appendChild(before_chat_collapsed);"
				+ "divTag.appendChild(collapsed);"
				+ "divTag.appendChild(expanded);"
				+ "divTag.appendChild(before_chat);"

				+ "document.getElementsByTagName('body')[0].appendChild(divTag);"
				//+"jQuery('body').append(divTag);"
				+ "});"
				+ "});";
		

		return data;
	}

	
	public String getExpireWidgetData() {
		String data = "jQuery.noConflict();jQuery(function(jQuery){jQuery(document).ready(function createWidget(){"
				+"var widget_css,headId,divTag,offline_mode,collapsed_offline_header,collapsed_offline;"

				+ "widget_css = document.createElement('link');"
				+ "var headId = document.getElementsByTagName('head')[0];"
				+ "widget_css.href='"
				+ Util.url
				+ "/css/assistroWidget.css';"
				+ "widget_css.type='text/css';"
				+ "widget_css.rel='stylesheet';"				

				+ "headId.appendChild(widget_css);"
				
				+ "divTag = document.createElement('div');"
				+ "divTag.className = 'chat_container';"			
				
				+ "collapsed_offline = document.createElement('div');"
				+ "collapsed_offline.className = 'collapsed_offline';"
				+ "collapsed_offline.setAttribute ('style','display:block');"
							
				//offline Collapsed State
				+ "collapsed_offline_header = document.createElement('div');"
				+ "collapsed_offline_header.className = 'header';"
				+ "collapsed_offline_header.innerHTML = 'Package expired!!';"
				
				+ "collapsed_offline.appendChild(collapsed_offline_header);"


				+ "divTag.appendChild(collapsed_offline);"

				+ "document.getElementsByTagName('body')[0].appendChild(divTag);"
				//+"jQuery('body').append(divTag);"
				+ "});"
				+ "});";
		

		return data;
	}
	
	public String getOffflineData(){
		String data = "jQuery.noConflict();jQuery(function(jQuery){jQuery(document).ready(function createWidget(){"
				+ "var  widget_jquery,s12,widgetjs;var widget_css,last_id,session;var divTag,supmsg,collapsed_offline_header,collapsed_offline,offline_header,offline_footer,offline_content,offlineContent,username,usernamelabel,minimizeBut,crossBut,header,buttons, content, footer,user_email,user_question,email_label,message_label,chat_but,offline_mode,off_name,off_name_label,off_email,off_email_label,off_msg,off_msg_label,off_but;var myhead;"
					
				+ "widget_jquery = document.createElement('script');"
				+ "widgetjs = document.createElement('script');"
				+ "widget_css = document.createElement('link');"
				+ "var headId = document.getElementsByTagName('head')[0];"
				+ "widget_css.href='"
				+ Util.url
				+ "/css/assistroWidget.css';"
				+ "widget_css.type='text/css';"
				+ "widget_css.rel='stylesheet';"				
				+ "widget_jquery.src = 'http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js';"

				+ "widgetjs.src = '"
				+ Util.url
				+ "/js/assistroOld.js';"


				+ "headId.appendChild(widgetjs);"
				+ "headId.appendChild(widget_css);"
				
				+ "divTag = document.createElement('div');"
				+ "divTag.className = 'chat_container';"
								
							
				//offline mode chat
				+ "offline_mode = document.createElement('div');"
				+ "offline_mode.setAttribute ('style','display:none');"
				+ "offline_mode.className = 'offline_state';"

				// offline Header
				+ "offline_header = document.createElement('div');"
				+ "offline_header.className = 'header';"
				+ "offline_header.innerHTML='Offline_Title';"

				+ "buttons = document.createElement('div');"
				+ "buttons.className = 'buttons';"

				+ "minimizeBut = document.createElement('a');"
				+ "minimizeBut.className = 'minimize';"
				+ "buttons.appendChild(minimizeBut);"

				+ "crossBut = document.createElement('a');"
				+ "crossBut.className = 'cross';"
				+ "buttons.appendChild(crossBut);"

				+ "offline_header.appendChild(buttons);"

				// offline footer
				+ "offline_footer = document.createElement('div');"
				+ "link1 = document.createElement('a');"
				+ "link1.className = 'host';"
				+ "link1.innerHTML='VIRTISM';"
				+ "link1.href='http://www.assistro.com';"
				+ "offline_footer.className = 'footer';"
				+ "offline_footer.innerHTML='i am ';"
				+ "offline_footer.appendChild(link1);"
					
				// offline Content
				+ "host_name = document.createElement('p');"
				+ "host_name.className = 'red';"
				+ "host_name.innerHTML = 'Not_There';"
				
				+ "off_name_label = document.createElement('label');"
				+ "off_name_label.innerHTML = 'Name';"

				+ "off_name = document.createElement('input');"
				+ "off_name.type = 'text';"
				+ "off_name.className = 'offname';"

				+ "off_email_label = document.createElement('label');"
				+ "off_email_label.innerHTML = 'Email';"

				+ "off_email = document.createElement('input');"
				+ "off_email.type = 'text';"
				+ "off_email.className = 'offemail';"

				+ "off_msg_label = document.createElement('label');"
				+ "off_msg_label.innerHTML = 'Enter Your Message';"
				+ "off_msg = document.createElement('textarea');"
				+ "off_msg.cols = '31';"
				+ "off_msg.className = 'offmsg';"

				+ "off_but= document.createElement('input');"
				+ "off_but.value = 'Send';"
				+ "off_but.type='button';"
				+ "off_but.className='sendofflineMsg';"

				
				+ "offlineContent = document.createElement('div');"
				+ "offlineContent.className = 'content';"
				
				+ "offlineContent.appendChild(host_name);"
				+ "offlineContent.appendChild(off_name_label);"
				+ "offlineContent.appendChild(off_name);"
				+ "offlineContent.appendChild(off_email_label);"
				+ "offlineContent.appendChild(off_email);"
				+ "offlineContent.appendChild(off_msg_label);"
				+ "offlineContent.appendChild(off_msg);"
				+ "offlineContent.appendChild(off_but);"
				
				+ "offline_mode.appendChild(offline_header);"
				+ "offline_mode.appendChild(offlineContent);"
				+ "offline_mode.appendChild(offline_footer);"
				
				+ "collapsed_offline = document.createElement('div');"
				+ "collapsed_offline.className = 'collapsed_offline';"
				+ "collapsed_offline.setAttribute ('style','display:block');"

				//offline Collapsed State
				+ "collapsed_offline_header = document.createElement('div');"
				+ "collapsed_offline_header.className = 'header';"
				+ "collapsed_offline_header.innerHTML = 'Live_Chat_offline';"

				+ "buttons = document.createElement('div');"
				+ "buttons.className = 'buttons';"

				+ "crossBut = document.createElement('a');"
				+ "crossBut.className = 'cross';"
				+ "buttons.appendChild(crossBut);"

				+ "collapsed_offline_header.appendChild(buttons);"
				
				+ "collapsed_offline.appendChild(collapsed_offline_header);"
				
				+ "divTag.appendChild(collapsed_offline);"
				+ "divTag.appendChild(offline_mode);"

				+ "document.getElementsByTagName('body')[0].appendChild(divTag);"
				//+"jQuery('body').append(divTag);"	
				+ "});"
				+ "});";

		return data;
	}
}
