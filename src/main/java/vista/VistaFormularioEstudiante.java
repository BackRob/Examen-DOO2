package vista;

import controlador.ControladorGestionEstudiantes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VistaFormularioEstudiante extends JDialog {
    private JPanel panel1;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblRut;
    private JTextField txtRut;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private JPanel panelBotones;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private ControladorGestionEstudiantes controladorGestionEstudiantes;

    //datosEstudiante trae la fila seleccionada de la tabla (id, nombre, rut, correo) para precargar el formulario
    public VistaFormularioEstudiante(ControladorGestionEstudiantes controladorGestionEstudiantes, Object[] datosEstudiante) {
        this.controladorGestionEstudiantes = controladorGestionEstudiantes;
        setTitle("Editar Estudiante");
        setContentPane(panel1);
        setModal(true);

        //TODO: precargar txtNombre, txtRut y txtCorreo con los valores de datosEstudiante

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: validar los campos y actualizar el estudiante con el DAO antes de avisar al controlador
                try {
                    controladorGestionEstudiantes.guardarEstudiante();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getTxtNombre() {return txtNombre;}
    public JTextField getTxtRut() {return txtRut;}
    public JTextField getTxtCorreo() {return txtCorreo;}
}
