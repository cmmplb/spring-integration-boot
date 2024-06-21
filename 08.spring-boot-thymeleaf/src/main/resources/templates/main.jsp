<%@ page import="com.cmmplb.thymeleaf.entity.Account " %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主界面</title>
</head>
<%
    Account user = (Account) request.getAttribute("user");
%>
<body>
<div align="center">
    <%-- 获取对象 --%>
    <h1>欢迎<%=user.getName()%>来到主界面</h1>
    <h1>欢迎${user.name}来到主界面</h1>
</div>
</body>
</html>