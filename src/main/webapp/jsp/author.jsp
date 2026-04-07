<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Author Details</title>
</head>
<body>

<h2>Author Details</h2>

<p><strong>ID:</strong> ${author.uuid}</p>
<p><strong>Last name:</strong> ${author.lastName}</p>
<p><strong>First name:</strong> ${author.firstName}</p>
<p><strong>Life years:</strong> ${author.lifeYears}</p>

<br>

<a href="controller?entity=author&action=findAll">Back to list</a>

</body>
</html>