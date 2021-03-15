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
    <script type="text/javascript" src="${pageContext.request.contextPath}/resourses/js/validationTel.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resourses/js/myScript.js"></script>
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
            <a class="nav-link" id="v-pills-home-tab" data-toggle="pill" href="profile?id=${globalId}" role="tab"
               aria-controls="v-pills-home" aria-selected="true">Home</a>
            <a class="nav-link" id="v-pills-register-tab" data-toggle="pill" href="registerJsp" role="tab"
               aria-controls="v-pills-settings" aria-selected="false">Register New Acc</a>
        </div>
    </div >
    <div id="container" >
        <li style="content: normal" >
            ${message}
        </li >
        <ul class="pagination" >
            <%--<c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                                         href="search?currentPage=${currentPage-1}">Previous
                </a>
                </li>
            </c:if>--%>

            <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i" >
                <c:choose >
                    <c:when test="${currentPage eq i}" >
                        <li class="page-item active" ><a class="page-link" >
                                ${i} <span class="sr-only" >(current)</span ></a >
                        </li >
                    </c:when >
                    <c:otherwise >
                        <li class="page-item" ><a class="page-link"
                                                  href="search?name=${requestScope.name}&currentPage=${i}" >${i}</a >
                        </li >
                    </c:otherwise >
                </c:choose >
            </c:forEach >
        </ul >
    </div >
    <div id="footer" >&copy; djcrgr@gmail.com</div >
</div >
</body >
