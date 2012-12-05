package com.vchat.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.vchat.beans.User;
import com.vchat.beans.WidgetContents;
import com.vchat.controller.VchatController;
import com.vchat.util.Util;

@ManagedBean(name = "widgetBean")
@RequestScoped
public class WidgetBean {

	private String header;

	private WidgetContents wc;
	private String widgetCode;

	public String getHeader() {
		header = "<div class='virtism_lw'> <h3 class='label'>Collapsed state</h3><div class='collapsed'>/</div><h3 class='label'>Expanded state</h3>      <div class='expanded'>        <div class='header'>         </div>        <div class='content'>                 </div>        <div class=/'footer'>         </div>      </div>    </div>'";
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getWidgetCode() {
		User user = getFromSession();
		String userEmail = DigestUtils.md5Hex(user.getEmail()).toString();
		if (widgetCode == null) {

			widgetCode = "<!-- Start assistro code --><script type='text/javascript'>/*{literal}<![CDATA[*/var virtism_config = {client_id: 1,widget_code:'"
					+ userEmail
					+ "'};/*]]>{/literal}*/</script><script type=\"text/javascript\" src=\""
					+ Util.url
					+ "/assistroWidget.jsp?widget_code="
					+ userEmail
					+ "\"></script><!-- End assistro code -->";

		}
		return widgetCode;
	}

	public void setWidgetCode(String widgetCode) {
		this.widgetCode = widgetCode;
	}

	public WidgetContents getWc() {
		if (wc == null) {
			wc = VchatController.getVchatController().getWidgetData(
					getWidgetId());
		}
		return wc;
	}

	public void setWc(WidgetContents wc) {
		this.wc = wc;
	}

	public void updateWid() {
		wc.setWidgetId(getWidgetId());
		wc = VchatController.getVchatController().updateWidget(this.wc);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Widget Updated:",
						"New Values saved successfully!!"));
	}

	private String getWidgetId() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		String sessionId = session.getId();
		User user = (User) session.getAttribute(sessionId);
		String wId = DigestUtils.md5Hex(user.getEmail()).toString();
		return wId;
	}

	public String fetchData() {
		return "customize";
	}

	private User getFromSession() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		String sessionId = session.getId();
		User user = (User) session.getAttribute(sessionId);
		return user;
	}

}
