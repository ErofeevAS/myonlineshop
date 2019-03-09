<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/login.css">
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
                    <h5 class="card-title text-center">add item menu</h5>
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
                    <form action="${pageContext.request.contextPath}/shop?command=item_add" method="post"
                          class="form-signin">
                        <c:if test="${not empty messages.name}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.name}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="text" id="inputName" class="form-control" name="name" placeholder="item name"
                                   required autofocus>
                            <label for="inputName">item name</label>
                        </div>
                        <c:if test="${not empty messages.description}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.description}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="text" id="inputDescription" class="form-control" name="description"
                                   placeholder="Description" required>
                            <label for="inputDescription">Description</label>
                        </div>
                        <c:if test="${not empty messages.price}">
                            <div class="alert alert-warning" role="alert">
                                <c:out value="${messages.price}"></c:out>
                            </div>
                        </c:if>
                        <div class="form-label-group">
                            <input type="text" id="inputPrice" class="form-control" name="price" placeholder="Price"
                                   required>
                            <label for="inputPrice">price</label>
                        </div>

                        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">save</button>
                        <hr class="my-4">

                        <div id="register-link" class="text-right">
                            <a href="${pageContext.request.contextPath}/shop?command=items_delete" class="text-info">back</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>


