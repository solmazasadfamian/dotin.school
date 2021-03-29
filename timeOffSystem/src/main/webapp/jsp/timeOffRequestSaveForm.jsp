<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>timeOffRequestSaveForm</title>
    <script src="../resource/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../resource/saveForm.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>
<div class="container" style="width: 491.37px;">
    <div class="row">
        <div class="col-md-12">
            <div class="alert alert-block alert-danger fade in" id="alert-info">
            </div>
        </div>
    </div>
    <input type="hidden" id="employee" name="employee" value="<%=request.getParameter("employee")%>"/>
    <div class="row">
        <div class="col-25">
            <label for="startTime">start time</label>
        </div>
        <div class="col-75">
            <input type="text" id="startTime" name="startTime" placeholder="example yyyy/mm/dd">
        </div>
    </div>
    <div class="row">
        <div class="col-25">
            <label for="endTime">end time</label>
        </div>
        <div class="col-75">
            <input type="text" id="endTime" name="endTime" placeholder="example yyyy/mm/dd">
        </div>
    </div>
    <div class="row">
        <div class="col-25">
            <label for="dayAmount">day amount</label>
        </div>
        <div class="col-75">
            <input type="number" id="dayAmount" name="dayAmount" placeholder="day amount...">
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
            url: '/time-off-request-controller',
            type: 'POST',
            data: {
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