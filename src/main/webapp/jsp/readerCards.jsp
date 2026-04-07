<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Reader cards</title>
</head>

<body>

<h2>Reader cards</h2>

<table border="1">

<tr>
<th>Reader ID</th>
<th>Card number</th>
<th>Issue date</th>
<th>Expiration date</th>
</tr>

<c:forEach var="readerCard" items="${readerCards}">

<tr>
<td>${readerCard.readerId}</td>
<td>${readerCard.cardNumber}</td>
<td>${readerCard.issueDate}</td>
<td>${readerCard.expirationDate}</td>
</tr>

</c:forEach>

</table>

</body>
</html>