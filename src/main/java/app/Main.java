package app;

import controlador.ControladorLogin;
import dao.ConexionBD;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ConexionBD.getIntancia().cerrar();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));



        SwingUtilities.invokeLater(() -> {
            ControladorLogin controlador = new ControladorLogin();
        });
    }
}

