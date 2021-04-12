<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>homePage</title>
    <link rel="stylesheet" type="text/css" href="../resource/home.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">
</head>
<body>
<header>
    <%@include file="mailHeader.jsp" %>
</header>

<input type="hidden" id="employee" name="employee" value="<%=request.getParameter("employee")%>"/>

<div class="row">
    <div class="leftcolumn">

        <img src="/jsp/leaveForm2.jpg" height="100%" width="100%" align="center">

    </div>
</div>

<div class="footer">
    <h2>Footer</h2>
</div>
</body>
</html>
