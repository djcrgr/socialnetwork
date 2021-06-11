<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=utf-8" %>
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
            background: #e5e5e5; /* Цвет фона левой колонки */
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
    <div id="header">
        <form action="search">
            <label for="search">Search:</label>
            <input type="text" id="search" name="name">
        </form>
    </div>
    <div id="sidebar">
        <p><a href="registerJsp">registration</a></p>
        <p><a href="profile?id=${sessionScope.globalId}"> home</a>  </p>
    </div>
    <div id = "container" >
        <h1 >Register new account</h1 >
        <form action="register" method="post" enctype="application/x-www-form-urlencoded" >
            <label>name:
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
    <div id="footer">&copy; djcrgr@gmail.com</div>
</div>
</body>
</html>