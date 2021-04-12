<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>header2</title>
    <link rel="stylesheet" type="text/css" href="../resource/home.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
    <style>
        .jj {
            width: 100%;
            height: 20px;
            font-size: 0;
        }
    </style>
</head>


<body>
<div class="header">
    <h1>سیستم مرخصی</h1>

</div>
<div class="navbar">
    <a href="/jsp/inbox.jsp?employee=<%=request.getParameter("employee")%>" style="float: right">صندوق ورودی</a>
    <a href="/jsp/sendBox.jsp?employee=<%=request.getParameter("employee")%>" style="float: right">ارسال شده</a>
    <a href="/jsp/emailSaveForm.jsp?employee=<%=request.getParameter("employee")%>" style="float: right">نوشتن</a>
    <a href="/jsp/homePage.jsp" style="float: left">خانه</a>
</div>
<div class="jj"></div>
</body>


</html>
