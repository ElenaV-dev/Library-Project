<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Book Details</title>
</head>
<body>

<h2>Book Details</h2>

<p><strong>ID:</strong> ${book.id}</p>
<p><strong>Title:</strong> ${book.title}</p>
<p><strong>Year:</strong> ${book.year}</p>
<p><strong>ISBN:</strong> ${book.isbn}</p>
<p><strong>Publisher:</strong> ${book.publisher}</p>

<br>

<a href="controller?entity=book&action=findAll">Back to list</a>

</body>

<br><br>

<c:if test="${sessionScope.userRole == 'READER'}">
    <form action="${pageContext.request.contextPath}/controller" method="post" style="display:inline;">
        <input type="hidden" name="entity" value="loan">
        <input type="hidden" name="action" value="requestBook">
        <input type="hidden" name="bookId" value="${book.id}">
        <button type="submit">Запросить книгу</button>
    </form>

    <form action="${pageContext.request.contextPath}/controller" method="post" style="display:inline;">
        <input type="hidden" name="entity" value="loan">
        <input type="hidden" name="action" value="returnBook">
        <input type="hidden" name="bookId" value="${book.id}">
        <button type="submit">Вернуть книгу</button>
    </form>
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