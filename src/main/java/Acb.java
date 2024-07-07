
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class Acb extends HttpServlet {

    private ModeloDatos bd;

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    private void reiniciarVotacion(HttpServletResponse res) throws IOException{
        bd.reiniciarVotos();
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
        s.setAttribute("nombreCliente", nombreP);
        res.sendRedirect(res.encodeRedirectURL("TablaVotos.jsp"));
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String resetear = req.getParameter("B3");
        if (resetear != null && resetear.equals("Poner votos a cero")) {
            reiniciarVotacion(res);
        } else {
            votar(req, res);
        }
    }

    @Override
    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
