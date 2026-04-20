<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Edit Book</title>
</head>
<body>

<h2>Edit Book</h2>

<form action="${pageContext.request.contextPath}/controller" method="post" accept-charset="UTF-8">

    <input type="hidden" name="entity" value="book">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${book.id}">

    <div>
        Title:
        <input type="text" name="title" value="${book.title}">
    </div>

    <div>
        Year:
        <input type="text" name="year" value="${book.year}">
    </div>

    <div>
        ISBN:
        <input type="text" name="isbn" value="${book.isbn}">
    </div>

    <div>
        Publisher:
        <input type="text" name="publisher" value="${book.publisher}">
    </div>

    <button type="submit">Save</button>

</form>

</body>
</html>