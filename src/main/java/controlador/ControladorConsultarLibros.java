package controlador;

import vista.VistaConsultarLibros;

public class ControladorConsultarLibros {

    private VistaConsultarLibros vistaConsultarLibros;
    private ControladorMenu controladorMenu;

    public ControladorConsultarLibros(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaConsultarLibros = new VistaConsultarLibros(this);
        cargarLibros();
    }

    //TODO: consultar todos los libros con el DAO y por cada uno hacer
    //vistaConsultarLibros.getModeloTabla().addRow(new Object[]{id, titulo, autor, isbn, editorial, stock, categoria});
    private void cargarLibros() {
        vistaConsultarLibros.getModeloTabla().setRowCount(0);
    }

    //TODO: filtrar los libros por titulo/autor con el DAO (o filtrar en memoria) y recargar la tabla con el resultado
    public void buscar(String texto) {

    }

    public void volver() {
        vistaConsultarLibros.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
