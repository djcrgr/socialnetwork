<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 05.11.2020
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<style >
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
</style >
<head >
    <title >Registration</title >
</head >
<body >
<ul >
    <li ><a href="profile?id=${sessionScope.globalId}" >Home</a ></li >
    <li ><a href="loginJsp" >Login</a ></li >
    <li ><a href="registerJsp" >Registration</a ></li >
    <li ><a href="logout" >logout</a ></li >
</ul >
<div style="text-align: center" >
    <h1 >Register new account</h1 >
    <form action="register" method="post" enctype="application/x-www-form-urlencoded" >
        <label >name:
            <input type="text" name="accname" size="30" >
        </label >
        <br ><br >
        <label >surname:
            <input type="text" name="accsurname" size="30" />
        </label >
        <br ><br >
        <label >age:
            <input type="number" name="age" size="30" />
        </label >
        <br ><br >
        <label >phoneNum (Home):
            <input type="number" name="phoneNumHome" size="30" />
        </label >
        <br ><br >
        <label >phoneNum (Work):
            <input type="number" name="phoneNumWork" size="30" />
        </label >
        <br ><br >
        <label >address:
            <input type="text" name="address" size="30" />
        </label >
        <br ><br >
        <label >email:
            <input type="email" name="email" size="30" />
        </label >
        <br ><br >
        <label>password:
            <input type="password" name="password" size="30" />
        </label >
        <br ><br >

        <button type="submit" >Submit</button >
    </form >
    <h2>${requestScope.mes}</h2>
    <form action="login" method="post" enctype="application/x-www-form-urlencoded">
        <br ><br >
        <button type="submit" >Proceed without image</button >
    </form>
    <form action="picUpload" method="post" enctype="multipart/form-data">
        <br ><br >
        <input type="hidden" name="newAccId" value="${requestScope.newAccId}" readonly>
        <input type="file" name="file" />
        <button type="submit" >Submit</button >
    </form>
</div >
</body >
</html >
