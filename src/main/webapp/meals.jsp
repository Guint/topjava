<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список еды</title>
</head>
<body>
<h1>Моя еда</h1>
<table border="1">
    <thead>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan=2></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealList}" var="meal">
        <c:choose>
            <c:when test="${meal.exceed}">
                <tr bgcolor="red">
                    <td><c:out value="${meal.dateTime}"/></td>
                    <td><c:out value="${meal.description}"/></td>
                    <td><c:out value="${meal.calories}"/></td>
                    <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Редактировать</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Удалить</a></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr bgcolor="green">
                    <td><c:out value="${meal.dateTime}"/></td>
                    <td><c:out value="${meal.description}"/></td>
                    <td><c:out value="${meal.calories}"/></td>
                    <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Редактировать</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Удалить</a></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Добавить</a></p>
</body>
</html>
