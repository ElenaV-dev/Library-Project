<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Authors</title>
</head>

<body>

<h2>Authors</h2>

<table border="1">

<tr>
<th>ID</th>
<th>Last name</th>
<th>First name</th>
<th>Life years</th>
</tr>

<c:forEach var="author" items="${authors}">

<tr>
<td>${author.uuid}</td>
<td>${author.lastName}</td>
<td>${author.firstName}</td>
<td>${author.lifeYears}</td>
</tr>

</c:forEach>

</table>

</body>
</html>