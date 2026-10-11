package controlador;

import vista.VistaReportes;

public class ControladorReportes {

    private VistaReportes vistaReportes;
    private ControladorMenu controladorMenu;

    public ControladorReportes(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaReportes = new VistaReportes(this);
    }

    //TODO: segun el tipoReporte seleccionado, armar la consulta/logica correspondiente y mostrar el resultado con
    //vistaReportes.getAreaResultado().setText(resultado);
    public void generarReporte(String tipoReporte) {

    }

    public void volver() {
        vistaReportes.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
