<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%--<meta charset="utf-8">--%>
    <%--<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">--%>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/login.css" />"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />"/>
    <title>Login</title>
</head>


<body>
<div class="container">
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card card-signin my-5">
                <div class="card-body">
                    <h5 class="card-title text-center">change password menu</h5>
                    <c:if test="${not empty error}">
                        <div class="alert alert-warning" role="alert">
                            <c:out value="${error}"></c:out>
                        </div>
                    </c:if>
                    <c:if test="${not empty info}">
                        <div class="alert alert-info" role="alert">
                            <c:out value="${info}"></c:out>
                        </div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/shop?command=change_password" method="post"
                          class="form-signin">
                        <c:if test="${not empty messages.oldpassword}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.oldpassword}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="password" id="inpuOldPassword" class="form-control" name="oldpassword"
                                   placeholder="old password"
                                   required autofocus>
                            <label for="inpuOldPassword">old password</label>
                        </div>
                        <c:if test="${not empty messages.password}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.password}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="password" id="inputNewPassword" class="form-control" name="password"
                                   placeholder="new password" required>
                            <label for="inputNewPassword">New password</label>
                        </div>
                        <c:if test="${not empty messages.repassword}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.repassword}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="password" id="inputReNewPassword" class="form-control" name="repassword"
                                   placeholder="repassword"
                                   required>
                            <label for="inputReNewPassword">Repeated new password</label>
                        </div>

                        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">change</button>
                        <hr class="my-4">

                        <c:if test="${user.permission.name == 'CUSTOMER' }">
                            <a href="${pageContext.request.contextPath}/shop?command=items" class="text-info">back</a>
                        </c:if>
                        <c:if test="${user.permission.name == 'SELLER' }">
                            <a href="${pageContext.request.contextPath}/shop?command=items_delete" class="text-info">back</a>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>


