<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="accService" class="com.getjavajob.training.karpovn.socialnetwork.service.AccountService"
             scope="application" />
<!DOCTYPE html>
<html >
<head >
    <title >login page</title >
</head >
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
<body >
<ul >
    <li ><a href="profile" >Home</a ></li >
    <li><a href="loginJsp" >Login</a> </li>
    <li ><a href="registerJsp" >Registration</a ></li >
    <li ><a href="logout" >logout</a ></li >
</ul >
<table border=1 cellpadding=5 >
    <h1>hello ${account.name}</h1>
</table >

</body >
</html >
