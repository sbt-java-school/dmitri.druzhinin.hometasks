<%--
  Страница ошибки.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Error</title>
</head>
${errorMessage}
<form action="/recipes-jpa-mvc/" method="get">
    <input type="submit" value="На главную"/>
</form>
<body>

</body>
</html>
