<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <%--<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">--%>
    <%--<link rel="stylesheet" href="css/login.css">--%>
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
                    <tr>
                        <form action="${pageContext.request.contextPath}/shop?command=orders&id=${order.id}"
                              method="post">
                            <th scope="row">#</th>
                            <td>${order.firstName}</td>
                            <td>${order.lastName}</td>
                            <td>${order.itemName}</td>
                            <td>${order.price}</td>
                            <td>${order.createdDate}</td>
                            <td>${order.quantity}</td>
                            <td>${order.price*order.quantity}</td>

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
                <jsp:include page="paginator.jsp"></jsp:include>
                </tfoot>
            </table>
        </div>


    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
