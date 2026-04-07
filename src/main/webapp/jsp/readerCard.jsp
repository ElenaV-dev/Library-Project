<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Reader card Details</title>
</head>
<body>

<h2>Reader card Details</h2>

<p><strong>Reader ID:</strong> ${readerCard.readerId}</p>
<p><strong>Card number:</strong> ${readerCard.cardNumber}</p>
<p><strong>Issue date:</strong> ${readerCard.issueDate}</p>
<p><strong>Expiration date:</strong> ${readerCard.expirationDate}</p>

<br>

<a href="controller?entity=readerCard&action=findAll">Back to list</a>

</body>
</html>