<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ошибка</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<div class="container text-center mt-5">

    <%
        Object status = request.getAttribute("javax.servlet.error.status_code");
        Object message = request.getAttribute("javax.servlet.error.message");

        if (status == null) status = "Error";
        if (message == null) message = "Something went wrong";
    %>

    <div class="card shadow-sm p-5">

        <h1 class="display-4 text-danger">
            <%= status %>
        </h1>

        <p class="lead mt-3">
            <%= message %>
        </p>

        <hr>

        <a class="btn btn-primary mt-3"
           href="${pageContext.request.contextPath}/controller">
            ⬅ На главную
        </a>

    </div>

</div>

</body>
</html>