<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Users</title>
</head>

<body>

<h2>Users</h2>

<table border="1">

<tr>
<th>ID</th>
<th>Last name</th>
<th>First name</th>
<th>Role</th>
<th>IIN</th>
<th>Address</th>
<th>Phone</th>
</tr>

<c:forEach var="user" items="${users}">

<tr>
<td>${user.id}</td>
<td>${user.lastName}</td>
<td>${user.firstName}</td>
<td>${user.role}</td>
<td>${user.iin}</td>
<td>${user.address}</td>
<td>${user.phone}</td>
</tr>

</c:forEach>

</table>

</body>
</html>