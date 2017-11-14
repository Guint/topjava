<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Добавить прием пищи</title>
</head>
<body>
<form method="POST" action="meals">
    <input hidden type="text" readonly="readonly" name="id" value="<c:out value="${meal.id}"/>"/> <br/>
    Дата/Время : <input type="datetime-local" name="dateTime"/>
    <br/>
    Описание : <input type="text" name="description"> <br/>
    Калории : <input type="text" name="calories"/> <br/>
    <input type="submit" value="Submit"/>
</form>

</body>
</html>
