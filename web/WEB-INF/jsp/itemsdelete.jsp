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
    <title>Delete menu</title>

</head>

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
        <c:if test="${not empty error}">
            <div class="alert alert-warning" role="alert">
                <c:out value="${error}"></c:out>
            </div>
        </c:if>
        <div class="col-sm-10">
            <table class="table table-dark">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">name</th>
                    <th scope="col">description</th>
                    <th scope="col">price</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${items}" var="item">
                    <c:set var="count" value="${count+1}" scope="page"/>
                    <tr>
                        <form action="${pageContext.request.contextPath}/shop?command=items_delete"
                              method="post">
                            <th scope="row">${count+(page-1)*amount}</th>
                            <td>${item.name}</td>
                            <td>${item.description}</td>
                            <td>${item.price}</td>
                            <input type="hidden" name="uniquenumber" value="${item.uniqueNumber}">
                            <td style="display: none">${item.uniqueNumber}</td>
                            <td>
                                <button class="btn-sm" type="submit">delete</button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>

                </tbody>
                <thead>

                <jsp:include page="util/paginator.jsp">
                    <jsp:param name="command" value="items_delete"/>
                </jsp:include>

                <c:if test="${not empty info}">
                    <div class="alert alert-warning" role="alert">
                        <c:out value="${info}"></c:out>
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
