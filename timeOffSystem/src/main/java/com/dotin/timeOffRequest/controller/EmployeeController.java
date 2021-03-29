package com.dotin.timeOffRequest.controller;

import com.dotin.timeOffRequest.entity.CategoryElement;
import com.dotin.timeOffRequest.entity.Employee;
import com.dotin.timeOffRequest.service.CategoryElementService;
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
    private EmployeeService employeeService = new EmployeeService();
    private CategoryElementService categoryElementService = new CategoryElementService();

    public EmployeeController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request with below info has received : " + request.getParameter("firstName"));
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Employee employee = new Employee();
        if (request.getParameter("id") != null)
            employee.setId(Long.valueOf(request.getParameter("id")));
        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setNationalCode(request.getParameter("nationalCode"));
        employee.setPhoneNumber(request.getParameter("phoneNumber"));
        employee.setAddress(request.getParameter("address"));
        employee.setEmailAddress(request.getParameter("emailAddress"));
        if (request.getParameter("manager") != null && !request.getParameter("manager").isEmpty()) {
            Long managerId = Long.valueOf(request.getParameter("manager"));
            Employee manager = employeeService.findById(managerId);
            employee.setManager(manager != null ? manager : null);
        }
        if (request.getParameter("role") != null) {
            Long roleId = Long.valueOf(request.getParameter("role"));
            CategoryElement role = categoryElementService.findById(roleId);
            employee.setRole(role != null ? role : null);
        }

        if (employee.getId() == null) {
            employeeService.add(employee);
        } else {
            employeeService.update(employee);
        }
        response.sendRedirect("/jsp/employeeTable.jsp");
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
