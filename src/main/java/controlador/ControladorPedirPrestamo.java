package controlador;

import dao.LibroDAO;
import dao.PrestamoDAO;
import model.Libro;
import model.Prestamo;
import model.Sesion;
import util.GestorPrestamos;
import vista.VistaPedirPrestamo;

import java.sql.SQLException;

public class ControladorPedirPrestamo {

    private VistaPedirPrestamo vistaPedirPrestamo;
    private ControladorMenu controladorMenu;

    public ControladorPedirPrestamo(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaPedirPrestamo = new VistaPedirPrestamo(this);
        cargarLibrosDisponibles();
    }

    //carga en la tabla solo los libros que tienen stock disponible
    private void cargarLibrosDisponibles() {
        vistaPedirPrestamo.getModeloTabla().setRowCount(0);
        try {
            java.util.List<Libro> libros = new LibroDAO().listarLibros();
            if (libros != null) {
                for (Libro libro : libros) {
                    if (libro.getStock() > 0) {
                        vistaPedirPrestamo.getModeloTabla().addRow(new Object[]{
                                libro.getIdLibro(), libro.getTitulo(), libro.getAutor(), libro.getStock()
                        });
                    }
                }
            }
        } catch (SQLException e) {
            vistaPedirPrestamo.mostrarError("Error al cargar los libros: " + e.getMessage());
        }
    }

    //metodo con hilo nuevo para registrar el prestamo
    public void pedirPrestamo(int filaSeleccionada) {
        if (filaSeleccionada < 0) {
            vistaPedirPrestamo.mostrarError("Selecciona un libro de la tabla");
            return;
        }
        int idEstudiante = Sesion.getIntancia().getUsuario().getId();
        int idLibro = (int) vistaPedirPrestamo.getModeloTabla().getValueAt(filaSeleccionada, 0);

        new Thread(()->{

            try {
                GestorPrestamos.getInstance().registrarPrestamo(idEstudiante,idLibro);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    vistaPedirPrestamo.mostrarMensaje("Prestamo registrado");
                    cargarLibrosDisponibles();
                });
            } catch (SQLException e) {
                javax.swing.SwingUtilities.invokeLater(() ->
                        vistaPedirPrestamo.mostrarError("Error al registrar el prestamo: " + e.getMessage()));
            }
        }).start();

    }

    public void volver() {
        vistaPedirPrestamo.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
