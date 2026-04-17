<%@ page contentType="text/html; charset=UTF-8" %>

<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="entity" value="user">
    <input type="hidden" name="action" value="register">

    <div>
        <label>Фамилия:</label>
        <input type="text" name="lastName" autocomplete="off"/>
    </div>

    <div>
        <label>Имя:</label>
        <input type="text" name="firstName" autocomplete="off"/>
    </div>

    <div>
        <label>IIN:</label>
        <input type="text" name="iin" autocomplete="off"/>
    </div>

    <div>
        <label>Email:</label>
        <input type="email" name="email" autocomplete="off"/>
    </div>

    <div>
        <label>Телефон:</label>
        <input type="text" name="phone" autocomplete="off"/>
    </div>

    <div>
        <label>Пароль:</label>
        <input type="password" name="password" autocomplete="off"/>
    </div>

    <button type="submit">Зарегистрироваться</button>
</form>

<p style="color:red">${error}</p>