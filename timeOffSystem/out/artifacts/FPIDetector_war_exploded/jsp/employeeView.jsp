<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.entity.Employee" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.entity.CategoryElement" %>
<%@ page import="com.dotin.timeOffRequest.service.CategoryElementService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>viewStudent</title>
    <link rel="stylesheet" type="text/css" href="home.css">
</head>
<body>
<header>
    <%@include file="header2.jsp" %>
</header>
<div class="row" style="background-color:#d3b1c2">
    <%
        String employeeId = request.getParameter("id");
        EmployeeService employeeService = new EmployeeService();
        Employee employee = employeeService.findById(Long.valueOf(employeeId));
        CategoryElementService categoryElementService = new CategoryElementService();
        CategoryElement role = categoryElementService.findById(employee.getRole().getId());
    %>
    <p>id: <%=employee.getId()%>
    </p>
    <p>fist name: <%=employee.getFirstName()%>
    </p>
    <p>last name: <%=employee.getLastName()%>
    </p>
    <p>national code: <%=employee.getNationalCode()%>
    </p>
    <p>role: <%=role.getName()%>
    </p>
    <p>phone number: <%=employee.getPhoneNumber()%>
    </p>
    <p>email address: <%=employee.getAddress()%>
    </p>
    <%
        if (employee.getManager() != null) {
    %>
    <p>manager: <%=employee.getManager().getFirstName()%> <%=employee.getManager().getLastName()%>
    </p>
    <%
    } else
    %>
    <p>manager: null</p>
</div>
<footer>
    <%@include file="footer.jsp" %>
</footer>
</body>
</html>
