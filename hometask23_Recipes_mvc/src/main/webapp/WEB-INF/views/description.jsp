<%--
  Добавление описания к создаваемому рецепту.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Описания</title>
</head>
<body>
Опишите процесс приготовления:
<form action="/recipes-jpa-mvc/recipe/create/description" method="post">
    <input type="text" name="description"/>
    <input type="submit" value="Сохранить рецепт">
</form>
</body>
</html>
