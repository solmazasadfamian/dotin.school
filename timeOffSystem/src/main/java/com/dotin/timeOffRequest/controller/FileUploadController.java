package com.dotin.timeOffRequest.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;


@WebServlet("/file-upload-controller")

public class FileUploadController extends HttpServlet {
    final static Logger logger = Logger.getLogger(FileUploadController.class);

    public static String fileUploadName = null;
    public static boolean fileUploaded = false;
    java.io.File file = new java.io.File("");
    private String name = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        new File(file.getAbsolutePath() + File.separator + "uploads" + File.separator).mkdir();
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        name = request.getSession().getId() + new File(item.getName()).getName();
                        item.write(new File(file.getAbsolutePath() + File.separator + "uploads" + File.separator + name));
                    }
                }
                JSONArray result = null;
                System.out.println(name);
                fileUploaded = true;
                fileUploadName = name;
                logger.info(result.toString());
                request.setAttribute("message", fileUploadName);
            } catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed due to " + ex);
                logger.info("message" + "File Upload Failed due to " + ex);
            }
        } else {
            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");
            logger.info("Sorry this Servlet only handles file upload request");
        }
    }

}