<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <title>Библиотека</title>

</head>

<body>

<div class="container">

<h1 class="text-center display-4 mt-3 mb-4">Библиотека</h1>

<div class="mb-3">
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/jsp/login.jsp">Войти</a>
    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/jsp/register.jsp">Зарегистрироваться</a>
</div>

<hr>

<h2>Поиск книги</h2>

<form class="mb-4" action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="entity" value="book">
    <input type="hidden" name="action" value="findByTitle">

    <input class="form-control mb-2" type="text" name="title" placeholder="Введите название книги"/>

    <button class="btn btn-success" type="submit">Поиск</button>
</form>

<hr>

<h2>Список книг</h2>

<table class="table table-striped">
    <tr>
        <th>ID</th>
        <th>Название</th>

    </tr>

    <c:forEach var="book" items="${books}">
        <tr>
            <td>${book.id}</td>
            <td>${book.title}</td>

        </tr>
    </c:forEach>
</table>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>