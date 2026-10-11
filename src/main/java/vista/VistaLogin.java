package vista;

import controlador.ControladorLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class VistaLogin extends JFrame {
    private JPanel jpanelSesion;
    private JButton btnIngresar;
    private JButton btnCerrar;
    private JPasswordField jPassContrasea;
    private JTextField jTextRut;
    private JLabel jLContrasena;
    private JLabel jLRut;

    private ControladorLogin controladorLogin;



    public VistaLogin(ControladorLogin controladorLogin){
        this.controladorLogin = controladorLogin;
        setTitle("Gestor biblioteca");
        setContentPane(jpanelSesion);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controladorLogin.Ingresar();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    mostrarError("No se pudo conectar a la base de datos. Intente nuevamente.");
                }

            }
        });



        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }


    //limpiar jtextField
    public void limpiarjTextField(){
        jTextRut.setText("");
        jPassContrasea.setText("");
    }


    //inicio de sesion rapido
    public void llenar(){
        jTextRut.setText("12345678-9");
        jPassContrasea.setText("clave123");
        try {
            controladorLogin.Ingresar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






    //sets and gets
    public JButton getBtnIngresar() {return btnIngresar;}
    public JPasswordField getjPassContrasea() {return jPassContrasea;}
    public JTextField getjTextRut() {return jTextRut;}
}
