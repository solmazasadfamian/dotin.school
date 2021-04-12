<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.entity.Employee" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.service.CategoryElementService" %>
<%@ page import="com.dotin.timeOffRequest.dto.EmployeeDto" %>
<%@ page import="com.dotin.timeOffRequest.dto.CategoryElementDto" %>
<%@ page import="com.dotin.timeOffRequest.mapper.EmployeeMapper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>dateTimeSelect</title>
    <link rel="stylesheet" type="text/css" href="../resource/home.css">
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>
<form action="/jsp/timeOffRequestSaveForm.jsp?dateTime">
    <input type="hidden" id="employee" name="employee" value="<%=request.getParameter("employee")%>"/>
    <div class="row">
    <div class="col-25">
        <label for="dateTime">نوع مرخصی</label>
    </div>
    <div class="col-75">
        <input type="radio" id="dateTime" name="dateTime" value="date">روزانه
        <input type="radio"  name="dateTime" value="time">ساعتی

    </div>
</div>
    <div class="row" style="float: right">
        <input id="save" type="submit" value="بعدی">
    </div>
</form>

<footer>
    <%@include file="footer.jsp" %>
</footer>
</body>
</html>
