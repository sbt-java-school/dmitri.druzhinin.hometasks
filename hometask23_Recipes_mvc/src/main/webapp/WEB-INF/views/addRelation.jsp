<%--
  Добавление новых relations к создаваемому рецепту.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Добавление</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th>Название</th>
        <th>Количетсво</th>
        <th>Единицы</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${recipeDto.relations}" var="relation">
        <tr>
            <td>${relation.ingredientName}</td>
            <td>${relation.amount}</td>
            <td>${relation.unit}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<br>
<div>
Добавьте нужные ингредиенты для рецепта.
<sf:form action="/recipes-jpa-mvc/recipe/create/relation" modelAttribute="relationDto" method="post">
    <sf:select path="ingredientName" items="${ingredients}"/>
    <sf:input path="amount" type="number"/>
    <sf:input path="unit"/>
    <input type="submit" value="Добавить">
</sf:form>
</div>
</form>
<form action="/recipes-jpa-mvc/recipe/create/description" method="get">
    <input type="submit" value="Далее">
</form>
</body>
</html>
