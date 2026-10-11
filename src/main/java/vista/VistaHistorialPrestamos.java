package vista;

import dao.PrestamoDAO;
import model.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VistaHistorialPrestamos extends JDialog {
    private JPanel panel1;
    private JTable tabla;
    private JPanel panelBotones;
    private JButton btnCerrar;

    private DefaultTableModel modeloTabla;

    //se abre desde Gestion de Estudiantes al apretar "Ver Prestamos" sobre un estudiante
    public VistaHistorialPrestamos(int idEstudiante) {
        setTitle("Historial de Prestamos");
        setContentPane(panel1);
        setModal(true);

        String[] columnas = {"ID", "Titulo", "Fecha Prestamo", "Fecha Devolucion", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modeloTabla);

        try {
            for (Prestamo prestamo : new PrestamoDAO().listarPorEstudiante(idEstudiante)) {
                modeloTabla.addRow(new Object[]{
                        prestamo.getId(), prestamo.getTituloLibro(), prestamo.getFechaPrestamo(),
                        prestamo.getFechaDevolucion(), prestamo.getEstado()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el historial: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public DefaultTableModel getModeloTabla() {return modeloTabla;}
}
