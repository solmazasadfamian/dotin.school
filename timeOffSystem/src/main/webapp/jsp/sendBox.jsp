<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dotin.timeOffRequest.service.EmployeeService" %>
<%@ page import="com.dotin.timeOffRequest.dto.EmployeeDto" %>
<%@ page import="com.dotin.timeOffRequest.dao.EmailDao" %>
<%@ page import="com.dotin.timeOffRequest.entity.Email" %>
<%@ page import="com.dotin.timeOffRequest.service.EmailService" %>
<%@ page import="com.dotin.timeOffRequest.dto.EmailDto" %>
<%@ page import="com.dotin.timeOffRequest.mapper.EmailMapper" %>
<%@ page import="com.dotin.timeOffRequest.entity.Employee" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>inbox</title>
    <link rel="stylesheet" type="text/css" href="../resource/table.css">
    <link rel="stylesheet" type="text/css" href="../resource/home.css">
    <link rel="stylesheet" type="text/css" href="../resource/header.css">

</head>
<body>
<header>
    <%@include file="mailHeader.jsp" %>
</header>
<input type="hidden" id="employee" name="employee" value="<%=request.getParameter("employee")%>"/>
<%
    EmailService emailService = new EmailService();
    List<Email> emailList = emailService.findAllBySenderId(Long.valueOf(request.getParameter("employee")));
    EmailMapper emailMapper = new EmailMapper();
%>
<div class="container">
<table>
    <thead>
    <tr>
        <th >ردیف
        <th>موضوع
        <th>
    </thead>
    <tbody>
    <%
        int rowIndex = 0;
        for (Email email : emailList) {
            Email entity = emailMapper.toEntity(emailService.findById(email.getId()));
            rowIndex++;
    %>
    <tr>
        <td><%=rowIndex%>
        <td><%=entity.getSubject()%>
        <td><a href="/jsp/mailSendBoxView.jsp?id=<%=email.getId()%>&employee=<%=request.getParameter("employee")%>" target="._top">مشاهده</a>
                <%
            }
    %>
    </tbody>
</table>
</div>
<footer>
    <%@include file="footer.jsp" %>
</footer>

</body>
</html>
