package controlador;

import dao.PrestamoDAO;
import model.Prestamo;
import model.Sesion;
import util.GestorPrestamos;
import vista.VistaDevolverLibro;

import java.sql.SQLException;

public class ControladorDevolverLibro {

    private VistaDevolverLibro vistaDevolverLibro;
    private ControladorMenu controladorMenu;

    public ControladorDevolverLibro(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaDevolverLibro = new VistaDevolverLibro(this);
        cargarPrestamosActivos();
    }

    //carga los prestamos activos (no devueltos) del estudiante logueado
    private void cargarPrestamosActivos() {
        int idEstudiante = Sesion.getIntancia().getUsuario().getId();
        vistaDevolverLibro.getModeloTabla().setRowCount(0);
        try {
            for (Prestamo prestamo : new PrestamoDAO().listarActivosPorEstudiante(idEstudiante)) {
                vistaDevolverLibro.getModeloTabla().addRow(new Object[]{
                        prestamo.getId(), prestamo.getTituloLibro(), prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion()
                });
            }
        } catch (SQLException e) {
            vistaDevolverLibro.mostrarError("Error al cargar los prestamos: " + e.getMessage());
        }
    }

    public void devolverLibro(int filaSeleccionada) {
        if (filaSeleccionada < 0) {
            vistaDevolverLibro.mostrarError("Selecciona un prestamo de la tabla");
            return;
        }
        int idPrestamo = (int) vistaDevolverLibro.getModeloTabla().getValueAt(filaSeleccionada, 0);

        new Thread(()->{

            try {
                GestorPrestamos.getInstance().devolverPrestamo(idPrestamo);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    vistaDevolverLibro.mostrarMensaje("Devolucion registrada");
                    cargarPrestamosActivos();
                });
            } catch (SQLException e) {
                javax.swing.SwingUtilities.invokeLater(() ->
                        vistaDevolverLibro.mostrarError("Error al registrar la devolucion: " + e.getMessage()));
            }
        }).start();
    }

    public void volver() {
        vistaDevolverLibro.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
