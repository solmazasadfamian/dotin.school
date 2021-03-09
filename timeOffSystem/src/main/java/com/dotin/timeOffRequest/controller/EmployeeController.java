package com.dotin.timeOffRequest.controller;

import com.dotin.timeOffRequest.entity.Employee;
import com.dotin.timeOffRequest.service.CategoryElementService;
import com.dotin.timeOffRequest.service.EmployeeService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EmployeeController")
public class EmployeeController extends HttpServlet {
    EmployeeService employeeService = new EmployeeService();
    CategoryElementService categoryElementService = new CategoryElementService();

    public EmployeeController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
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
            employee.setManager(employeeService.findById(managerId));
        }
        if (request.getParameter("role") != null) {
            Long roleId = Long.valueOf(request.getParameter("role"));
            employee.setRole(categoryElementService.findById(roleId));
        }
        try {
            if (employee.getId() == null) {
                employeeService.add(employee);
            } else {
                employeeService.update(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("/jsp/employeeTable.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action.equals("del")) {
            employeeService.delete(Long.valueOf(request.getParameter("id")));
            response.sendRedirect("/jsp/employeeTable.jsp");
        }
    }
}
