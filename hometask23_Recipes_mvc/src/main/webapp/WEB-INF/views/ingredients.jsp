<%--
  Просмотр всех возможных ингредиентов, а также создание новых.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <title>Ингредиенты</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th colspan="2">Ингредиенты</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${ingredientDtoList}" var="ingredient">
        <tr>
            <td>${ingredient.name}</td>
            <td>
                <form action="/recipes-jpa-mvc/ingredient/delete/${ingredient.name}" method="post">
                    <input type="submit" value="Удалить">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<br>
<div>
Добавление нового:
<sf:form method="post" modelAttribute="ingredientDto">
    <sf:input path="name"/>
    <input type="submit" value="Создать">
</sf:form>
</div>
<form action="/recipes-jpa-mvc/" method="get">
    <input type="submit" value="К рецептам"/>
</form>
</body>
</html>
