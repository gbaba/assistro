package com.vchat.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.vchat.beans.VisitorRecord;
import com.vchat.controller.VchatController;

public class ShowExcelData extends HttpServlet {
	
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet
   (HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
  OutputStream out = null;
  String wId = request.getParameter("widgetcode");
  try {
   response.setContentType("application/vnd.ms-excel");
   response.setHeader
     ("Content-Disposition", "attachment; filename=VisitorRecord.xls");
   WritableWorkbook w = 
     Workbook.createWorkbook(response.getOutputStream());
   WritableSheet sheet = w.createSheet("chatVisitor", 0);
   List<VisitorRecord> sheetRecord = VchatController.getVchatController().getUserRecord(wId); 
   System.out.println(sheetRecord.size());

   sheet.setColumnView(0,14);
   sheet.setColumnView(2,14);
   sheet.addCell(new Label(0, 0, "Visitors Name:"));
   sheet.addCell(new Label(2, 0, "Visitors Email:"));
   sheet.setColumnView(1,30);
   sheet.setColumnView(3,50);
   
   sheet.setColumnView(1,60);
   for (int i = 0 ; i < sheetRecord.size(); i++) {
		// First column
	   VisitorRecord vr = sheetRecord.get(i);
		sheet.addCell(new Label(1,i,vr.getVisitorName()));
		// Second column
		sheet.addCell(new Label(3,i,vr.getVisitorEmail()));
	}
   w.write();
   w.close();
  } 
  catch (Exception e){
   throw new ServletException("Exception in Excel Sample Servlet", e);
  } 
  finally{
   if (out != null)
    out.close();
  }
 }
}