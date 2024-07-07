<%@ page import="java.util.List" %>
<%@ page import="model.Jugador" %>
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
                <% List<Jugador> jugadores = (List<Jugador>) session.getAttribute("jugadores"); %>
                <% for (Jugador jugador : jugadores) { %>
                    <tr>
                        <td><%= jugador.getId() %></td>
                        <td><%= jugador.getNombre() %></td>
                        <td><%= jugador.getVotos() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <br>
        <br> <a href="index.html"> Ir al comienzo</a>
    </body>
</html>
