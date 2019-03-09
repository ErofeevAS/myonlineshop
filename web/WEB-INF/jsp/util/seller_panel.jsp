<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<form action=${pageContext.request.contextPath}/shop?command=profile_menu method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Profile</button>
    </div>
</form>
<form action=${pageContext.request.contextPath}/shop?command=add_item_page method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">add item</button>
    </div>
</form>

<form action=${pageContext.request.contextPath}/shop?command=logout method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Logout</button>
    </div>
</form>

<form action=${pageContext.request.contextPath}/shop?command=orders method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Orders</button>
    </div>
</form>

<form action=${pageContext.request.contextPath}/shop?command=items_delete method="post">
    <div class="form-label-group">
        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Delete Item</button>
    </div>
</form>

<form action=${pageContext.request.contextPath}/shop/import method="post" enctype="multipart/form-data">
    <div class="btn btn-lg btn-primary btn-block text-uppercase">
        <label class="btn btn-lg btn-primary btn-block text-uppercase">
            Import <input name="file" type="file" style="display: none">
            <input type="submit" name="s">
        </label>
    </div>
</form>


</body>
</html>
