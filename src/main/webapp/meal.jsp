<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Добавить прием пищи</title>
</head>
<body>
<h1>Добавить прием пищи</h1>
<form method="POST" action="meals">
    <input hidden type="text" readonly="readonly" name="mealId" value="<c:out value="${meal.id}"/>"/>
    <dl>
        <dt>Дата/Время:</dt>
        <dd>
            <input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}"/>"/>
        </dd>
    </dl>
    <dl>
        <dt>Описание:</dt>
        <dd>
            <input type="text" name="description" value="<c:out value="${meal.description}"/>"/>
        </dd>
    </dl>
    <dl>
        <dt>Калории:</dt>
        <dd>
            <input type="text" name="calories" value="<c:out value="${meal.calories}"/>"/>
        </dd>
    </dl>
    <button type="submit">Сохранить</button>
</form>
<a href="meals?action=meals">Назад</a>
</body>
</html>
