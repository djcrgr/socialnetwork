<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </style >
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head >
<body >
<div id="content" >
    <div id="header" >
        <form action="search">
            <label for="search">Search:</label>
            <input type="text" id="search" name="name">
        </form>
    </div >
    <div id="sidebar" >
        <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
            <a class="nav-link" id="v-pills-home-tab" data-toggle="pill" href="profile?id=${sessionScope.globalId}" role="tab"
               aria-controls="v-pills-home" aria-selected="true">Home</a>
            <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab" aria-controls="v-pills-messages" aria-selected="false">Messages</a>
            <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab" aria-controls="v-pills-settings" aria-selected="false">Settings</a>
            <a class="nav-link" id="v-pills-register-tab" data-toggle="pill" href="registerJsp" role="tab"
               aria-controls="v-pills-settings" aria-selected="false">Register New Acc</a>
        </div>
        <p ><a href="registerJsp" >registration</a ></p >
        <p ><a href="profile?id=${sessionScope.globalId}" > home</a ></p >
    </div >
    <div id="container" >
        <table class="table table-striped table-bordered table-sm" >
            <c:forEach items="${requestScope.resultList}" var="account" >
                <tr >
                    <td ><a href="profile?id=${account.id}" >${account.name}</a ></td >
                    <td >${account.surname}</td >
                </tr >
            </c:forEach >
        </table >
        <ul class="pagination" >
            <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i" >
                <c:choose >
                    <c:when test="${currentPage eq i}" >
                        <li class="page-item active" ><a class="page-link" >
                                ${i} <span class="sr-only" >(current)</span ></a >
                        </li >
                    </c:when >
                    <c:otherwise >
                        <li class="page-item" ><a class="page-link"
                                                  href="search?name=${requestScope.name}&page=${i}" >${i}</a >
                        </li >
                    </c:otherwise >
                </c:choose >
            </c:forEach >
        </ul >
        <br>
        <table class="table table-striped table-bordered table-sm" >
            <c:forEach items="${requestScope.resultListGroups}" var="group" >
                <tr >
                    <td ><a href="groupProfile?groupId=${group.id}" >${group.name}</a ></td >
                </tr >
            </c:forEach >
        </table >
        <ul class="pagination" >
            <c:forEach begin="1" end="${requestScope.numberOfPagesGr}" var="i" >
                <c:choose >
                    <c:when test="${requestScope.currentPageGr eq i}" >
                        <li class="page-item active" ><a class="page-link" >
                                ${i} <span class="sr-only" >(current)</span ></a >
                        </li >
                    </c:when >
                    <c:otherwise >
                        <li class="page-item" ><a class="page-link"
                                                  href="search?name=${requestScope.name}&page=${i}" >${i}</a >
                        </li >
                    </c:otherwise >
                </c:choose >
            </c:forEach >
        </ul >
    </div >
    <div id="footer" >&copy; djcrgr@gmail.com</div >
</div >
</body >
</html >
