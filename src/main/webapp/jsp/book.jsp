<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <title>Данные о книге</title>

</head>

<body>

<div class="container">

<div class="d-flex justify-content-between align-items-center mb-4">

        <h2 class="mb-0">📖 ${book.title}</h2>

        <a class="btn btn-outline-secondary"
           href="${pageContext.request.contextPath}/controller?entity=book&action=findAll">
            ← Назад
        </a>

    </div>

    <!-- Book card -->
    <div class="card shadow-sm mb-4">
        <div class="card-body">

            <p><strong>ID:</strong> ${book.id}</p>
            <p><strong>Год выпуска:</strong> ${book.year}</p>
            <p><strong>ISBN:</strong> ${book.isbn}</p>
            <p><strong>Издательство:</strong> ${book.publisher}</p>

            <hr>

            <p class="fs-5">
                <strong>Доступно экземпляров:</strong>
                <span class="badge bg-primary">
                    ${availableCopies}
                </span>
            </p>

        </div>
    </div>

    <!-- Reader actions -->
    <c:if test="${sessionScope.userRole == 'READER'}">

        <c:choose>

            <c:when test="${availableCopies > 0 and param.success != 'requested'}">

                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="entity" value="loan">
                    <input type="hidden" name="action" value="requestBook">
                    <input type="hidden" name="bookId" value="${book.id}">

                    <button class="btn btn-success w-100" type="submit">
                        📩 Запросить книгу
                    </button>
                </form>

            </c:when>

            <c:when test="${param.success == 'requested'}">

                <div class="alert alert-success mt-3">
                    Книга успешно запрошена
                </div>

            </c:when>

            <c:otherwise>

                <div class="alert alert-danger mt-3">
                    Книга недоступна
                </div>

            </c:otherwise>

        </c:choose>

    </c:if>

    <!-- Admin actions -->
    <c:if test="${sessionScope.userRole == 'ADMIN'}">

        <div class="d-flex gap-2 mt-4">

            <a class="btn btn-warning"
               href="${pageContext.request.contextPath}/controller?entity=book&action=showUpdate&id=${book.id}">
                ✏ Редактировать
            </a>

            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="entity" value="book">
                <input type="hidden" name="action" value="deleteById">
                <input type="hidden" name="id" value="${book.id}">

                <button class="btn btn-danger"
                        onclick="return confirm('Вы точно хотите удалить книгу?');">
                    🗑 Удалить
                </button>
            </form>

        </div>

    </c:if>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>