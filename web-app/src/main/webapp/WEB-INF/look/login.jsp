<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.11.2020
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
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
<head >
    <title >social-network</title >
</head >
<body >
<ul >
    <li ><a href="profile" >Home</a ></li >
    <li><a href="loginJsp">Login</a> </li>
    <li ><a href="registerJsp" >Registration</a ></li >
    <li ><a href="logout" >logout</a ></li >
</ul >
<div style="text-align: center" >
    <h1 >Login</h1 >
    <br >${message}
    <form action="login" method="post">
        <label for="email" >email:</label >
        <input type="email" id="email" name="email" size="30" />
        <br ><br >
        <label for="password" >password:</label >
        <input type="password" id="password" name="password" size="30" />
        <br ><br >
        <button type="submit" >Login</button >
        <input type="checkbox" id="remember" name="remember" >
        <label for="remember">remember</label>
    </form >
</div >
</body >
</html >
