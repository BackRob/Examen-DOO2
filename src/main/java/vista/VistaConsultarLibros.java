package vista;

import controlador.ControladorConsultarLibros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaConsultarLibros extends JFrame {
    private JPanel panel1;
    private JLabel lblTitulo;
    private JPanel panelBusqueda;
    private JLabel lblBuscar;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tabla;
    private JPanel panelBotones;
    private JButton btnVolver;

    private DefaultTableModel modeloTabla;
    private ControladorConsultarLibros controladorConsultarLibros;

    public VistaConsultarLibros(ControladorConsultarLibros controladorConsultarLibros) {
        this.controladorConsultarLibros = controladorConsultarLibros;
        setTitle("Gestor biblioteca");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //el diseñador no define columnas ni bloquea la edicion de celdas, se arma el modelo por codigo
        String[] columnas = {"ID", "Titulo", "Autor", "ISBN", "Editorial", "Stock", "Categoria"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modeloTabla);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorConsultarLibros.buscar(txtBuscar.getText());
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorConsultarLibros.volver();
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
