<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>User Details</title>
</head>
<body>

<h2>User Details</h2>

<p><strong>ID:</strong> ${user.id}</p>
<p><strong>Last name:</strong> ${user.lastName}</p>
<p><strong>First name:</strong> ${user.firstName}</p>
<p><strong>Role:</strong> ${user.role}</p>
<p><strong>IIN:</strong> ${user.iin}</p>
<p><strong>Address:</strong> ${user.address}</p>
<p><strong>Phone:</strong> ${user.phone}</p>

<br>

<a href="controller?entity=user&action=findAll">Back to list</a>

</body>
</html>