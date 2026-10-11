package controlador;

import dao.PrestamoDAO;
import model.Prestamo;
import util.GestorPrestamos;
import vista.VistaPrestamosYDevoluciones;

import java.sql.SQLException;

public class ControladorPrestamosYDevoluciones {

    private VistaPrestamosYDevoluciones vistaPrestamosYDevoluciones;
    private ControladorMenu controladorMenu;

    public ControladorPrestamosYDevoluciones(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaPrestamosYDevoluciones = new VistaPrestamosYDevoluciones(this);
        cargarPrestamos();
    }

    //carga todos los prestamos (de todos los estudiantes) en la tabla
    private void cargarPrestamos() {
        vistaPrestamosYDevoluciones.getModeloTabla().setRowCount(0);
        try {
            for (Prestamo prestamo : new PrestamoDAO().listarTodos()) {
                vistaPrestamosYDevoluciones.getModeloTabla().addRow(new Object[]{
                        prestamo.getId(), prestamo.getNombreEstudiante(), prestamo.getTituloLibro(),
                        prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion(), prestamo.getEstado()
                });
            }
        } catch (SQLException e) {
            vistaPrestamosYDevoluciones.mostrarError("Error al cargar los prestamos: " + e.getMessage());
        }
    }

    public void marcarDevuelto(int filaSeleccionada) {
        if (filaSeleccionada < 0) {
            vistaPrestamosYDevoluciones.mostrarError("Selecciona un prestamo de la tabla");
            return;
        }
        int idPrestamo = (int) vistaPrestamosYDevoluciones.getModeloTabla().getValueAt(filaSeleccionada, 0);

        new Thread(()->{

            try {
                GestorPrestamos.getInstance().devolverPrestamo(idPrestamo);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    vistaPrestamosYDevoluciones.mostrarMensaje("Devolucion registrada");
                    cargarPrestamos();
                });
            } catch (SQLException e) {
                javax.swing.SwingUtilities.invokeLater(() ->
                        vistaPrestamosYDevoluciones.mostrarError("Error al marcar la devolucion: " + e.getMessage()));
            }
        }).start();
    }

    public void volver() {
        vistaPrestamosYDevoluciones.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
