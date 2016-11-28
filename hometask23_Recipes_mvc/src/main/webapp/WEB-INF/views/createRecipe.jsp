<%--
  Создание нового рецепта.
--%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Создание нового рецепта</title>
</head>
<body>
    Название:
    <sf:form method="post" modelAttribute="recipeDto">
        <sf:label path="name">Name:</sf:label>
        <sf:input path="name"/><br>
        <sf:errors path="name" cssClass="error"/><br>
        <input type="submit" value="Далее">
    </sf:form>
</body>
</html>
