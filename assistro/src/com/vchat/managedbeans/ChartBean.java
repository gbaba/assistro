package com.vchat.managedbeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.vchat.beans.Chart;
import com.vchat.beans.User;
import com.vchat.controller.VchatController;

@ManagedBean(name = "chartBean")
@RequestScoped
public class ChartBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int start, end;
	private int mend;
	private String lastMonth, thisMonth;
	private int max;
	private CartesianChartModel linearModel;
	private ChartSeries series1, series2, series3;
	List<Chart> listChart;

	public ChartBean() {
		createLinearModel();
	}

	public CartesianChartModel getLinearModel() {
		return linearModel;
	}

	private void createLinearModel() {
		listChart = VchatController.getVchatController().getChartData(
				DigestUtils.md5Hex(getFromSession().getEmail()));
		linearModel = new CartesianChartModel();
		series1 = new ChartSeries();
		series2 = new ChartSeries();
		series3 = new ChartSeries();
		series3.setLabel("Visitors who chatted me");
		series2.setLabel("Visitors who saw me online");
		series1.setLabel("Visitors to My Site");
		getLast7Days();
		getLastMonthDates();
		if (end <= 7) {
			for (; start <= mend; start++) {
				createEntry(start + lastMonth);
			}
			for (int i = 1; i <= end; i++) {
				createEntry("0" + i + thisMonth);
			}
		} else {
			for (; start <= end; start++) {
				if (start < 10) {
					createEntry("0" + start + thisMonth);
				} else {
					createEntry(start + thisMonth);
				}

			}
		}
		linearModel.addSeries(series1);
		linearModel.addSeries(series2);
		linearModel.addSeries(series3);
	}

	private void createEntry(String date) {
		boolean check = false;
		for (Chart chart : listChart) {
			if (chart.getDay().contains(date)) {
				check = true;
				series1.set(getChartForamtedDate(chart.getDay()), chart.getVisitorOnSite());
				series2.set(getChartForamtedDate(chart.getDay()), chart.getSawOnline());
				series3.set(getChartForamtedDate(chart.getDay()), chart.getChated());
				if (max < (chart.getVisitorOnSite() + chart.getSawOnline() + chart
						.getChated())) {
					max = (chart.getVisitorOnSite() + chart.getSawOnline() + chart
							.getChated()) + 10;
				}
			}
		}
		if (!check) {
			series1.set(getChartForamtedDate(date), 0);
			series2.set(getChartForamtedDate(date), 0);
			series3.set(getChartForamtedDate(date), 0);
		}

	}

	private User getFromSession() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext()
				.getSession(false);
		String sessionId = session.getId();
		User user = (User) session.getAttribute(sessionId);
		return user;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	private String getForamtedDate(Date date) {
		SimpleDateFormat simpledateFormat = new SimpleDateFormat("dd");
		String formatedDate = simpledateFormat.format(date);
		return formatedDate;
	}

	private String getMForamtedDate(Date date) {
		SimpleDateFormat simpledateFormat = new SimpleDateFormat("MMyyyy");
		String formatedDate = simpledateFormat.format(date);
		return formatedDate;
	}

	private String getChartForamtedDate(String date) {
		SimpleDateFormat simpledateFormat = new SimpleDateFormat("dd MMM yyyy");
		String formatedDate = date;
		try {
			formatedDate = simpledateFormat.format(new SimpleDateFormat("ddMMyyyy").parse(date));
		} catch (ParseException e) {
			
		}
		return formatedDate;
	}

	private void getLast7Days() {
		Calendar today = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		today.add(Calendar.DATE, -7);
		endDate.add(Calendar.DATE, -1);
		Calendar currentMonth = Calendar.getInstance();
		currentMonth.add(Calendar.MONTH,0);
		thisMonth = getMForamtedDate(currentMonth.getTime());
		start = Integer.parseInt(getForamtedDate(today.getTime()));
		end = Integer.parseInt(getForamtedDate(endDate.getTime())) + 1;

	}

	public void getLastMonthDates() {
		Calendar lastMonthEndDate = Calendar.getInstance();
		lastMonthEndDate.set(Calendar.DATE, 0);
		lastMonth = getMForamtedDate(lastMonthEndDate.getTime());
		mend = Integer.parseInt(getForamtedDate(lastMonthEndDate.getTime()));
	}

}