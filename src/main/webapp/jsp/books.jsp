<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <title>Книги</title>

</head>

<body>

<div class="container">

<div class="d-flex justify-content-between align-items-center mb-4">

        <h2 class="mb-0">📚 Книги</h2>

        <div class="d-flex gap-2">

            <c:if test="${sessionScope.userRole == 'ADMIN'}">
                <a class="btn btn-success"
                   href="${pageContext.request.contextPath}/jsp/book-save.jsp">
                    + Добавить книгу
                </a>
            </c:if>

            <a class="btn btn-outline-danger"
               href="${pageContext.request.contextPath}/controller?entity=user&action=logout">
                Выйти
            </a>

        </div>
    </div>

    <!-- Table -->
    <div class="card shadow-sm">
        <div class="card-body p-0">

            <table class="table table-striped table-hover mb-0">

                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>Действие</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="book" items="${books}">
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>
                            <a class="btn btn-primary btn-sm"
                               href="${pageContext.request.contextPath}/controller?entity=book&action=findById&id=${book.id}">
                                Просмотреть
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>

        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>