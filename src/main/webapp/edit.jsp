
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Список еды</title>
</head>
<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>Дата</dt>
            <dd><input type="text" name="dateTime" size=50
                       value="<%= meal.getDateTime().format(TimeUtil.getDataFormatter())%>"></dd>
        </dl>
        <dl>
            <dt>Описание</dt>
            <dd><input type="text" name="description" size=50 value="${meal.description}"></dd>
        </dl>
        <dl>
            <dt>Калории</dt>
            <dd><input type="text" name="calories" size=50 value="${meal.calories}"></dd>
        </dl>
        <hr>
        <button type="submit">Сохранить</button>
    </form>
    <button onclick="window.history.back()">Назад</button>
</section>
</body>
</html>