package controlador;

import model.Sesion;
import vista.VistaPedirPrestamo;

public class ControladorPedirPrestamo {

    private VistaPedirPrestamo vistaPedirPrestamo;
    private ControladorMenu controladorMenu;

    public ControladorPedirPrestamo(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaPedirPrestamo = new VistaPedirPrestamo(this);
        cargarLibrosDisponibles();
    }

    //TODO: consultar con el DAO los libros con stock > 0 y por cada uno hacer
    //vistaPedirPrestamo.getModeloTabla().addRow(new Object[]{id, titulo, autor, stock});
    private void cargarLibrosDisponibles() {
        vistaPedirPrestamo.getModeloTabla().setRowCount(0);
    }

    //TODO: con Sesion.getIntancia().getUsuario().getId() como estudiante y el id de la fila seleccionada como libro,
    //crear el prestamo con el DAO, descontar el stock del libro y recargar la tabla
    public void pedirPrestamo(int filaSeleccionada) {
        if (filaSeleccionada < 0) {
            vistaPedirPrestamo.mostrarError("Selecciona un libro de la tabla");
            return;
        }
        int idEstudiante = Sesion.getIntancia().getUsuario().getId();
    }

    public void volver() {
        vistaPedirPrestamo.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
