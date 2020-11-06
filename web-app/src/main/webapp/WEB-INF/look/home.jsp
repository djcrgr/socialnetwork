<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.11.2020
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="account" scope="session" class="com.getjavajob.training.karpovn.socialnetwork.common.Account" />
<!DOCTYPE html>
<html>
<head>
    <title>home</title>
</head>
<body>
<style>
    ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #333333;
    }

    li {
        float: left;
    }

    li a {
        display: block;
        color: white;
        text-align: center;
        padding: 16px;
        text-decoration: none;
    }

    li a:hover {
        background-color: #111111;
    }
    table {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    tr:nth-child(even) {
        background-color: #dddddd;
    }
    tr:nth-child(odd) {
        background-color: #1ddeee;
    }
</style>
</head>
<ul >
    <li ><a href="profile" >Home</a ></li >
    <li><a href="loginJsp">Login</a> </li>
    <li ><a href="registerJsp" >Registration</a ></li >
    <li ><a href="logout" >logout</a ></li >
</ul >
<h1>Hello ${account.name}!</h1>
<table>
    <tr>
        <th>Name:</th>
        <td>${account.name}</td>
    </tr>
    <tr>
        <th>Surname:</th>
        <td>${account.surname}</td>
    </tr>
    <tr>
        <th>age:</th>
        <td>${account.age}</td>
    </tr>
</table>
<form name="logout" action="logout" method="post">
    <button type="submit" >logout</button >
</form>
</body>
</html>
