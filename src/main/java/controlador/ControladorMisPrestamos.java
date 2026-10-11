package controlador;

import model.Sesion;
import vista.VistaMisPrestamos;

public class ControladorMisPrestamos {

    private VistaMisPrestamos vistaMisPrestamos;
    private ControladorMenu controladorMenu;

    public ControladorMisPrestamos(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaMisPrestamos = new VistaMisPrestamos(this);
        cargarPrestamos();
    }

    //TODO: con Sesion.getIntancia().getUsuario().getId() consultar con el DAO todos los prestamos de ese estudiante
    //y por cada uno hacer
    //vistaMisPrestamos.getModeloTabla().addRow(new Object[]{id, titulo, fechaPrestamo, fechaDevolucion, estado});
    private void cargarPrestamos() {
        int idEstudiante = Sesion.getIntancia().getUsuario().getId();
        vistaMisPrestamos.getModeloTabla().setRowCount(0);
    }

    public void volver() {
        vistaMisPrestamos.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
