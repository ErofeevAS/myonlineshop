<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<form action=${pageContext.request.contextPath}/shop?command=profile_menu method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Profile</button>
    </div>
</form>
<form action=${pageContext.request.contextPath}/shop?command=items method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Items</button>
    </div>
</form>

<form action=${pageContext.request.contextPath}/shop?command=logout method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Logout</button>
    </div>
</form>


<form action=${pageContext.request.contextPath}/shop?command=my_orders method="post">
    <input type="hidden" name="command" value="myorders"/>
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">My Orders</button>
    </div>
</form>


</body>
</html>
