<%@ page import="com.dotin.timeOffRequest.entity.CategoryElement" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.entity.Employee" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.service.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>employeeSaveForm</title>
    <link rel="stylesheet" type="text/css" href="/jsp/saveForm.css">
    <link rel="stylesheet" type="text/css" href="/jsp/header2.css">
</head>
<body>
<header>
    <%@include file="header2.jsp" %>
</header>
<%
    CategoryElementService categoryElementService = new CategoryElementService();
    List<CategoryElement> categoryElementList = categoryElementService.findAll();
    EmployeeService employeeService = new EmployeeService();
    List<Employee> employees = employeeService.findAll();
%>
<div class="container" style="width: 491.37px;">
    <form action="/EmployeeController" method="post">
        <div class="row">
            <div class="col-25">
                <label for="fname">First Name</label>
            </div>
            <div class="col-75">
                <input type="text" id="fname" name="firstName" placeholder="Your name..">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label for="lname">Last Name</label>
            </div>
            <div class="col-75">
                <input type="text" id="lname" name="lastName" placeholder="Your last name..">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label for="nationalCode">National Code</label>
            </div>
            <div class="col-75">
                <input type="text" id="nationalCode" name="nationalCode" placeholder="National code..">
            </div>

        </div>

        <div class="row">
            <div class="col-25">
                <label for="role">Role</label>
            </div>
            <div class="col-75">
                <select id="role" name="role">
                    <%
                        for (CategoryElement categoryElement : categoryElementList) {
                    %>
                    <option value="<%=categoryElement.getId()%>"><%=categoryElement.getName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>

        <div class="row">
            <div class="col-25">
                <label for="phoneNumber">Phone number</label>
            </div>
            <div class="col-75">
                <input type="text" id="phoneNumber" name="phoneNumber" placeholder="Phone number..">
            </div>

        </div>
        <div class="row">
            <div class="col-25">
                <label for="emailAddress">Email address</label>
            </div>
            <div class="col-75">
                <input type="text" id="emailAddress" name="emailAddress" placeholder="Email Address..">
            </div>

        </div>

        <div class="row">
            <div class="col-25">
                <label for="manager">Manager</label>
            </div>
            <div class="col-75">
                <select id="manager" name="manager">
                    <option></option>
                    <%
                        for (Employee employee : employees) {
                    %>
                    <option value="<%=employee.getId()%>"><%=employee.getFirstName()%>&nbsp;<%=employee.getLastName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>
        <div class="row">
            <input type="submit" value="SAVE">
        </div>
    </form>
</div>
</body>
</html>
