<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <title>Вход</title>

</head>

<body>

<div class="container">

<div class="row justify-content-center">
        <div class="col-md-4">

            <h2 class="text-center mt-5 mb-4">Вход</h2>

            <form class="card p-4 shadow-sm"
                  action="${pageContext.request.contextPath}/controller"
                  method="post">

                <input type="hidden" name="entity" value="user">
                <input type="hidden" name="action" value="login">

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input class="form-control" type="text" name="email"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Пароль</label>
                    <input class="form-control" type="password" name="password"/>
                </div>

                <button class="btn btn-primary w-100" type="submit">
                    Войти
                </button>

            </form>

            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">
                        ${error}
                </div>
            </c:if>

        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>