package com.dotin.timeOffRequest.controller;

import com.dotin.timeOffRequest.dto.AttachmentDto;
import com.dotin.timeOffRequest.dto.EmailDto;
import com.dotin.timeOffRequest.exception.ErrorMessages;
import com.dotin.timeOffRequest.service.AttachmentService;
import com.dotin.timeOffRequest.service.EmailService;
import com.dotin.timeOffRequest.service.EmployeeService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/email-controller")
public class EmailController extends HttpServlet {
    private final static Logger log = Logger.getLogger(EmailController.class.getName());
    private final EmployeeService employeeService = new EmployeeService();
    private final EmailService emailService = new EmailService();

    public EmailController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request with below info has received : " + request.getParameter("firstName"));
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        EmailDto emailDto = new EmailDto();
        if (request.getParameter("sender") != null)
            emailDto.setSenderId(Long.valueOf(request.getParameter("sender")));
        if (request.getParameterValues("receiver[]") != null) {
            String[] receivers = request.getParameterValues("receiver[]");
            Set<Long> receiverId = new HashSet<>();
            for (String r : receivers)
                receiverId.add(Long.valueOf(r));
            emailDto.setReceiverId(receiverId);
        }
        emailDto.setSubject(request.getParameter("subject"));
        emailDto.setDescription(request.getParameter("description"));
        EmailDto savedEmail = emailService.add(emailDto);

        if (request.getParameter("file_name") != null) {
            AttachmentDto attachmentDto = new AttachmentDto();
            attachmentDto.setEmailId(savedEmail.getId());
            attachmentDto.setFileName(request.getParameter("file_name"));
            AttachmentService attachmentService = new AttachmentService();
            attachmentService.add(attachmentDto);
        }
        response.setStatus(200);
        String message = "<p><span>" + ErrorMessages.SUCCESS_MESSAGE + "</span></p>";
        out.print(message);
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request with below id has received : " + request.getParameter("id"));
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action.equals("del")) {
            emailService.delete(Long.valueOf(request.getParameter("id")));
            response.sendRedirect("/jsp/employeeTable.jsp");
        }
    }

}
