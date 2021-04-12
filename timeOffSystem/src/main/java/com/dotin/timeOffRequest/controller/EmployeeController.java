package com.dotin.timeOffRequest.controller;

import com.dotin.timeOffRequest.dto.EmployeeDto;
import com.dotin.timeOffRequest.service.EmployeeService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/employee-controller")
public class EmployeeController extends HttpServlet {
    private final static Logger log = Logger.getLogger(EmployeeController.class.getName());
    private final EmployeeService employeeService = new EmployeeService();

    public EmployeeController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request with below info has received : " + request.getParameter("firstName"));
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        EmployeeDto employeeDto = new EmployeeDto();
        if (request.getParameter("id") != null)
            employeeDto.setId(Long.valueOf(request.getParameter("id")));
        employeeDto.setFirstName(request.getParameter("firstName"));
        employeeDto.setLastName(request.getParameter("lastName"));
        employeeDto.setNationalCode(request.getParameter("nationalCode"));
        employeeDto.setPhoneNumber(request.getParameter("phoneNumber"));
        employeeDto.setAddress(request.getParameter("address"));
        employeeDto.setEmailAddress(request.getParameter("emailAddress"));
        if (request.getParameter("manager") != null && !request.getParameter("manager").isEmpty()) {
            employeeDto.setManagerId(Long.valueOf(request.getParameter("manager")));
        }
        if (request.getParameter("role") != null) {
            employeeDto.setRoleId(Long.valueOf(request.getParameter("role")));
        }

        if (employeeDto.getId() == null) {
            employeeService.add(employeeDto);
        } else {
            employeeService.update(employeeDto);
        }
        response.sendRedirect("/jsp/employeeTable.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request with below id has received : " + request.getParameter("id"));
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action.equals("del")) {
            employeeService.delete(Long.valueOf(request.getParameter("id")));
            response.sendRedirect("/jsp/employeeTable.jsp");
        }
        if (action.equals("disable")) {
            EmployeeDto employeeDto = employeeService.findById(Long.valueOf(request.getParameter("id")));
            employeeDto.setDisabled(true);
            employeeService.update(employeeDto);
            response.sendRedirect("/jsp/employeeTable.jsp");
        }
        if (action.equals("active")) {
            EmployeeDto employeeDto = employeeService.findById(Long.valueOf(request.getParameter("id")));
            employeeDto.setDisabled(false);
            employeeService.update(employeeDto);
            response.sendRedirect("/jsp/employeeTable.jsp");
        }
    }
}
