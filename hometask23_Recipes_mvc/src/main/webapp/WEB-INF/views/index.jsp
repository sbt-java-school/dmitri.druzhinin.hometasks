<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Книга рецептов</title>
</head>
<body>
<h1>Рецепты</h1>
<table border="1">

    <tbody>
    <c:forEach items="${recipeDtoList}" var="recipeDto">
        <tr>
            <td><c:out value="${recipeDto.name}"/></td>
            <td>
                <form action="recipe/${recipeDto.name}" method="get">
                    <input type="submit" value="Смотреть"/>
                </form>
            </td>
            <td>
                <form action="recipe/delete/${recipeDto.name}" method="post">
                    <input type="submit" value="Удалить">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="recipe/create" method="get">
    <input type="submit" value="Создать"/>
</form>
<br>
<form action="/recipes-jpa-mvc/ingredient/create" method="get">
    <input type="submit" value="Ингредиенты">
</form>
</body>
</html>
