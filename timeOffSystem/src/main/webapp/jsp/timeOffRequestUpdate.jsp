<%@ page import="com.dotin.timeOffRequest.service.TimeOffRequestService" %>
<%@ page import="com.dotin.timeOffRequest.dto.TimeOffRequestDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>timeOffRequestUpdate</title>
    <script src="../resource/jquery.min.js"></script>
    <script src="../resource/persian-date.min.js"></script>
    <script src="../resource/persian-datepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../resource/saveForm.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
    <link rel="stylesheet" type="text/css" href="../resource/persian-datepicker.min.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
</head>
<body>
<header>
    <%@include file="header.jsp" %>
</header>

<%
    TimeOffRequestService timeOffRequestService = new TimeOffRequestService();
    TimeOffRequestDto timeOffRequest = timeOffRequestService.findById(Long.valueOf(request.getParameter("id")));
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
    <%
        Long dateTimeId = timeOffRequestService.findById(Long.valueOf(request.getParameter("id"))).getRequestType();
        if (dateTimeId == 5){
    %>
    <input type="hidden" id="dateTime" name="dateTime" value="5"/>
    <div class="row">
        <div class="col-25">
            <span style="color: red">*</span>
            <label for="startDate">تاریخ شروع</label>
        </div>
        <div class="col-75">
            <input type="text" id="startDate" name="startDate" class="from-date"  value="<%=timeOffRequest.getStartDate()%>">
        </div>
    </div>
    <div class="row">
        <div class="col-25">
            <label for="endDate">تاریخ پایان</label>
        </div>
        <div class="col-75">
            <input type="text" id="endDate" name="endDate" class="to-date" value="<%=timeOffRequest.getEndDate()%>">
        </div>
    </div>
    <div class="row">
        <div class="col-25">
            <span style="color: red">*</span>
            <label for="dayAmount">تعداد روز</label>
        </div>
        <div class="col-75">
            <input type="number" id="dayAmount" name="dayAmount" value="<%=timeOffRequest.getTimeOffDayAmount()%>">
        </div>
    </div>
    <%
        }else if (dateTimeId == 6){
    %>
    <input type="hidden" id="dateTime" name="dateTime" value="6"/>
    <input type="hidden" id="id"  name="id" value="<%=request.getParameter("id")%>"/>
    <div class="row">
        <div class="col-25">
            <span style="color: red">*</span>
            <label for="date">تاریخ</label>
        </div>
        <div class="col-75">
            <input type="text" id="date" name="date" class="from-date" value="<%=timeOffRequest.getStartDate()%>">
        </div>
    </div>

    <div class="row">
        <div class="col-25">
            <span style="color: red">*</span>
            <label for="startTime">ساعت شروع</label>
        </div>
        <div class="col-75">
            <input type="text" id="startTime" name="startTime" value="<%=timeOffRequest.getStartTime()%>" class="startTime">
        </div>
    </div>

    <div class="row">
        <div class="col-25">
            <span style="color: red">*</span>
            <label for="endTime">ساعت پایان</label>
        </div>
        <div class="col-75">
            <input type="text" id="endTime" name="endTime" value="<%=timeOffRequest.getEndTime()%>" class="endTime">
        </div>
    </div>
    <%
        }
    %>
    <div class="row" style="float: left">
        <input id="save" type="submit" value="ذخیره">
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function() {
        var to, from;
        to = $(".to-date").persianDatepicker({
            format: 'YYYY/MM/DD',
            initialValue: false,
            autoClose: true,
            onSelect: function (unix) {
                to.touched = true;
                if (from && from.options && from.options.maxDate != unix) {
                    var cachedValue = from.getState().selected.unixDate;
                    from.options = {maxDate: unix};
                    if (from.touched) {
                        from.setDate(cachedValue);
                    }
                    console.log(to)
                }
            }
        });
        from = $(".from-date").persianDatepicker({
            format: 'YYYY/MM/DD',
            initialValue: false,
            autoClose: true,
            onSelect: function (unix) {
                from.touched = true;
                if (to && to.options && to.options.minDate != unix) {
                    var cachedValue = to.getState().selected.unixDate;
                    to.options = {minDate: unix};
                    if (to.touched) {
                        to.setDate(cachedValue);
                    }
                }
            }
        });



        $('.startTime').timepicker({
            timeFormat: 'HH:mm',
            interval: 30,
            minTime: '7',
            maxTime: '18:00',
            startTime: '7:00',
            dynamic: false,
            dropdown: true,
            scrollbar: true
        });

        $('.endTime').timepicker({
            timeFormat: 'HH:mm',
            interval: 30,
            minTime: '7',
            maxTime: '18:00',
            startTime: '7:00',
            dynamic: false,
            dropdown: true,
            scrollbar: true
        });


    });
</script>
<script>
    $('#alert-info').hide();

    function validateDate(){
        var result = true;
        if($("#startDate").val() == null || $("#startDate").val()=="") {
            alert("تاریخ شروع باید پر شود");
            result = false;
        }
        if($("#endDate").val() == null || $("#endDate").val()=="") {
            alert("تاریخ پایان باید پر شود");
            result = false;
        }
        if($("#dayAmount").val() == null || $("#dayAmount").val()=="") {
            alert("تعداد روزهای مرخصی باید پر شود");
            result = false;
        }
        return result;
    }
    function validateTime(){
        var result = true;
        if($("#startDate").val() == null || $("#startDate").val()=="") {
            alert("تاریخ شروع باید پر شود");
            result = false;
        }
        if($("#startTime").val() == null || $("#startTime").val()=="") {
            alert("ساعت شروع باید پر شود");
            result = false;
        }
        if($("#endTime").val() == null || $("#endTime").val()=="") {
            alert("ساعت پایان باید پر شود");
            result = false;
        }
        return result;
    }

    $('#save').click(function () {
        if(dateTime == "date") {
            if (!validateDate())
                return;
        }else if(dateTime == "time"){
            if (!validateTime())
                return;
        }
        $.ajax({
            url: '/time-off-request-controller',
            type: 'POST',
            data: {
                startDate: $("#startDate").val(),
                id: $("#id").val(),
                endDate: $("#endDate").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                date: $("#date").val(),
                dayAmount: $("#dayAmount").val(),
                dateTime: $("#dateTime").val(),
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