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
                        <span class="border border-5" ><a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="registerJsp" role="tab"
                                                          aria-controls="v-pills-messages" aria-selected="false" >Register new Account</a ></span>
                    </div >
                </div >
            </div >
        </div>
        <div class="col-10" >
            <div class="shadow-lg p-3 mb-5 bg-body rounded">
                <form class="col-sm-10" action="login" method="post">
                    <label for="email" >email:</label >
                    <input class="form-control" type="email" id="email" name="email" size="30" />
                    <br ><br >
                    <label for="password" >password:</label >
                    <input class="form-control" type="password" id="password" name="password" size="30" />
                    <br ><br >
                    <button class="btn btn-primary" type="submit" >Login</button >
                    <input class="form-check-input" type="checkbox" id="remember"/>
                    <label for="remember"> remember</label>
                </form >
            </div>
        </div >
    </div >
</div >
</body >
