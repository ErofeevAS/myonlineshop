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
            <div class="form-label-group">
                <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Profile</button>
            </div>
            <form action=${pageContext.request.contextPath}/shop?command=items method="get">
                <div class="form-label-group">
                    <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Items</button>
                </div>
            </form>
            <form action=${pageContext.request.contextPath}/shop?command=additem method="get">
                <input type="hidden" name="command" value="additem" />
                <div class="form-label-group">
                    <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">add item</button>
                </div>
            </form>
            <div class="form-label-group">
                <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Logout</button>
            </div>

        </div>
        <div class="col-sm-10">

            <table class="table table-dark">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">name</th>
                    <th scope="col">description</th>
                    <th scope="col">price</th>
                    <th scope="col">unique_number</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.items}" var="item">
                    <tr>
                        <form action=${pageContext.request.contextPath}/shop?command=order&user=${sessionScope.user}&item=${item} method="get">
                            <th scope="row">#</th>
                            <td>${item.name}</td>
                            <td>${item.description}</td>
                            <td>@${item.price}</td>
                            <td>@${item.uniqueNumber}</td>
                            <td><button type="submit">buy</button></td>
                        </form>
                    </tr>
                </c:forEach>

                </tbody>
                <thead>
                <div class ="page-selector">
                    <div class="page-per-page">
                        <span>Result per page</span>
                        <form class="page-dropdown">
                            <select name="pageSize">
                                <option value="5" selected>5</option>
                                <option value="10">10</option>
                                <option value="20">20</option>
                            </select>
                        </form>
                    </div>
                    <div class="paging">
                        <span class="current-page">${requestScope.maxpages}</span>
                        <nav aria-label="Page navigation example">
                            <ul class="pagination">
                                <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                <li class="page-item"><a class="page-link" href="#">2</a></li>
                                <li class="page-item"><a class="page-link" href="#">Next</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
                </thead>
            </table>
        </div>

    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>