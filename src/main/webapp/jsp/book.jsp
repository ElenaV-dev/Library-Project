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
</html>