<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Библиотека</h1>

<div>
    <a href="${pageContext.request.contextPath}/jsp/login.jsp">Войти</a>
    <a href="${pageContext.request.contextPath}/jsp/register.jsp">Зарегистрироваться</a>
</div>

<hr>

<h2>Поиск книги</h2>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="entity" value="book">
    <input type="hidden" name="action" value="findByTitle">

    <input type="text" name="title" placeholder="Введите название книги"/>

    <button type="submit">Поиск</button>
</form>

<hr>

<h2>Список книг</h2>

<table border="1">
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