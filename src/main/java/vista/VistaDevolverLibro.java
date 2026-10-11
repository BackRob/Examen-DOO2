package vista;

import controlador.ControladorDevolverLibro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaDevolverLibro extends JFrame {
    private JPanel panel1;
    private JLabel lblTitulo;
    private JTable tabla;
    private JPanel panelBotones;
    private JButton btnDevolver;
    private JButton btnVolver;

    private DefaultTableModel modeloTabla;
    private ControladorDevolverLibro controladorDevolverLibro;

    public VistaDevolverLibro(ControladorDevolverLibro controladorDevolverLibro) {
        this.controladorDevolverLibro = controladorDevolverLibro;
        setTitle("Gestor biblioteca");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //el diseñador no define columnas ni bloquea la edicion de celdas, se arma el modelo por codigo
        String[] columnas = {"ID Prestamo", "Titulo", "Fecha Prestamo", "Fecha Devolucion"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modeloTabla);

        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorDevolverLibro.devolverLibro(tabla.getSelectedRow());
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorDevolverLibro.volver();
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

    public JTable getTabla() {return tabla;}
    public DefaultTableModel getModeloTabla() {return modeloTabla;}
}
