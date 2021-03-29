package com.dotin.timeOffRequest.controller;

import com.dotin.timeOffRequest.entity.Attachment;
import com.dotin.timeOffRequest.entity.Email;
import com.dotin.timeOffRequest.entity.Employee;
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
    private EmployeeService employeeService = new EmployeeService();
    private EmailService emailService = new EmailService();

    public EmailController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request with below info has received : " + request.getParameter("firstName"));
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Email email = new Email();
        if (request.getParameter("sender") != null)
            email.setSender(employeeService.findById(Long.valueOf(request.getParameter("sender"))));
        if (request.getParameterValues("receiver") != null) {
            String[] receivers = request.getParameterValues("receiver");
            Set<Employee> receiverEmployee = new HashSet<>();
            for (String r : receivers)
                receiverEmployee.add(employeeService.findById(Long.valueOf(r)));
            email.setReceiver(receiverEmployee);
        }
        email.setSubject(request.getParameter("subject"));
        email.setDescription(request.getParameter("description"));
        Email savedEmail = emailService.add(email);

        if (request.getParameter("file_name") != null) {
            Attachment attachment = new Attachment();
            attachment.setEmail(savedEmail);
            attachment.setFileName(request.getParameter("file_name"));
            AttachmentService attachmentService = new AttachmentService();
            attachmentService.add(attachment);
        }
        response.setStatus(200);
        out.write("<p><strong style=\"text-align: center\">success</strong><br/><span>the request successfully done</span></p>");
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request with below id has received : " + request.getParameter("id"));
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action.equals("del")) {
            employeeService.delete(Long.valueOf(request.getParameter("id")));
            response.sendRedirect("/jsp/employeeTable.jsp");
        }
    }

}
