<%@ page import="com.dotin.timeOffRequest.service.*" %>
<%@ page import="com.dotin.timeOffRequest.dto.EmailDto" %>
<%@ page import="com.dotin.timeOffRequest.mapper.EmailMapper" %>
<%@ page import="com.dotin.timeOffRequest.entity.Email" %>
<%@ page import="com.dotin.timeOffRequest.entity.Employee" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>viewStudent</title>
    <link rel="stylesheet" type="text/css" href="../resource/home.css">
</head>
<body>
<header>
    <%@include file="mailHeader.jsp"%>
</header>
<input type="hidden" id="employee" name="employee" value="<%=request.getParameter("employee")%>"/>
<div class="row" style="background-color:#d3b1c2">
    <%
        EmailService emailService = new EmailService();
        EmailMapper emailMapper = new EmailMapper();
        EmailDto emailDto = emailService.findById(Long.valueOf(request.getParameter("id")));
        Email email = emailMapper.toEntity(emailDto);
        Set<Employee> receiverList = email.getReceiver();

    %>
    <p>موضوع : <%=email.getSubject()%>
    </p>
    <p>گیرندگان :
    <%
        for (Employee receiver : receiverList) {
    %>
    <%=receiver.getFirstName()%> <%=receiver.getLastName()%> ،
        <%
            }
        %>
    </p>
    <p><%=email.getDescription()%>
    </p>
</div>
<footer>
    <%@include file="footer.jsp" %>
</footer>
</body>
</html>
