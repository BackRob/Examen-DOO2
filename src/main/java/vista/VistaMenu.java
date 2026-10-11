package vista;

import controlador.ControladorMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VistaMenu extends JFrame {
    private JPanel panel1;
    private JButton btnCerrarSesion;
    private JButton btnGestionDeLibros;
    private JButton btnGestionEstudiantes;
    private JButton btnReportes;
    private JButton btnPedirPrestamo;
    private JButton btnPrestamosYDevoluciones;
    private JButton btnConsultarLibros;
    private JButton btnMisPrestamos;
    private JButton btnDevolverLibro;
    private JPanel jPanelMenu;

    private ControladorMenu controladorMenu;

    public VistaMenu(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        setTitle("Gestor biblioteca");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controladorMenu.cerrarVistaLogin();
            }
        });


        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorMenu.cerrarSesion();
            }
        });

        btnGestionDeLibros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controladorMenu.abrirGestionDeLibros();
                } catch (Exception ex) {
                    mostrarError("Error al abrir gestion de libros: " + ex.getMessage());
                    setVisible(true);
                }
            }
        });

        btnGestionEstudiantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controladorMenu.abrirGestionEstudiantes();
                } catch (Exception ex) {
                    mostrarError("Error al abrir gestion de estudiantes: " + ex.getMessage());
                    setVisible(true);
                }
            }
        });

        btnReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorMenu.abrirReportes();
            }
        });

        btnPedirPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorMenu.abrirPedirPrestamo();
            }
        });

        btnPrestamosYDevoluciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorMenu.abrirPrestamosYDevoluciones();
            }
        });

        btnConsultarLibros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorMenu.abrirConsultarLibros();
            }
        });

        btnMisPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorMenu.abrirMisPrestamos();
            }
        });

        btnDevolverLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorMenu.abrirDevolverLibro();
            }
        });



        pack();
        setLocationRelativeTo(null);
    }






    //gets
    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }
    public JButton getBtnGestionDeLibros() {return btnGestionDeLibros;}
    public JButton getBtnGestionEstudiantes() {
        return btnGestionEstudiantes;
    }
    public JButton getBtnReportes() {return btnReportes;}
    public JButton getBtnPedirPrestamo() {return btnPedirPrestamo;}
    public JButton getBtnPrestamosYDevoluciones() {return btnPrestamosYDevoluciones;}
    public JButton getBtnMisPrestamos() {return btnMisPrestamos;}
    public JButton getBtnConsultarLibros() {return btnConsultarLibros;}
    public JButton getBtnDevolverLibro() {return btnDevolverLibro;}



    //mensajes
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }





}
