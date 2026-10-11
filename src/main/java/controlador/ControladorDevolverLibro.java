package controlador;

import model.Sesion;
import vista.VistaDevolverLibro;

public class ControladorDevolverLibro {

    private VistaDevolverLibro vistaDevolverLibro;
    private ControladorMenu controladorMenu;

    public ControladorDevolverLibro(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaDevolverLibro = new VistaDevolverLibro(this);
        cargarPrestamosActivos();
    }

    //TODO: con Sesion.getIntancia().getUsuario().getId() consultar con el DAO los prestamos activos (no devueltos)
    //de ese estudiante y por cada uno hacer
    //vistaDevolverLibro.getModeloTabla().addRow(new Object[]{idPrestamo, titulo, fechaPrestamo, fechaDevolucion});
    private void cargarPrestamosActivos() {
        int idEstudiante = Sesion.getIntancia().getUsuario().getId();
        vistaDevolverLibro.getModeloTabla().setRowCount(0);
    }

    //TODO: tomar el id de prestamo de la fila seleccionada (columna 0), marcarlo como devuelto con el DAO,
    //reponer el stock del libro y recargar la tabla
    public void devolverLibro(int filaSeleccionada) {
        if (filaSeleccionada < 0) {
            vistaDevolverLibro.mostrarError("Selecciona un prestamo de la tabla");
            return;
        }
        cargarPrestamosActivos();
    }

    public void volver() {
        vistaDevolverLibro.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
