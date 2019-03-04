<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.03.2019
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<div class="page-selector">
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

</body>
</html>
