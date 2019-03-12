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
    <title>ITEMS</title>


</head>
<header>
    <h1>SHOP</h1>
</header>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm">
            <c:choose>
                <c:when test="${user.permission.name == 'CUSTOMER' }">
                    <jsp:include page="util/customer_panel.jsp"></jsp:include>
                </c:when>
                <c:when test="${user.permission.name == 'SELLER'}">
                    <jsp:include page="util/seller_panel.jsp"></jsp:include>
                </c:when>
                <c:otherwise>
                    <jsp:include page="util/customer_panel.jsp"></jsp:include>
                </c:otherwise>
            </c:choose>

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
                <c:set var="count" value="0" scope="page"/>

                <c:forEach items="${items}" var="item">
                    <c:set var="count" value="${count+1}" scope="page"/>
                    <tr>
                        <form action="${pageContext.request.contextPath}/shop?command=order"
                              method="post">
                            <th scope="row"><c:out value="${count+(page-1)*amount}"></c:out></th>
                            <td><c:out value="${item.name}"></c:out></td>
                            <td><c:out value="${item.description}"></c:out></td>
                            <td><c:out value="${item.price}"></c:out></td>
                            <input type="hidden" name="uniquenumber" value="${item.uniqueNumber}">
                            <td>
                                <c:if test="${not empty messages.quantity}">
                                    <div class="alert alert-warning" role="alert">
                                        <c:out value="${messages.quantity}"></c:out>
                                    </div>
                                </c:if>
                                <input type="number" min="0" name="quantity"><br>
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

                <jsp:include page="util/paginator.jsp">
                    <jsp:param name="command" value="items"/>
                </jsp:include>
                <c:if test="${not empty error}">
                    <div class="alert alert-warning" role="alert">
                        <c:out value="${error}"></c:out>
                    </div>
                </c:if>
                </thead>
            </table>
        </div>

    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
