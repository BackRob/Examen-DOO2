package vista;

import controlador.ControladorGestionDeLibros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VistaGestionDeLibros extends JFrame {
    private JPanel panel1;
    private JLabel lblTitulo;
    private JTable tabla;
    private JPanel panelBotones;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVolver;

    private DefaultTableModel modeloTabla;
    private ControladorGestionDeLibros controladorGestionDeLibros;

    public VistaGestionDeLibros(ControladorGestionDeLibros controladorGestionDeLibros) {
        this.controladorGestionDeLibros = controladorGestionDeLibros;
        setTitle("Gestor biblioteca");
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] columnas = {"ID", "Titulo", "Autor", "ISBN", "Editorial", "Stock", "Categoria"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modeloTabla);

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controladorGestionDeLibros.vistaAgregarLibro();
                } catch (Exception ex) {
                    mostrarError("Error al abrir el formulario: " + ex.getMessage());
                }
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controladorGestionDeLibros.editarLibro(tabla.getSelectedRow());
                } catch (Exception ex) {
                    mostrarError("Error al abrir el formulario: " + ex.getMessage());
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controladorGestionDeLibros.eliminarLibro(tabla.getSelectedRow());
                } catch (Exception ex) {
                    mostrarError("Error al eliminar el libro: " + ex.getMessage());
                }
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorGestionDeLibros.volver();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controladorGestionDeLibros.volver();
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
    public void setModeloTabla(DefaultTableModel modeloTabla) {tabla.setModel(modeloTabla);}
}
