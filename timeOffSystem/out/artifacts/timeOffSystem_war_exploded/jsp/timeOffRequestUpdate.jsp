<%@ page import="com.dotin.timeOffRequest.service.*" %>
<%@ page import="com.dotin.timeOffRequest.entity.TimeOffRequest" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>employeeSaveForm</title>
    <script src="../resource/jquery.min.js"></script>
    <script src="../resource/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../resource/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../resource/saveForm.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>

<%
    TimeOffRequestService timeOffRequestService = new TimeOffRequestService();
    TimeOffRequest timeOffRequest = timeOffRequestService.findById(Long.valueOf(request.getParameter("id")));
%>
<div class="container" style="width: 491.37px;">
    <div class="row">
        <div class="col-md-12">
            <div class="alert alert-block alert-danger fade in" id="alert-info">
            </div>
        </div>
    </div>
    <input type="hidden" id="employee" name="employee" value="<%=request.getParameter("employee")%>"/>
    <input type="hidden" id="id"  name="id" value="<%=request.getParameter("id")%>"/>
    <div class="row">
        <div class="col-25">
            <label for="startTime">start time</label>
        </div>
        <div class="col-75">
            <input type="text" id="startTime" name="startTime" value="<%=timeOffRequest.getStartTime()%>">
        </div>
    </div>
    <div class="row">
        <div class="col-25">
            <label for="endTime">end time</label>
        </div>
        <div class="col-75">
            <input type="text" id="endTime" name="endTime" value="<%=timeOffRequest.getEndTime()%>">
        </div>
    </div>
    <div class="row">
        <div class="col-25">
            <label for="dayAmount">day amount</label>
        </div>
        <div class="col-75">
            <input type="number" id="dayAmount" name="dayAmount" value="<%=timeOffRequest.getTimeOffDayAmount()%>">
        </div>
    </div>
    <div class="row">
        <input type="submit" value="SAVE" id="save">
    </div>
</div>
</body>
</html>
<script>
    $('#alert-info').hide();

    $('#save').click(function () {
        $.ajax({
            url: '/time-off-request-controller',
            type: 'POST',
            data: {
                id: $("#id").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                dayAmount: $("#dayAmount").val(),
                employee: $("#employee").val()
            },
            success: function (data) {
                window.location = "timeOffRequest.jsp?employee=<%=request.getParameter("employee")%>";
            },
            error: function (error) {
                $('#alert-info p').remove();
                $('#alert-info').append(error.responseText);
                $('#alert-info').show();
            }
        })
    });
</script>
