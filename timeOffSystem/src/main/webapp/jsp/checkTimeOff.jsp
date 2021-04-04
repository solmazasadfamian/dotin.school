<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.service.TimeOffRequestService" %>
<%@ page import="com.dotin.timeOffRequest.entity.TimeOffRequest" %>
<%@ page import="com.dotin.timeOffRequest.service.CategoryElementService" %>
<%@ page import="com.dotin.timeOffRequest.dto.EmployeeDto" %>
<%@ page import="com.dotin.timeOffRequest.dto.TimeOffRequestDto" %>
<%@ page import="com.dotin.timeOffRequest.mapper.TimeOffRequestMapper" %>
<%@ page import="com.dotin.timeOffRequest.dto.CategoryElementDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>timeOffRequest</title>
    <link rel="stylesheet" type="text/css" href="../resource/saveForm.css">
    <link rel="stylesheet" type="text/css" href="../resource/table.css">
    <link rel="stylesheet" type="text/css" href="../resource/home.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
</head>

<body>
<header>
    <%@include file="header.jsp" %>
</header>
<%
    EmployeeService employeeService = new EmployeeService();
    List<EmployeeDto> employees = employeeService.findAll();
%>
<div class="container" style="width: 491.37px;">
    <form action="/jsp/checkTimeOff.jsp" method="post">
        <div class="row">
            <div class="col-25">
                <label for="employee">manager</label>
            </div>
            <div class="col-75">
                <%
                    if (request.getParameter("employee") != null) {
                        EmployeeDto employee = employeeService.findById(Long.valueOf(request.getParameter("employee")));
                %>
                <select id="employee" name="employee" value="<%=employee.getId()%>">
                        <%
                        }else {
                    %>
                    <select id="employee" name="employee">
                        <%
                            }
                        %>
                        <option></option>
                        <%
                            EmployeeDto selectedEmployee = null;
                            if (request.getParameter("employee") != null) {
                                selectedEmployee = employeeService.findById(Long.valueOf(request.getParameter("employee")));
                            }
                            for (EmployeeDto employee : employees) {
                                if (employee.equals(selectedEmployee)) {
                        %>
                        <option value="<%=employee.getId()%>"
                                selected><%=employee.getFirstName()%>&nbsp;<%=employee.getLastName()%>
                                <%
                            }else {
                        %>
                        <option value="<%=employee.getId()%>"><%=employee.getFirstName()%>&nbsp;<%=employee.getLastName()%>
                            <%
                                }
                            %>
                        </option>
                        <%
                            }
                        %>
                    </select>
            </div>
        </div>
        <div class="row">
            <input type="submit" value="SELECT">
        </div>
    </form>
</div>

<%
    if (request.getParameter("employee") != null) {
        TimeOffRequestService timeOffRequestService = new TimeOffRequestService();
        List<TimeOffRequestDto> timeOffRequestList = timeOffRequestService.findAllByManagerId(Long.valueOf(request.getParameter("employee")));
        CategoryElementService categoryElementService = new CategoryElementService();
%>
<table>
    <thead>
    <tr>
        <th>ID
        <th>first name
        <th>last name
        <th>start time
        <th>end time
        <th>day amount
        <th>status
        <th>
        <th>
        <th>
    </thead>
    <tbody>


    <%
        for (TimeOffRequestDto timeOffRequestDto : timeOffRequestList) {
            TimeOffRequestMapper timeOffRequestMapper = new TimeOffRequestMapper();
            TimeOffRequest timeOffRequest = timeOffRequestMapper.toEntity(timeOffRequestDto);

    %>
    <tr>
        <td><%=timeOffRequest.getId()%>
        <td><%=timeOffRequest.getEmployee().getFirstName()%>
        <td><%=timeOffRequest.getEmployee().getLastName()%>
        <td><%=timeOffRequest.getStartTime()%>
        <td><%=timeOffRequest.getEndTime()%>
        <td><%=timeOffRequest.getTimeOffDayAmount()%>
                <%
          CategoryElementDto timeOffStatus = categoryElementService.findById(timeOffRequest.getTimeOffStatus().getId());
        %>
        <td><%=timeOffStatus.getName()%>

        <td>
            <a href="/time-off-request-controller?employee=<%=request.getParameter("employee")%>&action=approve&id=<%=timeOffRequest.getId()%>">APPROVE</a>
        <td>
            <a href="/time-off-request-controller?employee=<%=request.getParameter("employee")%>&action=reject&id=<%=timeOffRequest.getId()%>">REJECT</a>

                <%
        }
     %>

    </tbody>
</table>
<%
    }
%>

<footer>
    <%@include file="footer.jsp" %>
</footer>


</body>
</html>