<%@ page session="true" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <title>MyConstruction • Inicio</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body class="app">
<main class="card">
    <h1>Bienvenid@ <%= session.getAttribute("user") %></h1>
    <p class="subtitle">Has iniciado sesión correctamente.</p>
    <a href="logout" class="btn" style="text-decoration:none;display:inline-block;text-align:center">Salir</a>
</main>
</body>
</html>
