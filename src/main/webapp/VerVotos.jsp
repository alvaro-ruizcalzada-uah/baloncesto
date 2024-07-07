<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Votaci&oacute;n mejor jugador liga ACB</title>
        <link href="estilos.css" rel="stylesheet" type="text/css" />
    </head>
    <body class="resultado">
        <h2>Lista de Jugadores</h2>
        <table>
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Votos</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${jugadores}" var="jugador">
                    <tr>
                        <td>${jugador.nombre}</td>
                        <td>${jugador.votos}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <br> <a href="index.html"> Ir al comienzo</a>
    </body>
</html>
