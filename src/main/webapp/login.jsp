<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login • MyConstruction</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body class="app">

<main class="card">
    <h1>Iniciar sesión</h1>

    <!-- Mostrar error si viene con ?error=1 -->
    <%
        if (request.getParameter("error") != null) {
    %>
        <p style="color:red;">Usuario o contraseña inválidos</p>
    <%
        }
    %>

    <!-- Formulario -->
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="username">Usuario</label><br>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Contraseña</label><br>
        <input type="password" id="password" name="password" required><br><br>

        <button type="submit" class="btn">Ingresar</button>
    </form>
</main>

</body>
</html>
