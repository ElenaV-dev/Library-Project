<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Панель библиотекаря</title>
</head>

<body>

<h2>Выдачи</h2>

<table border="1">

<tr>
<th>ID</th>
<th>Book copy id</th>
<th>User id</th>
<th>Loan date</th>
<th>Return date</th>
<th>Actions</th>
</tr>

<c:forEach var="loan" items="${loans}">

<tr>
<td>${loan.id}</td>
<td>${loan.bookCopyId}</td>
<td>${loan.userId}</td>
<td>${loan.loanDate}</td>
<td>${loan.returnDate}</td>

<td>

    <form action="${pageContext.request.contextPath}/controller" method="post" style="display:inline;">
        <input type="hidden" name="entity" value="loan">
        <input type="hidden" name="action" value="issue">
        <input type="hidden" name="id" value="${loan.id}">
        <button type="submit">Выдать</button>
    </form>

</td>
</tr>

</c:forEach>

</table>

<div>
    <a href="${pageContext.request.contextPath}/controller?entity=user&action=logout">
        Выйти
    </a>
</div>

</body>
</html>