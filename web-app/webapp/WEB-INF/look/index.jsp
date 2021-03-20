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
    <script type="text/javascript" src="${pageContext.request.contextPath}/resourses/js/validationTel.js" ></script >
    <script type="text/javascript" src="${pageContext.request.contextPath}/resourses/js/myScript.js" ></script >
<body >
<div class="container" >
    <div class="d-flex p-3 bd-highlight" >
        <div class="col" >

        </div >
        <div >
            <form action="search" class="col" >
                <input class="form-control" type="text" id="search" name="name" placeholder="Type to search..." >
            </form >
        </div >
        <div class="col" >

        </div >
    </div >
    <div class="row" >
        <div class="col-2" >
            <div class="row" >
                <div class="btn-group-vertical" >
                    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical" >
                    <span class="border border-5" ><a class="nav-link" id="v-pills-home-tab" data-toggle="pill"
                                                      href="profile?id=${globalId}" role="tab"
                                                      aria-controls="v-pills-home"
                                                      aria-selected="true" >Home</a ></span >
                        <span class="border border-5" ><a class="nav-link" id="v-pills-messages-tab" data-toggle="pill"
                                                          href="registerJsp" role="tab"
                                                          aria-controls="v-pills-messages" aria-selected="false" >Register new Account</a ></span >
                    </div >
                </div >
            </div >
        </div >
        <div class="col-10" >
            <div class="shadow-lg p-3 mb-5 bg-body rounded" ><h4 class="display-3" >${requestScope.message} </h4 ></div >
            <div class="shadow-lg p-3 mb-5 bg-body rounded" ><h4 class="display-3" >${requestScope.string} </h4 ></div >
        </div >
    </div >
</div >
</body >
