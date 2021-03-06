<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.entity.Employee" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>employeeTable</title>
    <link rel="stylesheet" type="text/css" href="/jsp/table.css">
    <link rel="stylesheet" type="text/css" href="/jsp/home.css">
    <link rel="stylesheet" type="text/css" href="/jsp/header2.css">

</head>
<body>
<header>
    <%@include file="header2.jsp" %>
</header>
<%
    EmployeeService employeeServiceT = new EmployeeService();
    List<Employee> employees = employeeServiceT.findAll();
%>

<table>
    <thead>
    <tr>
        <th>ID
        <th>First Name
        <th>Last Name
        <th>National Code
        <th>
        <th>
        <th>
    </thead>
    <tbody>
    <%
        for (Employee employee : employees) {
    %>
    <tr>
        <td><%=employee.getId()%>
        <td><%=employee.getFirstName()%>
        <td><%=employee.getLastName()%>
        <td><%=employee.getNationalCode()%>
        <td><a href="/jsp/employeeView.jsp?id=<%=employee.getId()%>" target="._top">VIEW</a>
        <td><a href="/EmployeeController?action=del&id=<%=employee.getId()%>">DELETE</a>
        <td><a href="/jsp/employeeUpdate.jsp?id=<%=employee.getId()%>">UPDATE</a>
                <%
            }
    %>
    </tbody>
</table>
<button onclick="window.location.href = 'employeeSaveForm.jsp';"
        style="margin-left: 20px; color: white; background-color: #3c1a64; padding: 7px">INSERT EMPLOYEE
</button>
<footer>
    <%@include file="footer.jsp" %>
</footer>

</body>
</html>
