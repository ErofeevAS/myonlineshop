<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/login.css" />"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />"/>
    <title>Profile</title>
</head>


<body>
<div class="container">
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card card-signin my-5">
                <div class="card-body">
                    <h5 class="card-title text-center">profile menu</h5>
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
                    <form action="${pageContext.request.contextPath}/shop?command=profile_menu_change" method="post"
                          class="form-signin">
                        <p class="text-center">email: ${user.email}</p>
                        <c:if test="${not empty messages.firstName}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.firstName}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="text" id="inputFirstName" class="form-control" name="firstname"
                                   value="${user.firstName}" required>
                            <label for="inputFirstName">First Name</label>
                        </div>
                        <c:if test="${not empty messages.lastName}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.lastName}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="text" id="inputLastName" class="form-control" name="lastname"
                                   value="${user.lastName}"
                                   required>
                            <label for="inputLastName">Last Name</label>
                        </div>
                        <c:if test="${not empty messages.address}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.address}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="text" id="inputAddress" class="form-control" name="address"
                                   value="${profile.address}"
                                   required>
                            <label for="inputAddress">Address</label>
                        </div>
                        <c:if test="${not empty messages.telephone}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.telephone}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="text" id="inputTelephone" class="form-control" name="telephone"
                                   value="${profile.telephone}"
                                   required>
                            <label for="inputTelephone">Telephone</label>
                        </div>

                        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">change</button>
                        <hr class="my-4">

                        <div id="register-link" class="text-right">
                            <a href="${pageContext.request.contextPath}/shop?command=change_password_page" class="text-info">change
                                password</a>
                        </div>
                        <div id="register-link" class="text-right">
                            <c:if test="${user.permission.name == 'CUSTOMER' }">
                                <a href="${pageContext.request.contextPath}/shop?command=items" class="text-info">back</a>
                            </c:if>
                            <c:if test="${user.permission.name == 'SELLER' }">
                                <a href="${pageContext.request.contextPath}/shop?command=items_delete" class="text-info">back</a>
                            </c:if>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>


