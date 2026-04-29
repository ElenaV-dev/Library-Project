<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <title>Добавить книгу</title>

</head>

<body>

<div class="container">

<div class="row justify-content-center">
        <div class="col-md-6">

            <h2 class="text-center mb-4">📚 Добавить книгу</h2>

            <form class="card p-4 shadow-sm"
                  action="${pageContext.request.contextPath}/controller"
                  method="post"
                  accept-charset="UTF-8">

                <input type="hidden" name="entity" value="book">
                <input type="hidden" name="action" value="save">

                <div class="mb-3">
                    <label class="form-label">Название</label>
                    <input class="form-control ${not empty errors.title ? 'is-invalid' : ''}"
                           type="text"
                           name="title"
                           value="${title}">
                           <c:if test="${not empty errors.title}">
                               <div class="invalid-feedback">
                                   ${errors.title}
                               </div>
                           </c:if>
                </div>

                <div class="mb-3">
                    <label class="form-label">Год</label>
                    <input class="form-control ${not empty errors.year ? 'is-invalid' : ''}"
                           type="number"
                           name="year"
                           value="${year}">
                           <c:if test="${not empty errors.year}">
                               <div class="invalid-feedback">
                                   ${errors.year}
                               </div>
                           </c:if>
                </div>

                <div class="mb-3">
                    <label class="form-label">ISBN</label>
                    <input class="form-control ${not empty errors.isbn ? 'is-invalid' : ''}"
                           type="text"
                           name="isbn"
                           value="${isbn}">
                           <c:if test="${not empty errors.isbn}">
                               <div class="invalid-feedback">
                                   ${errors.isbn}
                               </div>
                           </c:if>
                </div>

                <div class="mb-3">
                    <label class="form-label">Издатель</label>
                   <input class="form-control"
                          type="text"
                          name="publisher"
                          value="${publisher}">
                </div>

                <button class="btn btn-success w-100" type="submit">
                    💾 Сохранить
                </button>

            </form>

        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>