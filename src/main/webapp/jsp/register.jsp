<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <title>Регистрация</title>

</head>

<body>

<div class="container">

<div class="row justify-content-center">
        <div class="col-md-5">

            <h2 class="text-center mt-5 mb-4">Регистрация</h2>

            <form class="card p-4 shadow-sm"
                  action="${pageContext.request.contextPath}/controller"
                  method="post">

                <input type="hidden" name="entity" value="user">
                <input type="hidden" name="action" value="register">

                <div class="mb-3">
                    <label class="form-label">Фамилия</label>
                    <input class="form-control" type="text" name="lastName" autocomplete="off">
                </div>

                <div class="mb-3">
                    <label class="form-label">Имя</label>
                    <input class="form-control" type="text" name="firstName" autocomplete="off">
                </div>

                <div class="mb-3">
                    <label class="form-label">IIN</label>
                    <input class="form-control" type="text" name="iin" autocomplete="off">
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input class="form-control" type="email" name="email" autocomplete="off">
                </div>

                <div class="mb-3">
                    <label class="form-label">Телефон</label>
                    <input class="form-control" type="text" name="phone" autocomplete="off">
                </div>

                <div class="mb-3">
                    <label class="form-label">Пароль</label>
                    <input class="form-control" type="password" name="password" autocomplete="off">
                </div>

                <button class="btn btn-success w-100" type="submit">
                    Зарегистрироваться
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