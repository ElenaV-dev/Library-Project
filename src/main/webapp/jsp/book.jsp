<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Book Details</title>
</head>
<body>

<h2>Данные о книге</h2>

<p><strong>ID:</strong> ${book.id}</p>
<p><strong>Название:</strong> ${book.title}</p>
<p><strong>Год выпуска:</strong> ${book.year}</p>
<p><strong>ISBN:</strong> ${book.isbn}</p>
<p><strong>Издательство:</strong> ${book.publisher}</p>
<p><strong>Доступно экземпляров:</strong> ${availableCopies}</p>

<br>

<a href="controller?entity=book&action=findAll">Назад</a>

</body>

<br><br>

<c:if test="${sessionScope.userRole == 'READER'}">
    <c:choose>

            <c:when test="${availableCopies > 0}">
                <form action="${pageContext.request.contextPath}/controller" method="post" style="display:inline;">
                    <input type="hidden" name="entity" value="loan">
                    <input type="hidden" name="action" value="requestBook">
                    <input type="hidden" name="bookId" value="${book.id}">
                    <button type="submit">Запросить книгу</button>
                </form>
            </c:when>

            <c:otherwise>
                <p style="color:red;"><strong>Книга недоступна</strong></p>
            </c:otherwise>

        </c:choose>

</c:if>

<c:if test="${sessionScope.userRole == 'ADMIN'}">

    <a href="${pageContext.request.contextPath}/controller?entity=book&action=showUpdate&id=${book.id}">
        <button>Редактировать книгу</button>
    </a>

     <form action="${pageContext.request.contextPath}/controller" method="post" style="display:inline;">
            <input type="hidden" name="entity" value="book">
            <input type="hidden" name="action" value="deleteById">
            <input type="hidden" name="id" value="${book.id}">

            <button type="submit"
                    onclick="return confirm('Вы точно хотите удалить книгу?');">
                Удалить книгу
            </button>
        </form>

</c:if>

</html>