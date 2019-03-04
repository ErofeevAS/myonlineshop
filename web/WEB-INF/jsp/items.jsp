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
            <jsp:include page="panel.jsp"></jsp:include>
        </div>
        <div class="col-sm-10">
            <table class="table table-dark">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">name</th>
                    <th scope="col">description</th>
                    <th scope="col">price</th>
                    <th scope="col">quantity</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.items}" var="item">
                    <tr>
                        <form action="${pageContext.request.contextPath}/shop?command=order&user=${sessionScope.user}"
                              method="post">
                            <th scope="row">#</th>
                            <td>${item.name}</td>
                            <td>${item.description}</td>
                            <td>${item.price}</td>
                            <input type="hidden" name="uniquenumber" value="${item.uniqueNumber}">
                            <td><input type="number" name="quantity"><br>
                            </td>
                            <td style="display: none">${item.uniqueNumber}</td>
                            <td>
                                <button class="btn-sm" type="submit">buy</button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>

                </tbody>
                <thead>
                <jsp:include page="paginator.jsp"></jsp:include>
                </thead>
            </table>
        </div>

    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
