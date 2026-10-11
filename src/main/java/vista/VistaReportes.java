package vista;

import controlador.ControladorReportes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaReportes extends JFrame {
    private JPanel panel1;
    private JLabel lblTitulo;
    private JPanel panelSeleccion;
    private JLabel lblTipoReporte;
    private JComboBox comboTipoReporte;
    private JButton btnGenerar;
    private JTextArea areaResultado;
    private JPanel panelBotones;
    private JButton btnVolver;

    private ControladorReportes controladorReportes;

    public VistaReportes(ControladorReportes controladorReportes) {
        this.controladorReportes = controladorReportes;
        setTitle("Gestor biblioteca");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //el diseñador no permite cargar items de un combo, se hace por codigo
        //TODO: cambiar/completar los tipos de reporte segun lo que necesites generar
        comboTipoReporte.setModel(new DefaultComboBoxModel(new String[]{
                "Libros mas prestados",
                "Estudiantes con prestamos atrasados",
                "Inventario de libros"
        }));

        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorReportes.generarReporte((String) comboTipoReporte.getSelectedItem());
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorReportes.volver();
            }
        });

        pack();
        setMinimumSize(new java.awt.Dimension(600, 450));
        setLocationRelativeTo(null);
        setVisible(true);
        toFront();
        requestFocus();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public JTextArea getAreaResultado() {return areaResultado;}
    public JComboBox getComboTipoReporte() {return comboTipoReporte;}
}
