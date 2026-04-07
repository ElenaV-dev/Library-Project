<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Book copy Details</title>
</head>
<body>

<h2>Book copy Details</h2>

<p><strong>ID:</strong> ${bookCopy.id}</p>
<p><strong>Book id:</strong> ${bookCopy.bookId}</p>
<p><strong>Inventory number:</strong> ${bookCopy.inventoryNumber}</p>
<p><strong>Status:</strong> ${bookCopy.status}</p>

<br>

<a href="controller?entity=bookCopy&action=findAll">Back to list</a>

</body>
</html>