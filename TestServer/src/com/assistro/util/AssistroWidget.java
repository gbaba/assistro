package com.assistro.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.assistro.beans.Chart;
import com.assistro.beans.Chat;
import com.assistro.beans.User;
import com.assistro.beans.WidgetAgents;
import com.assistro.beans.WidgetContents;
import com.assistro.controller.VMessageController;
import com.assistro.controller.VchatController;


public class AssistroWidget {
	WidgetContents wc;
	List<WidgetAgents> wa;
	Chart chart;
	List<Chat> sessionList;
	User user;
	WidgetAgents agent;
	//Chat chat;
	private int count;

	public AssistroWidget(String id) {
		wc = VchatController.getVchatController().getWidgetData(id);
		wa = VchatController.getVchatController().getOperatorList(id);
		chart = VchatController.getVchatController().getChartData(id,
				new SimpleDateFormat("ddMMyyyy").format(new Date()));
		this.agent = VchatController.getVchatController().getChatAgent(id);
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
			//chart.setWidgetId(wc.getWidgetId());
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

	public WidgetAgents getAgent() {
//		for(int i=0; i<wa.size();i++){
//			if(wa.contains(this.agent)){
//				agent =(WidgetAgents) wa.get(i);
//			}
//		}
		return this.agent;
	}

	public void setAgent(WidgetAgents agent) {
		this.agent = agent;
	}

	public String getData() {
		String data = "function createWidget(){"
				+ "var  widget_jquery,s12,widgetjs;var widget_css,help_me,img,imgUrl,beforelink,bigimg,bigimgUrl,agentName,newMsgDiv,msgCou,colWrapper,clear,jheader,agentImage,agentName,agentDes,webLink,inLink;var divTag,supmsg,before_chat_header_text,before_chat_collapsed,before_collapsed_header,before_chat_content,before_chat_header,before_chat_footer,before_name,before_email,collapsed,username,usernamelabel,expanded,welMes,host_name,minimizeBut,crossBut,header,buttons, content, footer,message_panel,chat_area,send_button,before_chat,user_email,user_question,message_label,chat_but;var myhead;"
					
				+ "widget_jquery = document.createElement('script');"
				+ "widgetjs = document.createElement('script');"
				+ "widget_css = document.createElement('link');"
				+ "var headId = document.getElementsByTagName('body')[0];"
				+ "widget_css.href='"
				+ Util.url
				+"/admin/css/assistroStandard.css';"
				+ "widget_css.type='text/css';"
				+ "widget_css.rel='stylesheet';"				

				+ "widgetjs.src = '"
				+ Util.url
				+ "/js/iamassistro.js';"


				+ "headId.appendChild(widgetjs);"
				+ "headId.appendChild(widget_css);"
				
				+ "divTag = document.createElement('div');"	
				+ "divTag.className = 'chat_container';"
				
				//agent image
				+ "img = document.createElement('div');"	
				+ "img.className = 'agent_image';"
				+ "img.id = 'aImg';"	
				+ "img.setAttribute ('style','display:block');"
				
//				+ "agentName = document.createElement('p');"
//				+ "agentName.innerHTML='Agent_Name';"
				
				+ "imgUrl = document.createElement('img');"
				+ "imgUrl.id = 'aImgurl';"
				//+ "imgUrl.src='img';"	
				+ "imgUrl.setAttribute ('width','42');"
				+ "imgUrl.setAttribute ('height','42');"
				//+ "imgUrl.setAttribute ('style','border:2px double #CCCCCC');"
//				+ "img.appendChild(agentName);"
				//+ "img.appendChild(imgUrl);"
				
//				//highlight image
//				+ "bigimg = document.createElement('div');"
//				+ "bigimg.id = 'showImg';"	
//				+ "bigimg.setAttribute ('style','display:none');"
//				
//				+ "agentName = document.createElement('p');"
//				+ "agentName.innerHTML='Agent_Name';"
//				
//				+ "bigimgUrl = document.createElement('img');"
//				+ "bigimgUrl.src='Agent';"	
//				+ "bigimgUrl.setAttribute ('width','110');"
//				+ "bigimgUrl.setAttribute ('height','80');"
//				+ "bigimgUrl.setAttribute ('style','border:2px double #CCCCCC');"
//				
//				+ "bigimg.appendChild(agentName);"
//				+ "bigimg.appendChild(bigimgUrl);"

				// before chat widget
				+ "before_chat = document.createElement('div');"
				+ "before_chat.setAttribute ('style','display:none');"
				+ "before_chat.id = 'before-chat-wrapper';"
				
				// before Chat Header
				+ "before_chat_header = document.createElement('div');"
				+ "before_chat_header.id = 'before-chat-top-window';"
				
				+ "before_chat_header_text = document.createElement('h3');"
				+ "before_chat_header_text.innerHTML='Need Help';"

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
				+ "before_chat_footer.id = 'chat-before-copy';"
				
				+ "beforelink = document.createElement('p');"
				+ "beforelink.innerHTML='i am ';"
				
				+ "link1 = document.createElement('a');"
				//+ "link1.className = 'host';"
				+ "link1.innerHTML='VIRTISM';"
				+ "link1.href='http://www.assistro.com';"
				//+ "before_chat_footer.className = 'footer';"
				+ "beforelink.appendChild(link1);"
				
				+ "before_chat_footer.appendChild(beforelink);"

				//before Chat Content
				+ "help_me = document.createElement('p');"
				//+ "help_me.setAttribute ('style','color:green;margin-left:18px;margin-top:0px;');"
				+ "help_me.innerHTML = 'Enter your details below to chat with our customer service team';"
				
				+ "before_name = document.createElement('label');"
				+ "before_name.innerHTML = 'Full Name';"
				
				+ "username = document.createElement('input');"
				+ "username.type = 'text';"
				//+ "username.placeholder = 'Full Name';"
				+ "username.className = 'input-beforeCh';"
				+ "username.id = 'beforeName';"
				
				+ "before_email = document.createElement('label');"
				+ "before_email.innerHTML = 'Email';"
				
				+ "user_email = document.createElement('input');"
				+ "user_email.type = 'text';"
				//+ "user_email.placeholder = 'Email';"
				+ "user_email.className = 'input-beforeCh';"
				+ "user_email.id = 'beforeEmail';"

				+ "chat_but= document.createElement('a');"
				+ "chat_but.className='before-chat-btn';"
				+ "chat_but.id='sendToChat';"
				+ "chat_but.setAttribute ('style','margin-left:226px;');"

				+ "before_chat_content = document.createElement('div');"
				+ "before_chat_content.id = 'before-chat-content';"
				//+ "before_chat_content.setAttribute ('style','height:231px');"

				+ "before_chat_content.appendChild(help_me);"
				+ "before_chat_content.appendChild(before_name);"
				+ "before_chat_content.appendChild(username);"
				+ "before_chat_content.appendChild(before_email);"
				+ "before_chat_content.appendChild(user_email);"
				+ "before_chat_content.appendChild(chat_but);"

				+ "before_chat.appendChild(before_chat_header);"
				+ "before_chat.appendChild(before_chat_content);"
				+ "before_chat.appendChild(before_chat_footer);"
				
				// expanded widget
				+ "expanded = document.createElement('div');"
				+ "expanded.className = 'expand-menu';"
				+ "expanded.id = 'chat-app-wrapper';"
				+ "expanded.setAttribute ('style','display:none');"
				
				// Expanded Widget header
				+ "header = document.createElement('div');"
				//+ "header.className = 'header';"
				+ "header.id = 'chat-app-top-window';"
				
				+ "supmsg = document.createElement('h3');"
				+ "supmsg.innerHTML='Widget_Virtism_Chat_Title';"

				+ "buttons = document.createElement('div');"
				+ "buttons.className = 'buttons';"

				+ "minimizeBut = document.createElement('a');"
				+ "minimizeBut.className = 'minimize';"
				+ "buttons.appendChild(minimizeBut);"

				+ "crossBut = document.createElement('a');"
				+ "crossBut.className = 'cross';"
				+ "buttons.appendChild(crossBut);"
								
				+ "clear = document.createElement('div');"
				+ "clear.className = 'clear';"				
				
				+ "jheader = document.createElement('div');"
				+ "jheader.id = 'chat-app-header';"
				
				+ "agentImage = document.createElement('div');"
				+ "agentImage.id = 'chat-app-avatar';"
				+ "agentImage.appendChild(imgUrl);"
				
				+ "agentName = document.createElement('h5');"
				+ "agentName.id='agentName';"
				
				+ "agentDes = document.createElement('h6');"
				+ "agentDes.innerHTML='Agent_Designation';"
				
				+ "header.appendChild(supmsg);"
				+ "header.appendChild(buttons);"
				//+ "header.appendChild(clear);"
				
				+ "clear1 = document.createElement('div');"
				+ "clear1.className = 'clear';"
								
				+ "jheader.appendChild(agentImage);"
				+ "jheader.appendChild(agentName);"
				+ "jheader.appendChild(agentDes);"
				+ "jheader.appendChild(clear1);"
				
				
				// Expanded widget footer
				+ "footer = document.createElement('div');"
				+ "footer.id = 'chat-app-footer';"
				
				+ "chat_area = document.createElement('input');"
				+ "chat_area.className = 'type_text';"
				+ "chat_area.type = 'text';"
				+ "chat_area.name = 'visitors_name';"	
				+ "chat_area.id = 'sendmess';"
				
				+ "send_button= document.createElement('a');"
				+ "send_button.className = 'chat-send-btn';"
				//+ "send_button.value = 'send';"
				//+ "send_button.type='button';"
				
				+ "footer.appendChild(chat_area);"
				+ "footer.appendChild(send_button);"
				
				+ "webLink = document.createElement('div');"
				+ "webLink.id = 'chat-app-copy';"
				
				+ "inLink = document.createElement('p');"
				+ "inLink.innerHTML='i am ';"
				
				+ "link1 = document.createElement('a');"
				//+ "link1.className = 'host';"
				+ "link1.innerHTML='VIRTISM';"
				+ "link1.href='http://www.assistro.com';"
				+ "link1.onclick='return ! parent.window.open(this.href)';"
				+ "inLink.appendChild(link1);"
				
				+ "webLink.appendChild(inLink);"
	
				//expanded Widget Content
				+ "content = document.createElement('div');"
				//+ "content.className = 'content';"
				+ "content.id = 'chat-app-content';"

				+ "welMes = document.createElement('p');"
				+ "welMes.innerHTML = 'Welcome_To_Assistro';"
				+ "welMes.className = 'top_label';"

				+ "host_name = document.createElement('p');"
				+ "host_name.className = 'host_name';"
				
				+ "message_panel = document.createElement('div');"
				+ "message_panel.className = 'content';"
				+ "message_panel.id = 'content_1';"
				+ "host_name.innerHTML = 'Welcome_To_Customer_Help';"
				//+ "img.appendChild(welMes);"
				+ "message_panel.appendChild(host_name);"				
				
				//+ "content.appendChild(img);"
				+ "content.appendChild(welMes);"
				+ "content.appendChild(message_panel);"

				+ "expanded.appendChild(header);"
				+ "expanded.appendChild(jheader);"
				+ "expanded.appendChild(content);"
				+ "expanded.appendChild(footer);"
				+ "expanded.appendChild(webLink);"
				
				//Online collaspsed state
				
				+ "colWrapper = document.createElement('div');"
				+ "colWrapper.id = 'col_wrap';"
				+ "colWrapper.setAttribute ('style','display:block');"
				
				
				+ "newMsgDiv = document.createElement('div');"
				+ "newMsgDiv.id = 'new_msg';"
				+ "newMsgDiv.setAttribute ('style','display:none');"
				
				+ "msgCou = document.createElement('p');"
				+ "msgCou.id = 'msg_counter';"	
				+ "newMsgDiv.appendChild(msgCou);"
				
				+ "collapsed = document.createElement('div');"
				+ "collapsed.id = 'live-chat-btn';"
				+ "collapsed.setAttribute ('style','display:block');"
				
				+ "myhead = document.createElement('h3');"
				+ "myhead.innerHTML='Live_Chat';"
							
				+ "collapsed.appendChild(myhead);"
				
				+ "colWrapper.appendChild(newMsgDiv);"
				+ "colWrapper.appendChild(collapsed);"
				
								
				//before Chat Collpased State
				+ "before_chat_collapsed = document.createElement('div');"
				+ "before_chat_collapsed.id = 'before_btn';"
				+ "before_chat_collapsed.setAttribute ('style','display:none');"
				
				+ "before_collapsed_header = document.createElement('h3');"
				//+ "before_collapsed_header.className = 'header';"
				+ "before_collapsed_header.innerHTML = 'Start_Chat';"

				//+ "buttons = document.createElement('div');"
				//+ "buttons.className = 'buttons';"

				//+ "crossBut = document.createElement('a');"
				//+ "crossBut.className = 'cross';"
				//+ "buttons.appendChild(crossBut);"

				//+ "before_collapsed_header.appendChild(buttons);"
				
				+ "before_chat_collapsed.appendChild(before_collapsed_header);"
				
				+ "divTag.appendChild(before_chat_collapsed);"
				+ "divTag.appendChild(colWrapper);"
				+ "divTag.appendChild(expanded);"		
				+ "divTag.appendChild(before_chat);"

				+ "document.getElementsByTagName('body')[0].appendChild(divTag);"
				//+"jQuery('body').append(divTag);"	

				+ "}window.onload = createWidget;";
		

		return data;
	}

	
	public String getExpireWidgetData() {
		String data = "function createWidget(){"
				+"var widget_css,headId,divTag,offline_mode,collapsed_offline_header,collapsed_offline;"

				+ "widget_css = document.createElement('link');"
				+ "var headId = document.getElementsByTagName('body')[0];"
				+ "widget_css.href='"
				+ Util.url
				//+ "/css/widget.css';"
				+"/admin/css/assistroStandard.css';"
				+ "widget_css.type='text/css';"
				+ "widget_css.rel='stylesheet';"				

				+ "headId.appendChild(widget_css);"
				
				+ "divTag = document.createElement('div');"
				+ "divTag.className = 'chat_container';"			
				
				//+ "collapsed_offline = document.createElement('div');"
				//+ "collapsed_offline.className = 'collapsed_offline';"
				//+ "collapsed_offline.setAttribute ('style','display:block');"
				
				+ "collapsed_offline = document.createElement('div');"
				+ "collapsed_offline.id = 'live-chat-btn';"
				+ "collapsed_offline.setAttribute ('style','display:block');"
							
				//offline Collapsed State
				+ "collapsed_offline_header = document.createElement('h3');"
				//+ "collapsed_offline_header.className = 'header';"
				+ "collapsed_offline_header.innerHTML = 'Package expired!!';"
				
				+ "collapsed_offline.appendChild(collapsed_offline_header);"


				+ "divTag.appendChild(collapsed_offline);"

				+ "document.getElementsByTagName('body')[0].appendChild(divTag);"
				//+"jQuery('body').append(divTag);"	
				+ "}window.onload = createWidget;";
		

		return data;
	}
	
	public String getOffflineData(){
		String data = "function createWidget(){"
				+ "var  widget_jquery,s12,widgetjs;var widget_css,topmsg,clear2,last_id,session,mylink;var divTag,supmsg,collapsed_offline_header,collapsed_offline,offline_header,offline_footer,offline_content,offlineContent,username,usernamelabel,minimizeBut,crossBut,header,buttons, content, footer,user_email,user_question,email_label,message_label,chat_but,offline_mode,off_name,off_name_label,off_email,off_email_label,off_msg,off_msg_label,off_but;var myhead;"
					
				+ "widget_jquery = document.createElement('script');"
				+ "widgetjs = document.createElement('script');"
				+ "widget_css = document.createElement('link');"
				+ "var headId = document.getElementsByTagName('body')[0];"
				+ "widget_css.href='"
				+ Util.url
				//+ "/css/widget.css';"
				+"/admin/css/assistroStandard.css';"
				+ "widget_css.type='text/css';"
				+ "widget_css.rel='stylesheet';"				
				//+ "widget_jquery.src = 'http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js';"

				+ "widgetjs.src = '"
				+ Util.url
				+ "/js/assistroff.js';"


				+ "headId.appendChild(widgetjs);"
				+ "headId.appendChild(widget_css);"
				
				+ "divTag = document.createElement('div');"
				+ "divTag.className = 'chat_container';"
						
				//offline mode chat
				+ "offline_mode = document.createElement('div');"
				+ "offline_mode.id = 'chat-off-wrapper';"
				+ "offline_mode.setAttribute ('style','display:none');"
							
				//+ "offline_mode = document.createElement('div');"
				//+ "offline_mode.setAttribute ('style','display:none');"
				//+ "offline_mode.className = 'offline_state';"

				// offline Header
				+ "offline_header = document.createElement('div');"
			//	+ "offline_header.className = 'header';"
				+ "offline_header.id = 'chat-off-top-window';"
				
				+ "topmsg = document.createElement('h3');"
				+ "topmsg.innerHTML='Offline_Title';"


				+ "buttons = document.createElement('div');"
				+ "buttons.className = 'buttons';"

				+ "minimizeBut = document.createElement('span');"
				+ "minimizeBut.className = 'minimize';"
				+ "buttons.appendChild(minimizeBut);"

				+ "crossBut = document.createElement('span');"
				+ "crossBut.className = 'cross';"
				+ "buttons.appendChild(crossBut);"
				
				+ "clear2 = document.createElement('div');"
				+ "clear2.className = 'clear';"

				+ "offline_header.appendChild(topmsg);"
				+ "offline_header.appendChild(buttons);"
				+ "offline_header.appendChild(clear2);"
				
				// offline footer
				+ "offline_footer = document.createElement('div');"
				+ "offline_footer.id = 'chat-off-copy';"
				
				+ "mylink = document.createElement('p');"
				+ "mylink.innerHTML='i am ';"
				
				+ "link1 = document.createElement('a');"
				+ "link1.className = 'host';"
				+ "link1.innerHTML='VIRTISM';"
				+ "link1.href='http://www.assistro.com';"
				
				+ "mylink.appendChild(link1);"	

				+ "offline_footer.appendChild(mylink);"
					
				// offline Content
				+ "host_name = document.createElement('h5');"
				+ "host_name.innerHTML = 'Not_There';"
				
				+ "off_name_label = document.createElement('label');"
				+ "off_name_label.innerHTML = 'Full Name';"
				

				+ "off_name = document.createElement('input');"
				+ "off_name.type = 'text';"
				//+ "off_name.placeholder = 'Full Name';"
				+ "off_name.className = 'input-offbe';"
				+ "off_name.id = 'off_name';"
				//+ "off_name.setAttribute ('style','margin-top:2px;');"

				+ "off_email_label = document.createElement('label');"
				+ "off_email_label.innerHTML = 'Email';"

				+ "off_email = document.createElement('input');"
				+ "off_email.type = 'text';"
				+ "off_email.className = 'input-offbe';"
				+ "off_email.id = 'off_email';"
				//+ "off_email.placeholder = 'Email';"

				+ "off_msg_label = document.createElement('label');"
				+ "off_msg_label.innerHTML = 'Enter Your Message';"
				+ "off_msg = document.createElement('textarea');"
				+ "off_msg.cols = '31';"
				+ "off_msg.className = 'text-offbe';"
				+ "off_msg.id = 'off_msg';"
				//+ "off_msg.placeholder = 'Your Message..';"

				+ "off_but= document.createElement('a');"
				+ "off_but.className='email-send-btn';"
				//+ "off_but.setAttribute ('style','margin-left:245px; margin-top:4px');"

				
				+ "offlineContent = document.createElement('div');"
				+ "offlineContent.id = 'chat-off-content';"
				
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
				
				//+ "collapsed_offline = document.createElement('div');"
				//+ "collapsed_offline.className = 'collapsed_offline';"
				//+ "collapsed_offline.setAttribute ('style','display:block');"

				//offline Collapsed State
				+ "collapsed_offline = document.createElement('div');"
				+ "collapsed_offline.id = 'offline_btn';"
				+ "collapsed_offline.setAttribute ('style','display:block');"
				
				+ "collapsed_offline_header = document.createElement('h3');"
				+ "collapsed_offline_header.innerHTML = 'Live_Chat_offline';"
				
				//+ "collapsed_offline_header = document.createElement('div');"
				//+ "collapsed_offline_header.className = 'header';"
				//+ "collapsed_offline_header.innerHTML = 'Live_Chat_offline';"

				//+ "buttons = document.createElement('div');"
				//+ "buttons.className = 'buttons';"

				//+ "crossBut = document.createElement('a');"
				//+ "crossBut.className = 'cross';"
				//+ "buttons.appendChild(crossBut);"

				//+ "collapsed_offline_header.appendChild(buttons);"
				
				+ "collapsed_offline.appendChild(collapsed_offline_header);"
				
				+ "divTag.appendChild(collapsed_offline);"
				+ "divTag.appendChild(offline_mode);"

				+ "document.getElementsByTagName('body')[0].appendChild(divTag);"
				//+"jQuery('body').append(divTag);"	

				+ "}window.onload = createWidget;";
		

		return data;
	}
}
