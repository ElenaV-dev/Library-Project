<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Loan Details</title>
</head>
<body>

<h2>Loan Details</h2>

<p><strong>ID:</strong> ${loan.id}</p>
<p><strong>Book copy id:</strong> ${loan.bookCopyId}</p>
<p><strong>User id:</strong> ${loan.userId}</p>
<p><strong>Loan date:</strong> ${loan.loanDate}</p>
<p><strong>Return date:</strong> ${loan.returnDate}</p>

<br>

<a href="controller?entity=loan&action=findAll">Back to list</a>

</body>
</html>