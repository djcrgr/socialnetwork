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
            <a class="nav-link" id="v-pills-home-tab" data-toggle="pill" href="profile?id=${globalId}" role="tab"
               aria-controls="v-pills-home" aria-selected="true" >Home</a >
            <a class="nav-link active" id="v-pills-profile-tab" data-toggle="pill"
               href="readAcc?idAcc=${sessionScope.globalId}" role="tab" aria-controls="v-pills-profile"
               aria-selected="false" >Profile Edit</a >
            <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab"
               aria-controls="v-pills-messages" aria-selected="false" >Messages</a >
            <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab"
               aria-controls="v-pills-settings" aria-selected="false" >Settings</a >
            <a class="nav-link" id="v-pills-register-tab" data-toggle="pill" href="registerJsp" role="tab"
               aria-controls="v-pills-settings" aria-selected="false" >Register New Acc</a >
        </div >
        <form name="logout" action="logout" method="post" >
            <button type="submit" >logout</button >
        </form >
    </div >
    <div id="container" >
        <h1 >Editing info ${requestScope.currentAcc.name}</h1 >
        <h1>${requestScope.idAcc}</h1>
        <br />
        <form action="updateAcc" method="post">
            <label>
                editing id <input type="text" name="idAccount" value="${requestScope.idAcc}" readonly/>
            </label>
            <br><br>
            <label >
                name:
                <input type="text" name="name" size="30" value="${requestScope.currentAcc.name}"/>
        </label >
            <br ><br >
            <label for="surname" >surname:
                <input type="text" id="surname" name="surname" size="30" value="${requestScope.currentAcc.surname}"/>
            </label >
            <br ><br >
            <label for="age" >age:
                <input type="number" id="age" name="age" size="30" value="${requestScope.currentAcc.age}" />
            </label >
            <br ><br >
            <label for="phoneNumHome" >phoneNum (Home):
                <input type="number" id="phoneNumHome" name="phoneNumHome" size="30"
                       value="${requestScope.homePhone}" />
            </label >
            <br ><br >
            <label for="phoneNumWork" >phoneNum (Work):
                <input type="number" id="phoneNumWork" name="phoneNumWork" size="30"
                       value="${requestScope.workPhone}" />
            </label >
            <br ><br >
            <label for="address" >address:
                <input type="text" id="address" name="address" size="30" value="${requestScope.currentAcc.address}" />
            </label >
            <br ><br >
            <label for="email" >email:
                <input type="email" id="email" name="email" size="30" value="${requestScope.currentAcc.email}" />
            </label >
            <br ><br >
            <label for="password" >password:
                <input type="password" id="password" name="password" size="30"
                       value="${requestScope.currentAcc.password}" />
            </label >
            <br ><br >
            <button type="submit" >Submit</button >
        </form >
        <br>
        <form action="picUpload" method="post" enctype="multipart/form-data">
            <input type="hidden" name="newAccId" value="${requestScope.currentAcc.id}" readonly>
            <input type="file" name="file" />
            <button type="submit" >Submit</button >
        </form>
    </div >
    <div id="footer" >&copy; djcrgr@gmail.com</div >
</div >
</body >
</html >