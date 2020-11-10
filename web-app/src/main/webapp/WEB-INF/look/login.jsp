<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.11.2020
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%--
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

</div >
</body >
</html >
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>test</title>
    <style type="text/css">
        body {
            font: 10pt Arial, Helvetica, sans-serif; /* Шрифт на веб-странице */
            background: #e1dfb9; /* Цвет фона */
        }
        h2 {
            font-size: 1.1em; /* Размер шрифта */
            color: #800040; /* Цвет текста */
            margin-top: 0; /* Отступ сверху */
        }
        #container {
            height: 20cm;
            width: 500px; /* Ширина слоя */
            margin: 0 auto; /* Выравнивание по центру */
            background: #5f5f5f; /* Цвет фона левой колонки */
        }
        #header {
            font-size: 2.2em; /* Размер текста */
            text-align: center; /* Выравнивание по центру */
            padding: 5px; /* Отступы вокруг текста */
            background: #8fa09b; /* Цвет фона шапки */
            color: #ffe; /* Цвет текста */
        }
        #sidebar {
            margin-top: 10px;
            width: 110px; /* Ширина слоя */
            padding: 0 10px; /* Отступы вокруг текста */
            float: left; /* Обтекание по правому краю */
        }
        #content {
            height: 100%;
            margin-left: 130px; /* Отступ слева */
            padding: 10px; /* Поля вокруг текста */
            background: #c4daf3; /* Цвет фона правой колонки */
        }
        #footer {
            background: #8fa09b; /* Цвет фона подвала */
            color: #fff; /* Цвет текста */
            padding: 5px; /* Отступы вокруг текста */
            clear: left; /* Отменяем действие float */
        }
    </style>
</head>
<body>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content">
    <div id="header">s</div>
    <div id="sidebar">
        <p><a href="registerJsp">registration</a></p>
        <p><a href="profile?id=${id}"> home</a>  </p>
    </div>
    <div id="container">
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
    </div>
    <div id="footer">&copy; djcrgr@gmail.com</div>
</div>
</body>
</html>