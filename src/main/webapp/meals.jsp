<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список еды </title>
</head>
<body>
<div class=container3>
    <h1>Список еды</h1>
</div>
<table class=container5 border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan=2>Action</th>
    </tr>
    <jsp:useBean id="mealTo" scope="request" type="java.util.List"/>
    <c:forEach items="${mealTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style=" color : ${mealTo.excess == true ? "red":"green"}">
            <td>${f:formatLocalDateTime(mealTo.dateTime)}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?action=edit&mealsId=${mealTo.uuid}">Update</a></td>
            <td><a href="meals?action=delete&mealsId=${mealTo.uuid}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<div class=container3>
    <p><a href="meals?action=insert">Добавить еду</a></p>
</div>
</body>
</html>
