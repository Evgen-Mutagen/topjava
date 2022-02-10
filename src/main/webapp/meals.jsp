<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Список еды </title>
</head>
<body>
<hr>
<h1>Список еды</h1>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
            <th>Редактирование</th>
            <th>Удаление</th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color: ${meal.excess ? "red" : "green"}">
                <td>
                ${meal.getDateTime().format(TimeUtil.DATE_FORMATTER)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>

                <td><a href="meals?id=${meal.id}&action=insert" class="button">Edit</a></td>
                <td><a href="meals?id=${meal.id}&action=delete" class="button">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <p><a href="meals?action=insert" class="button">Добавить еду</a></p>
    </section>
</body>
</html>
