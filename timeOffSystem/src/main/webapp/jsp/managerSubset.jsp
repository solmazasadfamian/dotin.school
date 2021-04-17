<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.dto.EmployeeDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>employeeTable</title>
    <link rel="stylesheet" type="text/css" href="../resource/table.css">
    <link rel="stylesheet" type="text/css" href="../resource/home.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">

</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>
<%
    EmployeeService employeeServiceT = new EmployeeService();
    List<EmployeeDto> employees = employeeServiceT.findAllById("manager.id", Long.valueOf(request.getParameter("id")));
%>
<div class="container">
<table>
    <thead>
    <tr>
        <th >ردیف
        <th>نام
        <th>نام خانوادگی
        <th>کد ملی
        <th>وضعیت
        <th>
    </thead>
    <tbody>
    <%
        int rowIndex = 0;
        for (EmployeeDto employee : employees) {
            rowIndex++;
    %>
    <tr>
        <td><%=rowIndex%>
        <td><%=employee.getFirstName()%>
        <td><%=employee.getLastName()%>
        <td><%=employee.getNationalCode()%>
        <%
            if (employee.getDisabled().equals(false)){
        %>
        <td>فعال
        <%
            }else {
        %>
        <td>غیر فعال
        <%
            }
        %>
        <td><a href="/jsp/employeeUpdate.jsp?id=<%=employee.getId()%>">ویرایش</a>
        <%
            }
    %>
    </tbody>
</table>
</div>
<footer>
    <%@include file="footer.jsp" %>
</footer>

</body>
</html>
