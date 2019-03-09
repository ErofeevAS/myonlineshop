<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/login.css" />"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />"/>
    <title>Login</title>
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
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">firstName</th>
                    <th scope="col">lastName</th>
                    <th scope="col">itemName</th>
                    <th scope="col">price</th>
                    <th scope="col">timestamp</th>
                    <th scope="col">quantity</th>
                    <th scope="col">totalprice</th>
                    <th scope="col">status</th>

                </tr>
                <tbody>
                <c:forEach items="${requestScope.orders}" var="order">
                    <c:set var="count" value="${count+1}" scope="page"/>
                    <tr>
                        <form action="${pageContext.request.contextPath}/shop?command=orders"
                              method="post">
                            <input type="hidden" name="id" value="${order.id}">
                            <th scope="row">${count+(page-1)*amount}</th>
                            <td><c:out value="${order.firstName}"></c:out></td>
                            <td><c:out value="${order.lastName}"></c:out></td>
                            <td><c:out value="${order.itemName}"></c:out></td>
                            <td><c:out value="${order.price}"></c:out></td>
                            <td><c:out value="${order.createdDate}"></c:out></td>
                            <td><c:out value="${order.quantity}"></c:out></td>
                            <td><c:out value="${order.price*order.quantity}"></c:out></td>

                            <td>
                                <select id="status" name="status">
                                    <option selected="selected">
                                            ${order.status}
                                    </option>
                                    <option>NEW</option>
                                    <option>REVIEWING</option>
                                    <option>IN_PROCESS</option>
                                    <option>DELIVERED</option>
                                </select>
                            </td>
                            <td>
                                <button class="btn-sm" type="submit">change</button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>

                </tbody>
                <tfoot>
                <jsp:include page="util/paginator.jsp">
                    <jsp:param name="command" value="orders"/>
                </jsp:include>

                <c:if test="${not empty info}">
                    <div class="alert alert-warning" role="alert">
                        <c:out value="${info}"></c:out>
                    </div>
                </c:if>
                </tfoot>
            </table>
        </div>


    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
