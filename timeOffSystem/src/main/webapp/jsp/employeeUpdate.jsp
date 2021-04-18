<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.service.CategoryElementService" %>
<%@ page import="com.dotin.timeOffRequest.dto.CategoryElementDto" %>
<%@ page import="com.dotin.timeOffRequest.dto.EmployeeDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>employeeUpdate</title>
    <script src="../resource/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../resource/saveForm.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
    <meta charset="UTF-8">
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>
<%
    CategoryElementService categoryElementServiceT = new CategoryElementService();
    List<CategoryElementDto> categoryElementList = categoryElementServiceT.findAll();
    EmployeeService employeeServiceT = new EmployeeService();
    List<EmployeeDto> employees = employeeServiceT.findAll();
    EmployeeDto employee = employeeServiceT.findById(Long.valueOf(request.getParameter("id")));
%>
<div class="container" style="width: 491.37px;">
    <%
        if (request.getParameter("managerSubset") != null){
    %>
    <input id="managerSubset" type="hidden" name="managerSubset" value="<%=request.getParameter("managerSubset")%>"/>
    <%
        }
    %>

    <input id="id" type="hidden" name="id" value="<%=request.getParameter("id")%>"/>
        <div class="row">
            <div class="col-25">
                <label for="fname">نام</label>
            </div>
            <div class="col-75">
                <input type="text" id="fname" name="firstName" value="<%=employee.getFirstName()%>">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label for="lname">نام خانوادگی</label>
            </div>
            <div class="col-75">
                <input type="text" id="lname" name="lastName" value="<%=employee.getLastName()%>">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label for="nationalCode">کد ملی</label>
            </div>
            <div class="col-75">
                <input type="text" id="nationalCode" name="nationalCode" value="<%=employee.getNationalCode()%>">
            </div>

        </div>

        <div class="row">
            <div class="col-25">
                <label for="role">نقش</label>
            </div>
            <div class="col-75">
                <select id="role" name="role">
                    <%
                        for (CategoryElementDto categoryElement : categoryElementList) {
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
                <label for="phoneNumber">شماره همراه</label>
            </div>
            <div class="col-75">
                <input type="text" id="phoneNumber" name="phoneNumber" value="<%=employee.getPhoneNumber()%>">
            </div>

        </div>
        <div class="row">
            <div class="col-25">
                <label for="emailAddress">پست الکترونیک</label>
            </div>
            <div class="col-75">
                <input type="text" id="emailAddress" name="emailAddress" value="<%=employee.getEmailAddress()%>">
            </div>

        </div>

        <div class="row">
            <div class="col-25">
                <label for="manager">مدیر</label>
            </div>
            <div class="col-75">
                <select id="manager" name="manager">
                    <option></option>
                    <%
                        for (EmployeeDto manager : employees) {
                    %>
                    <option value="<%=manager.getId()%>"><%=manager.getFirstName()%>&nbsp;<%=manager.getLastName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>


        <div class="row" style="float: left">
            <input type="submit" value="ذخیره" id="save">
        </div>
<%--
    </form>
--%>
</div>
</body>
</html>

<script>
    $('#alert-info').hide();

    function validate(){
        var result = true;
        if($("#fname").val() == null || $("#fname").val()=="") {
            alert("first name must be present, please enter first name");
            result = false;
        }
        if($("#lname").val() == null || $("#lname").val()=="") {
            alert("last name must be present, please enter last name");
            result = false;
        }
        if($("#nationalCode").val() == null || $("#nationalCode").val()=="") {
            alert("national code must be present, please enter national code");
            result = false;
        }
        if($("#role").val() == null || $("#role").val()=="") {
            alert("role must be present, please enter role");
            result = false;
        }
        return result;
    }

    $('#save').click(function () {
        if(!validate())
            return;
        $.ajax({
            url: '/employee-controller',
            type: 'POST',
            data: {
                id: $("#id").val(),
                firstName: $("#fname").val(),
                lastName: $("#lname").val(),
                nationalCode: $("#nationalCode").val(),
                role: $("#role").val(),
                phoneNumber: $("#phoneNumber").val(),
                emailAddress: $("#emailAddress").val(),
                manager: $("#manager").val()
            },
            success: function (data) {
                console.log(data)
                console.log($("#managerSubset").val())
                if ($("#managerSubset").val()=="true"){
                    window.location = "managerSubset.jsp?id=<%=request.getParameter("managerId")%>";
                }else {
                    window.location = "employeeTable.jsp";
                }
            },
            error: function (error) {
                $('#alert-info p').remove();
                $('#alert-info').append(error.responseText);
                $('#alert-info').show();
            }
        })
    });
</script>

