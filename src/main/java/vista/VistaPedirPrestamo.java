package vista;

import controlador.ControladorPedirPrestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPedirPrestamo extends JFrame {
    private JPanel panel1;
    private JLabel lblTitulo;
    private JTable tabla;
    private JPanel panelBotones;
    private JButton btnPedirPrestamo;
    private JButton btnVolver;

    private DefaultTableModel modeloTabla;
    private ControladorPedirPrestamo controladorPedirPrestamo;

    public VistaPedirPrestamo(ControladorPedirPrestamo controladorPedirPrestamo) {
        this.controladorPedirPrestamo = controladorPedirPrestamo;
        setTitle("Gestor biblioteca");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //el diseñador no define columnas ni bloquea la edicion de celdas, se arma el modelo por codigo
        String[] columnas = {"ID", "Titulo", "Autor", "Stock Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modeloTabla);

        btnPedirPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorPedirPrestamo.pedirPrestamo(tabla.getSelectedRow());
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorPedirPrestamo.volver();
            }
        });

        pack();
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

    public JTable getTabla() {return tabla;}
    public DefaultTableModel getModeloTabla() {return modeloTabla;}
}
