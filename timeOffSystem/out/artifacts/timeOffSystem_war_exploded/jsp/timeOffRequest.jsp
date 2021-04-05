<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.service.TimeOffRequestService" %>
<%@ page import="com.dotin.timeOffRequest.service.CategoryElementService" %>
<%@ page import="com.dotin.timeOffRequest.dto.EmployeeDto" %>
<%@ page import="com.dotin.timeOffRequest.dto.TimeOffRequestDto" %>
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
    <form action="/jsp/timeOffRequest.jsp" method="post">
        <div class="row">
            <div class="col-25">
                <label for="employee">employee</label>
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
        List<TimeOffRequestDto> timeOffRequestList = timeOffRequestService.findAllById("employee.id", Long.valueOf(request.getParameter("employee")));
        CategoryElementService categoryElementService = new CategoryElementService();
%>
<table>
    <thead>
    <tr>
        <th>ID
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
        for (TimeOffRequestDto timeOffRequest : timeOffRequestList) {
    %>
    <tr>
        <td><%=timeOffRequest.getId()%>
        <td><%=timeOffRequest.getStartTime()%>
        <td><%=timeOffRequest.getEndTime()%>
        <td><%=timeOffRequest.getTimeOffDayAmount()%>
                <%
          CategoryElementDto timeOffStatus = categoryElementService.findById(timeOffRequest.getTimeOffStatusId());
        %>
        <td><%=timeOffStatus.getName()%>
                <%
            if (timeOffStatus.getName().equals("notapproved")){
        %>
        <td>
            <a href="/time-off-request-controller?employee=<%=request.getParameter("employee")%>&action=del&id=<%=timeOffRequest.getId()%>">DELETE</a>
        <td>
            <a href="/jsp/timeOffRequestUpdate.jsp?employee=<%=request.getParameter("employee")%>&id=<%=timeOffRequest.getId()%>">UPDATE</a>
                <%
        }else {
                %>
        
                <%
        }
            }
     %>

    </tbody>
</table>
<button onclick="window.location.href = 'timeOffRequestSaveForm.jsp?employee=<%=request.getParameter("employee")%>';"
        style="margin-left: 20px; color: white; background-color: #3c1a64; padding: 7px">INSERT TIME OFF REQUEST
</button>

<%
    }
%>

<footer>
    <%@include file="footer.jsp" %>
</footer>


</body>
</html>
