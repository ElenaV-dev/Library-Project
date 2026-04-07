<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Loans</title>
</head>

<body>

<h2>Loans</h2>

<table border="1">

<tr>
<th>ID</th>
<th>Book copy id</th>
<th>User id</th>
<th>Loan date</th>
<th>Return date</th>
</tr>

<c:forEach var="loan" items="${loans}">

<tr>
<td>${loan.id}</td>
<td>${loan.bookCopyId}</td>
<td>${loan.userId}</td>
<td>${loan.loanDate}</td>
<td>${loan.returnDate}</td>
</tr>

</c:forEach>

</table>

</body>
</html>