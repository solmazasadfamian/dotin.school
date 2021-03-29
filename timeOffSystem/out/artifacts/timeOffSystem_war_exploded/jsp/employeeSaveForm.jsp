<%@ page import="com.dotin.timeOffRequest.entity.CategoryElement" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.entity.Employee" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.service.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>employeeSaveForm</title>
    <script src="../resource/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../resource/saveForm.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>
<%
    CategoryElementService categoryElementService = new CategoryElementService();
    List<CategoryElement> categoryElementList = categoryElementService.findAllByCode("category.id" , 1l);
    EmployeeService employeeService = new EmployeeService();
    List<Employee> employees = employeeService.findAll();
%>
<div class="container" style="width: 491.37px;">
    <div class="row">
        <div class="col-md-12">
            <div class="alert alert-block alert-danger fade in" id="alert-info">
            </div>
        </div>
    </div>
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
            <input id="save" type="submit" value="SAVE">
        </div>
</div>
</body>
</html>
<script>
    $('#alert-info').hide();

    $('#save').click(function () {
        $.ajax({
            url: '/employee-controller',
            type: 'POST',
            data: {
                firstName: $("#fname").val(),
                lastName: $("#lname").val(),
                nationalCode: $("#nationalCode").val(),
                role: $("#role").val(),
                phoneNumber: $("#phoneNumber").val(),
                emailAddress: $("#emailAddress").val(),
                manager: $("#manager").val()
            },
            success: function (data) {
                window.location = "employeeTable.jsp";
            },
            error: function (error) {
                $('#alert-info p').remove();
                $('#alert-info').append(error.responseText);
                $('#alert-info').show();
            }
        })
    });
</script>
<style>
    .fade.in {
        opacity: 1;
        display: block;
    }
    .alert-danger {
        color: #a94442;
        background-color: #f2dede;
        border-color: #ebccd1;
    }
    .alert {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-top-color: transparent;
        border-right-color: transparent;
        border-bottom-color: transparent;
        border-left-color: transparent;
        border-radius: 4px;
    }


</style>
