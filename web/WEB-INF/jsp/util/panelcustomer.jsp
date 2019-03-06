<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.03.2019
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<form action=${pageContext.request.contextPath}/shop?command=profile method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Profile</button>
    </div>
</form>
<form action=${pageContext.request.contextPath}/shop?command=items method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Items</button>
    </div>
</form>
<form action=${pageContext.request.contextPath}/shop?command=itemadd method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">add item</button>
    </div>
</form>

<form action=${pageContext.request.contextPath}/shop?command=logout method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Logout</button>
    </div>
</form>


<form action=${pageContext.request.contextPath}/shop?command=myorders method="post">
    <input type="hidden" name="command" value="myorders"/>
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">My Orders</button>
    </div>
</form>

<form action=${pageContext.request.contextPath}/shop?command=orders method="post">
    <input type="hidden" name="command" value="orders"/>
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Orders</button>
    </div>
</form>

<form action=${pageContext.request.contextPath}/shop?command=itemdelete method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Delete Item</button>
    </div>
</form>


</body>
</html>
