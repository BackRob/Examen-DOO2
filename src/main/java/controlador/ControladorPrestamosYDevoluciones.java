package controlador;

import vista.VistaPrestamosYDevoluciones;

public class ControladorPrestamosYDevoluciones {

    private VistaPrestamosYDevoluciones vistaPrestamosYDevoluciones;
    private ControladorMenu controladorMenu;

    public ControladorPrestamosYDevoluciones(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaPrestamosYDevoluciones = new VistaPrestamosYDevoluciones(this);
        cargarPrestamos();
    }

    //TODO: consultar con el DAO todos los prestamos (de todos los estudiantes) y por cada uno hacer
    //vistaPrestamosYDevoluciones.getModeloTabla().addRow(new Object[]{id, estudiante, titulo, fechaPrestamo, fechaDevolucion, estado});
    private void cargarPrestamos() {
        vistaPrestamosYDevoluciones.getModeloTabla().setRowCount(0);
    }

    //TODO: tomar el id de prestamo de la fila seleccionada (columna 0), marcarlo como devuelto con el DAO,
    //reponer el stock del libro correspondiente y recargar la tabla
    public void marcarDevuelto(int filaSeleccionada) {
        if (filaSeleccionada < 0) {
            vistaPrestamosYDevoluciones.mostrarError("Selecciona un prestamo de la tabla");
            return;
        }
        cargarPrestamos();
    }

    public void volver() {
        vistaPrestamosYDevoluciones.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
