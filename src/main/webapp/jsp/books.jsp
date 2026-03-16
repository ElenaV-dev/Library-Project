<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Books</title>
</head>

<body>

<h2>Books</h2>

<table border="1">

<tr>
<th>ID</th>
<th>Title</th>
<th>ISBN</th>
<th>Publisher</th>
</tr>

<c:forEach var="book" items="${books}">

<tr>
<td>${book.id}</td>
<td>${book.title}</td>
<td>${book.isbn}</td>
<td>${book.publisher}</td>
</tr>

</c:forEach>

</table>

</body>
</html>