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
                    <h5 class="card-title text-center">Sign In</h5>

                    <c:if test="${not empty error}">
                        <div class="alert alert-warning" role="alert">
                            <c:out value="${error}"></c:out>
                        </div>
                    </c:if>
                    <c:if test="${not empty messages.email}">
                        <div class="alert alert-warning" role="alert">
                            <c:out value="${messages.email}"></c:out>
                        </div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/shop?command=login" method="post"
                          class="form-signin">
                        <div class="form-label-group">
                            <input type="email" name="email" value="${email}" id="inputEmail" class="form-control"
                                   placeholder="Email address" required autofocus>
                            <label for="inputEmail">Email address</label>
                        </div>
                        <c:if test="${not empty messages.password}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.password}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="password" name="password" value="${password}" id="inputPassword"
                                   class="form-control" placeholder="Password" required>
                            <label for="inputPassword">Password</label>
                        </div>
                        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Sign in</button>
                        <hr class="my-4">

                    </form>

                    <div id="register-link" class="text-right">
                        <form action="${pageContext.request.contextPath}/shop?command=registration_page" method="post">
                            <button type="submit" class="btn btn-link">registration</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
