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
<input type="hidden" id="employee" name="employee" value="<%=request.getParameter("employee")%>"/>

<%
    EmployeeService employeeService = new EmployeeService();
    List<EmployeeDto> employees = employeeService.findAll();
%>
<div class="container" style="width: 491.37px;">
    <form action="/jsp/timeOffRequest.jsp" method="post">
        <div class="row">
            <div class="col-25">
                <label for="employee" style="float: right">کارمند</label>
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
        <div class="row" style="float: left">
            <input type="submit" value="انتخاب">
        </div>
    </form>
</div>
<div class="container">
<%
    if (request.getParameter("employee") != null) {
        TimeOffRequestService timeOffRequestService = new TimeOffRequestService();
        List<TimeOffRequestDto> timeOffRequestList = timeOffRequestService.findAllById("employee.id", Long.valueOf(request.getParameter("employee")));
        CategoryElementService categoryElementService = new CategoryElementService();
%>

<table>
    <thead>
    <tr>
        <th>ردیف
        <th>تاریخ شروع
        <th>تاریخ پایان
        <th>تعداد روز
        <th>ساعت شروع
        <th>ساعت پایان
        <th>وضعیت
        <th>
        <th>
        <th>
    </thead>
    <tbody>


    <%
        int rowIndex = 0;
        for (TimeOffRequestDto timeOffRequest : timeOffRequestList) {
            rowIndex++;
    %>
    <tr>
        <td><%=rowIndex%>
        <td><%=timeOffRequest.getStartDate()%>
        <td><%=timeOffRequest.getEndDate()%>
        <td><%=timeOffRequest.getTimeOffDayAmount()%>
        <%
            if (timeOffRequest.getStartTime()!= null){
        %>
        <td><%=timeOffRequest.getStartTime()%>
        <%
            }else {
        %>
        <td>-
        <%
            }
        %>
        <%
            if (timeOffRequest.getEndTime()!=null){
        %>
        <td><%=timeOffRequest.getEndTime()%>
        <%
            }else {
        %>
        <td>-
        <%
            }
        %>
                <%
          CategoryElementDto timeOffStatus = categoryElementService.findById(timeOffRequest.getTimeOffStatusId());
        %>
        <td><%=timeOffStatus.getName()%>
                <%
            if (timeOffStatus.getCode()==300){
        %>
        <td>
            <a href="/time-off-request-controller?employee=<%=request.getParameter("employee")%>&action=del&id=<%=timeOffRequest.getId()%>">حذف</a>
        <td>
            <a href="/jsp/timeOffRequestUpdate.jsp?employee=<%=request.getParameter("employee")%>&id=<%=timeOffRequest.getId()%>">ویرایش</a>
                <%
        }else {
                %>

                <%
        }
            }
     %>

    </tbody>
</table>
<button onclick="window.location.href = 'dateTime.jsp?employee=<%=request.getParameter("employee")%>';"
        style="margin-right: 15px ; color: white; background-color: #3c1a64; padding: 7px">اضافه کردن درخواست جدید
</button>

<%
    }
%>
</div>

<footer>
    <%@include file="footer.jsp" %>
</footer>


</body>
</html>
