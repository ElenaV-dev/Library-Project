<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <title>Панель библиотекаря</title>

</head>

<body>

<div class="container">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <h2 class="mb-0">📚 Выдачи книг</h2>

        <a class="btn btn-outline-danger"
           href="${pageContext.request.contextPath}/controller?entity=user&action=logout">
            Выйти
        </a>

    </div>

    <div class="card shadow-sm">
        <div class="card-body p-0">

            <table class="table table-striped table-hover mb-0">

                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Копия книги</th>
                    <th>Пользователь</th>
                    <th>Дата выдачи</th>
                    <th>Дата возврата</th>
                    <th>Действие</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="loan" items="${loans}">

                    <tr>
                        <td>${loan.id}</td>
                        <td>${loan.bookCopyId}</td>
                        <td>${loan.userId}</td>
                        <td>${loan.loanDate}</td>
                        <td>${loan.returnDate}</td>

                        <td>

                            <form action="${pageContext.request.contextPath}/controller"
                                  method="post">

                                <input type="hidden" name="entity" value="loan">
                                <input type="hidden" name="action" value="issue">
                                <input type="hidden" name="id" value="${loan.id}">

                                <button class="btn btn-success btn-sm"
                                        type="submit">
                                    📦 Выдать
                                </button>

                            </form>

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