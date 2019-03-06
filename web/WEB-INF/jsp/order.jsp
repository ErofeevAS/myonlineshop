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
</head>
<header>
    <h1>SHOP</h1>
</header>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm">
            <jsp:include page="util/panel.jsp"></jsp:include>
        </div>
        <div class="col-sm-10">
            <table class="table table-dark">
                <thead>
                <tr>
                    <th scope="col">firstName</th>
                    <th scope="col">lastName</th>
                    <th scope="col">itemName</th>
                    <th scope="col">price</th>
                    <th scope="col">timestamp</th>
                    <th scope="col">quantity</th>
                    <th scope="col">totalprice</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">${firstname}</th>
                    <td>${lastname}</td>
                    <td>${itemname}</td>
                    <td>${price}</td>
                    <td>${timestamp}</td>
                    <td>${quantity}</td>
                    <td>${price*quantity}</td>
                </tr>
                </tbody>
                <thead>
                </thead>
            </table>
        </div>

    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
