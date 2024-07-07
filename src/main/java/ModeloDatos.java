import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModeloDatos {

    private static final Logger logger = LoggerFactory.getLogger(ModeloDatos.class);
    private static final String ERROR_MSG = "El error es: {}";

    private Connection con;
    private Statement set;
    private ResultSet rs;

    public void abrirConexion() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Con variables de entorno
            String dbHost = System.getenv().get("DATABASE_HOST");
            String dbPort = System.getenv().get("DATABASE_PORT");
            String dbName = System.getenv().get("DATABASE_NAME");
            String dbUser = System.getenv().get("DATABASE_USER");
            String dbPass = System.getenv().get("DATABASE_PASS");

            String url = dbHost + ":" + dbPort + "/" + dbName;
            con = DriverManager.getConnection(url, dbUser, dbPass);

        } catch (Exception e) {
            logger.error("No se ha podido conectar");
            logger.error(ERROR_MSG, e.getMessage());
        }
    }

    public boolean existeJugador(String nombre) {
        boolean existe = false;
        String cad;
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT id, Nombre FROM Jugadores");
            while (rs.next()) {
                cad = rs.getString("Nombre");
                cad = cad.trim();
                if (cad.compareTo(nombre.trim()) == 0) {
                    existe = true;
                }
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            logger.error("No lee de la tabla");
            logger.error(ERROR_MSG, e.getMessage());
        }
        return (existe);
    }

    public void actualizarJugador(String nombre) {
        try {
            set = con.createStatement();
            set.executeUpdate("UPDATE Jugadores SET votos=votos+1 WHERE nombre " + " LIKE '%" + nombre + "%'");
            rs.close();
            set.close();
        } catch (Exception e) {
            logger.error("No modifica la tabla");
            logger.error(ERROR_MSG, e.getMessage());
        }
    }

    public void insertarJugador(String nombre) {
        try {
            set = con.createStatement();
            set.executeUpdate("INSERT INTO Jugadores " + " (nombre,votos) VALUES ('" + nombre + "',1)");
            rs.close();
            set.close();
        } catch (Exception e) {
            logger.error("No inserta en la tabla");
            logger.error(ERROR_MSG, e.getMessage());
        }
    }

    public void reiniciarVotos() {
        try {
            set = con.createStatement();
            set.executeUpdate("UPDATE Jugadores SET votos=0");
            rs.close();
            set.close();
        } catch (Exception e) {
            logger.error("No modifica la tabla");
            logger.error(ERROR_MSG, e.getMessage());
        }
    }

    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT id, Nombre, Votos FROM Jugadores");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("Nombre");
                int votos = rs.getInt("Votos");

                Jugador jugador = new Jugador(id, nombre, votos);
                jugadores.add(jugador);
            }
            rs.close();
            set.close();
        } catch (SQLException e) {
            logger.error("Error al ejecutar la consulta SQL", e);
        }
        return jugadores;
    }


    public void cerrarConexion() {
        try {
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
