<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <nav aria-label="Page navigation example">


        <ul class="pagination">
            <li class="page-item"><a class="page-link"

                    <c:if test="${page<=1}">
                        <c:set value="1" var="page"></c:set>
                    </c:if>
                                     href="${pageContext.request.contextPath}/shop?command=${param.command}&page=${page-1}&amount=${amount}">Previous</a>
            </li>
            <c:forEach var="i" begin="1" end="${maxpages-1}" step="1">
                <li class="page-item"><a class="page-link"
                                         href="${pageContext.request.contextPath}/shop?command=${param.command}&page=${i}&amount=${amount}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item"><a class="page-link"
                                     href="${pageContext.request.contextPath}/shop?command=${param.command}&page=${maxpages}&amount=${amount}">Last</a>
            </li>


            <form action="${pageContext.request.contextPath}/shop?command=${param.command}&page=1" method="post" id="selectform">
                <input type='submit' name='change'/>
            </form>
            <br>
            <select name="amount" form="selectform">
                <option selected="selected">${amount}</option>
                <option>2</option>
                <option>5</option>
                <option>10</option>
                <option>50</option>
            </select>

            </li>


        </ul>
    </nav>
</div>

</body>
</html>
