package com.dotin.timeOffRequest.controller;

import com.dotin.timeOffRequest.dto.TimeOffRequestDto;
import com.dotin.timeOffRequest.exception.BadRequestException;
import com.dotin.timeOffRequest.service.CategoryElementService;
import com.dotin.timeOffRequest.service.EmployeeService;
import com.dotin.timeOffRequest.service.TimeOffRequestService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/time-off-request-controller")
public class TimeOffRequestController extends HttpServlet {
    private final static Logger log = Logger.getLogger(TimeOffRequestController.class.getName());
    private final TimeOffRequestService timeOffRequestService = new TimeOffRequestService();
    private final CategoryElementService categoryElementService = new CategoryElementService();
    private final EmployeeService employeeService = new EmployeeService();

    public TimeOffRequestController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request with below info has received : " + request.getParameter("employee"));
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        TimeOffRequestDto timeOffRequestDto = new TimeOffRequestDto();
        if (request.getParameter("id") != null)
            timeOffRequestDto.setId(Long.valueOf(request.getParameter("id")));
        timeOffRequestDto.setStartTime(request.getParameter("startTime"));
        timeOffRequestDto.setEndTime(request.getParameter("endTime"));
        timeOffRequestDto.setTimeOffDayAmount(Integer.valueOf(request.getParameter("dayAmount")));
        if (request.getParameter("employee") != null) {
            timeOffRequestDto.setEmployeeId(Long.valueOf(request.getParameter("employee")));
        }
        try {
            timeOffRequestService.preAdd(timeOffRequestDto);
            response.setStatus(200);
            out.write("<p><strong style=\"text-align: center\">success</strong><br/><span>the request successfully done</span></p>");
            out.close();
        } catch (BadRequestException e) {
            response.setStatus(400);
            out.write("<p><strong style=\"text-align: center\">error</strong><br/><span>" + e.getErrorMessage() + "</span></p>");
            out.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("request with below info has received : " + request.getParameter("action"));
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action.equals("del") && request.getParameter("id") != null) {
            timeOffRequestService.delete(Long.valueOf(request.getParameter("id")));
            response.sendRedirect(request.getContextPath() + "/jsp/timeOffRequest.jsp?employee=" + request.getParameter("employee"));
        }
        if (action.equals("approve") && request.getParameter("id") != null) {
            TimeOffRequestDto timeOffRequestDto = timeOffRequestService.findById(Long.valueOf(request.getParameter("id")));
            timeOffRequestDto.setTimeOffStatusId(categoryElementService.findByCode(200l).getId());
            timeOffRequestService.update(timeOffRequestDto);
            response.sendRedirect(request.getContextPath() + "/jsp/checkTimeOff.jsp?employee=" + request.getParameter("employee"));
        }
        if (action.equals("reject") && request.getParameter("id") != null) {
            TimeOffRequestDto timeOffRequestDto = timeOffRequestService.findById(Long.valueOf(request.getParameter("id")));
            timeOffRequestDto.setTimeOffStatusId(categoryElementService.findByCode(400l).getId());
            timeOffRequestService.update(timeOffRequestDto);
            timeOffRequestService.updateEmployeeBalance(timeOffRequestDto);

            response.sendRedirect(request.getContextPath() + "/jsp/checkTimeOff.jsp?employee=" + request.getParameter("employee"));
        }
    }
}
