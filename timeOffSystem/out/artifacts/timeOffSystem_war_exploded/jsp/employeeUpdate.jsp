<%@ page import="com.dotin.timeOffRequest.entity.CategoryElement" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.entity.Employee" %>
<%@ page import="com.dotin.timeOffRequest.service.CategoryElementService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>employeeUpdate</title>
    <link rel="stylesheet" type="text/css" href="../resource/saveForm.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>
<%
    CategoryElementService categoryElementServiceT = new CategoryElementService();
    List<CategoryElement> categoryElementList = categoryElementServiceT.findAll();
    EmployeeService employeeServiceT = new EmployeeService();
    List<Employee> employees = employeeServiceT.findAll();
    Employee employee = employeeServiceT.findById(Long.valueOf(request.getParameter("id")));
%>
<div class="container" style="width: 491.37px;">
    <form action="/employee-controller" method="post">
        <input type="hidden" name="id" value="<%=request.getParameter("id")%>"/>
        <div class="row">
            <div class="col-25">
                <label for="fname">First Name</label>
            </div>
            <div class="col-75">
                <input type="text" id="fname" name="firstName" value="<%=employee.getFirstName()%>">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label for="lname">Last Name</label>
            </div>
            <div class="col-75">
                <input type="text" id="lname" name="lastName" value="<%=employee.getLastName()%>">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label for="nationalCode">National Code</label>
            </div>
            <div class="col-75">
                <input type="text" id="nationalCode" name="nationalCode" value="<%=employee.getNationalCode()%>">
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
                <input type="text" id="phoneNumber" name="phoneNumber" value="<%=employee.getPhoneNumber()%>">
            </div>

        </div>
        <div class="row">
            <div class="col-25">
                <label for="emailAddress">Email address</label>
            </div>
            <div class="col-75">
                <input type="text" id="emailAddress" name="emailAddress" value="<%=employee.getEmailAddress()%>">
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
                        for (Employee manager : employees) {
                    %>
                    <option value="<%=manager.getId()%>"><%=manager.getFirstName()%>&nbsp;<%=manager.getLastName()%>
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
