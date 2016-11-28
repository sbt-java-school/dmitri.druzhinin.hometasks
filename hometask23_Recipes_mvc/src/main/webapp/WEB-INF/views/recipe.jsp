<%--
  Просмотр и редактирование рецепта.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>${recipeDto.name}</title>
</head>
<body>
<h1>${recipeDto.name}</h1><br>
<table>
    <thead>
    <tr>
        <th>Ингредиент</th>
        <th>Количество</th>
        <th>Единицы</th>
        <th colspan="1"/>
    </tr>
    </thead>
    <c:forEach items="${recipeDto.relations}" var="relation">
        <tr>
            <td>${relation.ingredientName}</td>
            <td>${relation.amount}</td>
            <td>${relation.unit}</td>
            <td>
                <form action="/recipes-jpa-mvc/relation/delete/${relation.ingredientName}" method="post">
                    <input type="submit" value="Удалить">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br><br>
<h2>Добавление ингредиента в рецепт</h2>
<sf:form action="/recipes-jpa-mvc/relation/add" method="post" modelAttribute="relationDto">
    <sf:select path="ingredientName" items="${ingredients}"/>
    <sf:input path="amount"/>
    <sf:input path="unit"/>
    <input type="submit" value="Добавить">
</sf:form>
<br>
<form action="/recipes-jpa-mvc/recipe/save" method="post">
    <input type="text" name="description" value="${recipeDto.description}">
    <input type="submit" value="Сохранить изменения">
</form>
</body>
</html>
