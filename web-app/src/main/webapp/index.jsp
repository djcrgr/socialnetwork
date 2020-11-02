<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="accService" class="com.getjavajob.training.karpovn.socialnetwork.service.AccountService" scope="application"/>
<html>
    <head>
    <title>Test</title>
</head>
<body>
<table border=1 cellpadding=5>
            <% request.setAttribute("list", accService.showAll());%>
    <c:forEach var="acc" items="${list}">
        <tr>
            <td>${acc.name}</td>
            <td>${acc.surname}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
