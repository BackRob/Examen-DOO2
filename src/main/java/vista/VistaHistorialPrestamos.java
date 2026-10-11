package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        //el diseñador no define columnas ni bloquea la edicion de celdas, se arma el modelo por codigo
        String[] columnas = {"ID", "Titulo", "Fecha Prestamo", "Fecha Devolucion", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modeloTabla);

        //TODO: cargar aqui los prestamos del estudiante (idEstudiante) con PrestamoDAO.listarPorEstudiante(...)

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
