package controlador;

import model.Sesion;
import model.Usuario;
import vista.VistaMenu;

import java.sql.SQLException;

public class ControladorMenu {

    private VistaMenu vistaMenu;
    private ControladorLogin controladorLogin;


    //constructor
    ControladorMenu(ControladorLogin controladorLogin) {
        this.controladorLogin = controladorLogin;
        this.vistaMenu = new VistaMenu(this);
        Usuario usuarioActivo = Sesion.getIntancia().getUsuario();

        if (usuarioActivo ==null) {
            vistaMenu.mostrarError("Error al iniciar Sesion");
            controladorLogin.mostrarVista();
            return;
        }
        if (usuarioActivo.esBiblotecario()){
            sesionBibliotecario();
        }else{
            sesionEstudiante();
        }
    }



    //muestra la vista de estudiante
    private void sesionEstudiante() {
        vistaMenu.getBtnGestionDeLibros().setVisible(false);
        vistaMenu.getBtnGestionEstudiantes().setVisible(false);
        vistaMenu.getBtnPrestamosYDevoluciones().setVisible(false);
        vistaMenu.getBtnReportes().setVisible(false);

        vistaMenu.setVisible(true);
        vistaMenu.toFront();
        vistaMenu.requestFocus();
    }

    //muestra la vista de bibliotecario
    private void sesionBibliotecario() {
        vistaMenu.getBtnConsultarLibros().setVisible(false);
        vistaMenu.getBtnPedirPrestamo().setVisible(false);
        vistaMenu.getBtnDevolverLibro().setVisible(false);
        vistaMenu.getBtnMisPrestamos().setVisible(false);

        vistaMenu.setVisible(true);
        vistaMenu.toFront();
        vistaMenu.requestFocus();
    }


    //cerrar ventana oculta
    public void cerrarVistaLogin(){
        controladorLogin.cerrarVentana();
    }

    //cerrar Sesion
    public void cerrarSesion(){
        Sesion.getIntancia().cerrarSesion();
        vistaMenu.dispose();
        controladorLogin.mostrarVista();
    }

    //vuelve a mostrar el menu desde una vista hija
    public void mostrarVistaMenu(){
        vistaMenu.setVisible(true);
        vistaMenu.toFront();
        vistaMenu.requestFocus();
    }

    //abre las vistas de cada boton del menu
    public void abrirGestionDeLibros() throws SQLException {
        vistaMenu.setVisible(false);
        new ControladorGestionDeLibros(this);
    }

    public void abrirGestionEstudiantes() throws SQLException {
        vistaMenu.setVisible(false);
        new ControladorGestionEstudiantes(this);
    }

    public void abrirReportes(){
        vistaMenu.setVisible(false);
        new ControladorReportes(this);
    }

    public void abrirPedirPrestamo(){
        vistaMenu.setVisible(false);
        new ControladorPedirPrestamo(this);
    }

    public void abrirPrestamosYDevoluciones(){
        vistaMenu.setVisible(false);
        new ControladorPrestamosYDevoluciones(this);
    }

    public void abrirConsultarLibros(){
        vistaMenu.setVisible(false);
        new ControladorConsultarLibros(this);
    }

    public void abrirMisPrestamos(){
        vistaMenu.setVisible(false);
        new ControladorMisPrestamos(this);
    }

    public void abrirDevolverLibro(){
        vistaMenu.setVisible(false);
        new ControladorDevolverLibro(this);
    }




}
