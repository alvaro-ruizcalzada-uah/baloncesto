
import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import model.Jugador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Acb extends HttpServlet {

    private ModeloDatos bd;

    private static final Logger logger = LoggerFactory.getLogger(Acb.class);

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
        logger.info("Se inicia la conexión con la base de datos.");
    }

    private void reiniciarVotacion(HttpServletResponse res) throws IOException{
        bd.reiniciarVotos();
        logger.info("Se reinicia la votación.");
        res.sendRedirect(res.encodeRedirectURL("VotacionReiniciada.jsp"));
    }

    private void votar(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession s = req.getSession(true);
        String nombreP = req.getParameter("txtNombre");
        String nombre = req.getParameter("R1");
        if (nombre.equals("Otros")) {
            nombre = req.getParameter("txtOtros");
        }
        if (bd.existeJugador(nombre)) {
            bd.actualizarJugador(nombre);
        } else {
            bd.insertarJugador(nombre);
        }
        logger.info("Se vota a {}", nombre);
        s.setAttribute("nombreCliente", nombreP);
        res.sendRedirect(res.encodeRedirectURL("TablaVotos.jsp"));
    }

    private void mostrarVotos(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession s = req.getSession(true);
        List<Jugador> jugadores = bd.obtenerTodosLosJugadores();
        logger.info("Se muestra las votaciones de los {} jugadores.", jugadores.size());
        s.setAttribute("jugadores", jugadores);
        res.sendRedirect(res.encodeRedirectURL("VerVotos.jsp"));
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String accion = req.getParameter("B1");
        logger.info("Se procesa la acción {}", accion);
        switch(accion) {
            case "Poner votos a cero": 
                reiniciarVotacion(res);
                break;
            case "Ver votos":
                mostrarVotos(req, res);
                break;
            default:
                votar(req, res);
        }
    }

    @Override
    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
