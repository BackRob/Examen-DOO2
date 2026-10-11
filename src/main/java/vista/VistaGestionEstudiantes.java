package vista;

import controlador.ControladorGestionEstudiantes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class VistaGestionEstudiantes extends JFrame {
    private JPanel panel1;
    private JLabel lblTitulo;
    private JTable tabla;
    private JPanel panelBotones;
    private JButton btnVerPrestamos;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVolver;

    private DefaultTableModel modeloTabla;
    private ControladorGestionEstudiantes controladorGestionEstudiantes;

    public VistaGestionEstudiantes(ControladorGestionEstudiantes controladorGestionEstudiantes) {
        this.controladorGestionEstudiantes = controladorGestionEstudiantes;
        setTitle("Gestor biblioteca");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] columnas = {"ID", "Nombre", "Rut", "Curso", "Correo","contraseña"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modeloTabla);

        btnVerPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorGestionEstudiantes.verPrestamos(tabla.getSelectedRow());
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorGestionEstudiantes.editarEstudiante(tabla.getSelectedRow());
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controladorGestionEstudiantes.eliminarEstudiante(tabla.getSelectedRow());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorGestionEstudiantes.volver();
            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {controladorGestionEstudiantes.volver();
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
