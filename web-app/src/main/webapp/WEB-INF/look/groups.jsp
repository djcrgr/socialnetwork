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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous" >

</head >
<body >
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="account" class="com.getjavajob.training.karpovn.socialnetwork.common.Account" />
<div id="content" >
    <div id="header" >
        <form action="search" >
            <label for="search" >Search:</label >
            <input type="text" id="search" name="name" >
        </form >
    </div >
    <div id="sidebar" >
        <div id="photoFrame" >
            <p ><img src="data:image/png;base64, ${requestScope.image}" width="200" height="200" /></p >
        </div >
        <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical" >
            <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="profile?id=${globalId}" role="tab"
               aria-controls="v-pills-home" aria-selected="true" >Home</a >
            <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
               href="readAcc?idAcc=${sessionScope.globalId}" role="tab" aria-controls="v-pills-profile"
               aria-selected="false" >Profile Edit</a >
            <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab"
               aria-controls="v-pills-messages" aria-selected="false" >Messages</a >
            <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab"
               aria-controls="v-pills-settings" aria-selected="false" >Settings</a >
            <a class="nav-link" id="v-pills-register-tab" data-toggle="pill" href="registerJsp" role="tab"
               aria-controls="v-pills-settings" aria-selected="false" >Register New Acc</a >
            <a class="nav-link" id="v-pills-showGroups-tab" data-toggle="pill" href="registerJsp" role="tab"
               aria-controls="v-pills-settings" aria-selected="false" >Show groups</a >
        </div >
        <p ><a href="registerJsp" >registration</a ></p >
        <form name="logout" action="logout" method="post" >
            <button type="submit" >logout</button >
        </form >
    </div >
    <div id="container" >
        <h1 >Hello ${requestScope.account.name}</h1 >
        <br />
        <p ><strong >accountID: </strong >${requestScope.account.id}</p >
        <p ><strong >Name: </strong >${requestScope.account.name}</p >
        <p ><strong >Surname: </strong >${requestScope.account.surname}</p >
        <p ><strong >Age: </strong >${requestScope.account.age}</p >
        <p ><strong >Address: </strong > ${requestScope.account.address}</p >
        <p >phones</p >
        <c:forEach var="phone" items="${requestScope.account.phoneNum}">
            <table >
                <tr >
                    <td >${phone.number}</td >
                    <td >${phone.type}</td >
                </tr >
            </table >
        </c:forEach>
        <p ><strong >e-mail:</strong >${requestScope.account.email}</p >
    </div >
    <div id="footer" >&copy; djcrgr@gmail.com</div >
</div >
</body >
</html >
