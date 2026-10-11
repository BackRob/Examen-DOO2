package controlador;

import dao.PrestamoDAO;
import model.Prestamo;
import model.Sesion;
import vista.VistaMisPrestamos;

import java.sql.SQLException;

public class ControladorMisPrestamos {

    private VistaMisPrestamos vistaMisPrestamos;
    private ControladorMenu controladorMenu;

    public ControladorMisPrestamos(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaMisPrestamos = new VistaMisPrestamos(this);
        cargarPrestamos();
    }

    //carga todos los prestamos (historicos) del estudiante logueado
    private void cargarPrestamos() {
        int idEstudiante = Sesion.getIntancia().getUsuario().getId();
        vistaMisPrestamos.getModeloTabla().setRowCount(0);
        try {
            for (Prestamo prestamo : new PrestamoDAO().listarPorEstudiante(idEstudiante)) {
                vistaMisPrestamos.getModeloTabla().addRow(new Object[]{
                        prestamo.getId(), prestamo.getTituloLibro(), prestamo.getFechaPrestamo(),
                        prestamo.getFechaDevolucion(), prestamo.getEstado()
                });
            }
        } catch (SQLException e) {
            vistaMisPrestamos.mostrarError("Error al cargar los prestamos: " + e.getMessage());
        }
    }

    public void volver() {
        vistaMisPrestamos.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
