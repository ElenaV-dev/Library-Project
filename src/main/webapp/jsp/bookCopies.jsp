<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Book copies</title>
</head>

<body>

<h2>Book copies</h2>

<table border="1">

<tr>
<th>ID</th>
<th>Book id</th>
<th>Inventory number</th>
<th>Status</th>
</tr>

<c:forEach var="bookCopy" items="${bookCopies}">

<tr>
<td>${bookCopy.id}</td>
<td>${bookCopy.bookId}</td>
<td>${bookCopy.inventoryNumber}</td>
<td>${bookCopy.status}</td>
</tr>

</c:forEach>

</table>

</body>
</html>