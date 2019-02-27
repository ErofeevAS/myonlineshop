<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 27.02.2019
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
    <title>MyTEst</title>
</head>
<body>
<form method="post" action="/myonlineshop/hello">
    Enter login:<input name="login" type="text"/><br>
    Enter password:<input name="password" type="password"/><br>
    <input type="submit">
</form>

    <table style="overflow-x:auto">
        <tr>
            <th>id</th>
            <th>name</th>
            <th>description</th>
            <th>price</th>
            <th>take</th>
        </tr>
        <tr>
            <td>1</td>
            <td>phone</td>
            <td>super phone</td>
            <td>666.6</td>
            <td><input type="checkbox" checked></td>
        </tr>
        <tr>
            <td>2</td>
            <td>phone</td>
            <td>super phone</td>
            <td>666.6</td>
            <td><input type="checkbox" checked></td>
        </tr>

    </table>


</body>
</html>
