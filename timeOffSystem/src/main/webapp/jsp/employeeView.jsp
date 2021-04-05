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
    <title>viewStudent</title>
    <link rel="stylesheet" type="text/css" href="../resource/home.css">
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>
<div class="row" style="background-color:#d3b1c2">
    <%
        String employeeId = request.getParameter("id");
        EmployeeService employeeService = new EmployeeService();
        EmployeeDto employeeDto = employeeService.findById(Long.valueOf(employeeId));
        CategoryElementService categoryElementService = new CategoryElementService();
        CategoryElementDto role = categoryElementService.findById(employeeDto.getRoleId());
    %>
    <p>id: <%=employeeDto.getId()%>
    </p>
    <p>fist name: <%=employeeDto.getFirstName()%>
    </p>
    <p>last name: <%=employeeDto.getLastName()%>
    </p>
    <p>national code: <%=employeeDto.getNationalCode()%>
    </p>
    <p>role: <%=role.getName()%>
    </p>
    <p>phone number: <%=employeeDto.getPhoneNumber()%>
    </p>
    <p>email address: <%=employeeDto.getAddress()%>
    </p>
    <p>time off balance: <%=employeeDto.getTimeOffBalance()%>
    </p>
    <%
        if (employeeDto.getManagerId() != null) {
            EmployeeMapper employeeMapper = new EmployeeMapper();
            Employee employee = employeeMapper.toEntity(employeeDto);

    %>
    <p>manager: <%=employee.getManager().getFirstName()%> <%=employee.getManager().getLastName()%>
    </p>
    <%
    } else{
    %>
    <p>manager: null</p>
    <%
        }
    %>
</div>
<footer>
    <%@include file="footer.jsp" %>
</footer>
</body>
</html>
