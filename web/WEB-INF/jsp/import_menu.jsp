<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/login.css" />"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />"/>
    <title>Import menu</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm">
            <form action=${pageContext.request.contextPath}/shop/import method="post" enctype="multipart/form-data">
                <div class="btn btn-lg btn-primary btn-block text-uppercase">
                    <label class="btn btn-xs btn-primary btn-block text-uppercase">
                        file... <input name="file" type="file" style="display: none">
                        <%--<input type="submit" class="btn btn-xs btn-primary btn-block text-uppercase">import<input>--%>
                        <button class="btn btn-xs btn-primary btn-block text-uppercase" type="submit">import</button>
                    </label>
                </div>


            </form>
        </div>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
</body>
</html>
