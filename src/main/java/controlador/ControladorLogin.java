package controlador;

import dao.UsuarioDAO;
import model.Sesion;
import model.Usuario;
import util.Validador;
import vista.VistaLogin;

import java.sql.SQLException;

public class ControladorLogin {

    private VistaLogin vistaLogin;
    private UsuarioDAO usuarioDAO;


    public ControladorLogin(){
        vistaLogin = new VistaLogin(this);
        usuarioDAO = new UsuarioDAO();

        //funcion para hacer pruebas.
        //vistaLogin.llenar();
    }


    public void Ingresar() throws SQLException {
        vistaLogin.getBtnIngresar().setEnabled(false);

        String rut = vistaLogin.getjTextRut().getText();
        String contraseña = vistaLogin.getjPassContrasea().getText();
        Usuario usuario = null;

        if (Validador.esFormatoRutValido(rut) && Validador.esTextoValido(contraseña)) {
            usuario = usuarioDAO.autenticar(rut, contraseña);
        }

        if (usuario == null) {
            vistaLogin.mostrarError("Credenciales no válidas");
        }else{
            Sesion.getIntancia().setUsuario(usuario);
            vistaLogin.mostrarMensaje("Inicio de sesión correcto.\nBienvenido/a, " + usuario.getNombre() + ".");
            ocultarVista();
            new ControladorMenu(this);
        }

        vistaLogin.getBtnIngresar().setEnabled(true);
    }



    public void cerrarVentana() {vistaLogin.dispose();}
    public void ocultarVista(){vistaLogin.setVisible(false);}

    //muestra y limpia
    public void mostrarVista(){
        vistaLogin.limpiarjTextField();
        vistaLogin.setVisible(true);
        vistaLogin.toFront();
        vistaLogin.requestFocus();
    }



}
