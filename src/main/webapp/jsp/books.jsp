<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Books</title>
</head>

<body>

<h2>Books</h2>

<div>
    <a href="${pageContext.request.contextPath}/controller?entity=user&action=logout">
        Выйти
    </a>
</div>

<c:if test="${sessionScope.userRole == 'ADMIN'}">
    <div style="margin:10px 0;">
        <a href="${pageContext.request.contextPath}/jsp/book-save.jsp">
            <button>Добавить книгу</button>
        </a>
    </div>
</c:if>

<table border="1">

<tr>
<th>ID</th>
<th>Title</th>
<th>Action</th>
</tr>

<c:forEach var="book" items="${books}">

<tr>
    <td>${book.id}</td>
    <td>${book.title}</td>
    <td>
        <a href="${pageContext.request.contextPath}/controller?entity=book&action=findById&id=${book.id}">
            Просмотреть</a>
    </td>
</tr>

</c:forEach>

</table>

</body>
</html>