<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <title>MyConstruction • Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body class="app">
<main class="card">
    <h1>Iniciar sesión</h1>
    <p class="subtitle">Accede con tu usuario de MyConstruction</p>

    <form method="post" action="${pageContext.request.contextPath}/login"class="form">
        <label>
            <span>Usuario</span>
            <input type="text" name="username" required autocomplete="username">
        </label>

        <label>
            <span>Contraseña</span>
            <input type="password" name="password" required autocomplete="current-password">
        </label>

        <button type="submit" class="btn">Ingresar</button>
    </form>

    <footer class="foot">
        <small>© MyConstruction_nqm</small>
    </footer>
</main>
</body>
</html>
