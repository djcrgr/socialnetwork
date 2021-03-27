<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >

<head >
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title >social-network</title >
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous" >
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
            crossorigin="anonymous" ></script >
    <script type="text/javascript" src="/web/js/validationTel.js"></script>
    <script type="text/javascript" src="/web/js/myScript.js"></script>
</head >
<body >
<div class="container" >
    <div class="d-flex p-3 bd-highlight">
        <div class="col">

        </div>
        <div>
            <form action="search" class="col" >
                <input class="form-control" type="text" id="search" name="name" placeholder="Type to search...">
            </form >
        </div >
        <div class="col">

        </div>
    </div >
    <div class="row" >
        <div class="col-2">
            <div class="row" >
                <div class="btn-group-vertical" >
                    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical" >
                    <span class="border border-5" ><a class="nav-link" id="v-pills-home-tab" data-toggle="pill"
                                                      href="profile?id=${globalId}" role="tab" aria-controls="v-pills-home"
                                                      aria-selected="true" >Home</a ></span >
                        <span class="border border-5" ><a class="nav-link active" id="v-pills-profile-tab" data-toggle="pill"
                                                          href="readAcc?idAcc=${sessionScope.globalId}" role="tab" aria-controls="v-pills-profile"
                                                          aria-selected="false" >Profile Edit</a ></span>
                        <span class="border border-5" ><a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab"
                                                          aria-controls="v-pills-messages" aria-selected="false" >Messages</a ></span>
                        <span class="border border-5" ><a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab"
                                                          aria-controls="v-pills-settings" aria-selected="false" >Settings</a ></span>
                        <span class="border border-5" ><a class="nav-link" id="v-pills-logout-tab" data-toggle="pill" href="logout" role="tab"
                                                          aria-controls="v-pills-settings" aria-selected="false" >Logout</a ></span>
                    </div >
                </div >
            </div >
        </div>
        <div class="col-10" >
            <div class="shadow-lg p-3 mb-5 bg-body rounded">
                <table class="table table-striped table-bordered table-sm" >
                    <tbody>users</tbody>
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
                    <tbody>groups</tbody>
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
            </div>
        </div >
    </div >
</div >
</body >
