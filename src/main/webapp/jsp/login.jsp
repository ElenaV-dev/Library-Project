<%@ page contentType="text/html; charset=UTF-8" %>

<h2>Вход</h2>

<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="entity" value="user">
    <input type="hidden" name="action" value="login">

    <div>
        <label>Логин:</label>
        <input type="text" name="email"/>
    </div>

    <div>
        <label>Пароль:</label>
        <input type="password" name="password"/>
    </div>

    <button type="submit">Войти</button>
</form>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>