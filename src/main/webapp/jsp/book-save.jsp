<%@ page contentType="text/html;charset=UTF-8" %>

<h2>Добавить книгу</h2>

<form action="${pageContext.request.contextPath}/controller" method="post" accept-charset="UTF-8">

    <input type="hidden" name="entity" value="book">
    <input type="hidden" name="action" value="save">

    <div>
        <label>Название:</label>
        <input type="text" name="title"/>
    </div>

    <div>
        <label>Год:</label>
        <input type="number" name="year"/>
    </div>

    <div>
        <label>ISBN:</label>
        <input type="text" name="isbn"/>
    </div>

    <div>
        <label>Издатель:</label>
        <input type="text" name="publisher"/>
    </div>

    <button type="submit">Сохранить</button>
</form>

<p style="color:red">${error}</p>