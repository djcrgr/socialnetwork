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
            <div class="row">
                <figure class="figure-img" >
                    <img src="data:image/png;base64, ${requestScope.image}"
                         class="figure-img img-fluid rounded" alt="..." width="170">
                </figure>
            </div>
            <div class="row" >
                <div class="btn-group-vertical" >
                    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical" >
                    <span class="border border-5" ><a class="nav-link active" id="v-pills-home-tab" data-toggle="pill"
                                                      href="profile?id=${globalId}" role="tab" aria-controls="v-pills-home"
                                                      aria-selected="true" >Home</a ></span >
                        <span class="border border-5" ><a class="nav-link" id="v-pills-profile-tab" data-toggle="pill"
                                                          href="readAcc?idAcc=${sessionScope.globalId}" role="tab" aria-controls="v-pills-profile"
                                                          aria-selected="false" >Profile Edit</a ></span>
                        <span class="border border-5" ><a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab"
                                                          aria-controls="v-pills-messages" aria-selected="false" >Messages</a ></span>
                        <span class="border border-5" ><a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab"
                                                          aria-controls="v-pills-settings" aria-selected="false" >Settings</a ></span>
                        <span class="border border-5" ><a class="nav-link" id="v-pills-logout-tab" data-toggle="pill" href="logout" role="tab"
                                                          aria-controls="v-pills-settings" aria-selected="false" >Logout</a ></span>
                        <span class="border border-5" ><a class="nav-link" id="v-pills-showGroups-tab" data-toggle="pill" href="showAllGroups" role="tab"
                                                          aria-controls="v-pills-settings" aria-selected="false" >Show groups</a ></span>
                    </div >
                </div >
            </div >
        </div>
        <div class="col-10" >
            <div class="shadow-lg p-3 mb-5 bg-body rounded">
                <h1 >Hello ${requestScope.account.name}</h1 >
                <br/>
                <h5><p ><strong >accountID: </strong >${requestScope.account.id}</p ></h5>
                <h5><p ><strong >Name: </strong >${requestScope.account.name}</p ></h5>
                <h5><p ><strong >Surname: </strong >${requestScope.account.surname}</p ></h5>
                <h5><p ><strong >Age: </strong >${requestScope.account.age}</p ></h5>
                <h5><p ><strong >Address: </strong >${requestScope.account.address}</p ></h5>
                <h5><p ><strong >phones</strong></p ></h5>
                <h5></h5><c:forEach var="phone" items="${requestScope.account.phoneNum}">
                    <table >
                        <tr >
                            <td >${phone.number}</td >
                            <td >${phone.type}</td >
                        </tr >
                    </table >
                </c:forEach>
                <br/>
                <h5><p><strong >e-mail: </strong >${requestScope.account.email}</p></h5>
            </div>
        </div >
    </div >
</div >
</body >
