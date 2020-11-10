<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head >
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title >test</title >
    <style type="text/css" >
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
            background: #d0d0d0; /* Цвет фона левой колонки */
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
    </style >
</head >
<body >
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="account" class="com.getjavajob.training.karpovn.socialnetwork.common.Account" />
<div id="content" >
    <div id="header" >
        <form action="search">
            <label for="search">Search:</label>
            <input type="text" id="search" name="name">
        </form>
    </div >
    <div id="sidebar" >
        <p ><a href="registerJsp" >registration</a ></p >
        <p ><a href="profile?id=${sessionScope.id}" > home</a ></p >
        <form name="logout" action="logout" method="post">
            <button type="submit" >logout</button >
        </form>
    </div >
    <div id="container" >
        <h1 >Hello ${requestScope.account.name}</h1 >
        <br />
        <p><img src="data:image/png;base64, ${requestScope.image}"/></p>
        <p ><strong >Name:</strong >${requestScope.account.name}</p >
        <p ><strong >Surname:</strong >${requestScope.account.surname}</p >
        <p ><strong >Age:</strong >${requestScope.account.age}</p >
        <p ><strong >Phone:</strong >${requestScope.account.phoneNum}</p >
        <p ><strong >e-mail:</strong >${requestScope.account.email}</p >
    </div >
    <div id="footer" >&copy; djcrgr@gmail.com</div >
</div >
</body >
</html >