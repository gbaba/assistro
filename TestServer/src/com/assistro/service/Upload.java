package com.assistro.service;



import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.assistro.beans.WidgetAgents;
import com.assistro.controller.VchatController;
import com.assistro.util.Util;

public class Upload extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("myFile");
        
        HttpSession session = req.getSession(false);
        
        WidgetAgents wa =(WidgetAgents) session.getAttribute("AgentInfo");
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        //String myurl= Util.url+"/serve?blob-key="+blobKey.getKeyString();
        String imageUrl = imagesService.getServingUrl(blobKey, 80, false);
        if (blobKey != null) {
        	//wa.setAgentImageURL(Util.url+"/serve?blob-key="+blobKey.getKeyString());
        	wa.setAgentImageURL(imageUrl);
        	VchatController.getVchatController().updateAgent(wa);
        	res.sendRedirect(Util.url+"/uploadDone.jsp?myurl="+imageUrl);
        	//res.sendRedirect(Util.url+"/uploadDone.jsp?myurl="+imageUrl);
        }
//        else {
//            res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
//        }
    }
}