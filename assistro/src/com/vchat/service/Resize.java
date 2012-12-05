package com.vchat.service;



import java.io.IOException;
import java.io.OutputStream;
 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.Image.Format;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
 
public class Resize extends HttpServlet {
 
    /**
     * Default Serialization UID
     */
    private static final long serialVersionUID = 1L;
 
    private BlobstoreService blobstoreService = BlobstoreServiceFactory
            .getBlobstoreService();
 
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        //Get the Blob Key and grab the Image from the Blobstore
        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
        Image oldImage = ImagesServiceFactory.makeImageFromBlob(blobKey);   
 
        //Create the Image Service Factory and the Resize Transform
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        Transform resize = ImagesServiceFactory.makeResize(100, 70);
 
        //Resize The Image using the transform created above
        Image resizedImage = imagesService.applyTransform(resize, oldImage);

        //Serve the newly transformed image
        byte[] resizedImageData = resizedImage.getImageData();
        OutputStream outputStream = res.getOutputStream();
        outputStream.write(resizedImageData);
    }
}