<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<%@ page contentType="text/html;charset=utf-8" %>
<head >
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title >social-network</title >
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous" >
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
            crossorigin="anonymous" ></script >
    <script type="text/javascript" src="${pageContext.request.contextPath}/resourses/js/validationTel.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resourses/js/myScript.js"></script>
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
            <div class="shadow-lg p-3 mb-5 bg-body rounded"><h4 class="display-3">Editing info </h4></div>
            <form  id = "form">
                <div class="col-xs-10">
                    <input class="form-control" type="hidden" value="${requestScope.idAcc}" id="idAccount" name="idAccount" readonly>
                </div>
                <div class="shadow-lg p-3 mb-5 bg-body rounded">
                    <div class="form-group row">
                        <label for="name" class="col-xs-2 col-form-label">Name</label>
                        <div class="col-xs-10">
                            <input class="form-control" type="text" value="${requestScope.currentAcc.name}" id="name" name="name" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="surname" class="col-xs-2 col-form-label">Surname</label>
                        <div class="col-xs-10">
                            <input class="form-control" type="text" value="${requestScope.currentAcc.surname}" id="surname" name="surname" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="age" class="col-xs-2 col-form-label">age</label>
                        <div class="col-xs-10">
                            <input class="form-control" type="text" value="${requestScope.currentAcc.age}" id="age" name="age" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="phoneNumHome" class="col-xs-2 col-form-label">phone (Home)</label>
                        <div class="col-xs-10">
                            <input class="form-control" type="telH" value="${requestScope.homePhone}" id="phoneNumHome" name="phoneNumHome" onchange="validateTelHome(this.value)" required>
                            <button class="buttonC" id="clearButtonH">Clear</button>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="phoneNumWork" class="col-xs-2 col-form-label">phone (Work)</label>
                        <div class="col-xs-10">
                            <input class="form-control" type="telW" value="${requestScope.workPhone}" id="phoneNumWork" name="phoneNumWork" onchange="validateTelWork(this.value)" required>
                            <button class="buttonC" id="clearButtonW">Clear</button>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="address" class="col-xs-2 col-form-label">address</label>
                        <div class="col-xs-10">
                            <input class="form-control" type="text" value="${requestScope.currentAcc.address}" id="address" name="address" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="email" class="col-xs-2 col-form-label">email</label>
                        <div class="col-xs-10">
                            <input class="form-control" type="text" value="${requestScope.currentAcc.email}" id="email" name="email" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="password" class="col-xs-2 col-form-label">password</label>
                        <div class="col-xs-10">
                            <input class="form-control" type="password" value="${requestScope.currentAcc.password}" id="password" name="password" required>
                        </div>
                    </div>
                    <input class="btn btn-primary xxx" type="button" value="Submit">
                </div>
            </form>
            <script >
                let form = document.querySelector('#form')
                let button = document.querySelector('.xxx')
                button.addEventListener('click', (ev) => {
                    ev.preventDefault();
                    sendToServer();
                })
                let buttonClearH = document.querySelector('#clearButtonH')
                buttonClearH.addEventListener('click', function(e){
                    e.preventDefault();
                    let inputHome = document.querySelector('#phoneNumHome')
                    inputHome.setAttribute('value', '')
                })
                let buttonClearW = document.querySelector('#clearButtonW')
                buttonClearW.addEventListener('click', function(e){
                    e.preventDefault();
                    let inputHome = document.querySelector('#phoneNumWork')
                    inputHome.setAttribute('value', '')
                })
            </script>
            <div class="shadow-lg p-3 mb-5 bg-body rounded" >
                <form action="picUpload" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="newAccId" value="${requestScope.currentAcc.id}" readonly>
                    <input type="file" name="file" />
                    <button class="btn btn-primary" type="submit" onclick="uploadPicture()">Upload new picture</button >
                </form>
            </div>
        </div >
    </div >
</div >
</body >
