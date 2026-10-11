package vista;

import controlador.ControladorGestionEstudiantes;
import dao.EstudianteDAO;
import model.Estudiante;
import util.Validador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        //datosEstudiante trae columnas {id, nombre, rut, curso, correo, contraseña}, se precargan solo las editables
        txtNombre.setText(datosEstudiante[1].toString());
        txtRut.setText(datosEstudiante[2].toString());
        txtCorreo.setText(datosEstudiante[4].toString());

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validarCampos()) {
                    return;
                }
                try {
                    int id = (int) datosEstudiante[0];
                    String curso = datosEstudiante[3] == null ? null : datosEstudiante[3].toString();
                    Estudiante estudiante = new Estudiante(id, txtNombre.getText(), txtRut.getText(), curso, txtCorreo.getText(), null);
                    new EstudianteDAO().editarEstudiante(estudiante);
                    controladorGestionEstudiantes.guardarEstudiante();
                } catch (Exception ex) {
                    mostrarError("Error al guardar el estudiante: " + ex.getMessage());
                    return;
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

    //valida que los campos de texto no esten vacios y que el rut tenga formato/digito verificador correcto
    private boolean validarCampos() {
        if (!Validador.esTextoValido(txtNombre.getText())) {
            mostrarError("El nombre es obligatorio");
            return false;
        }
        if (!Validador.esRutValido(txtRut.getText())) {
            mostrarError("El rut no es valido");
            return false;
        }
        if (!Validador.esTextoValido(txtCorreo.getText())) {
            mostrarError("El correo es obligatorio");
            return false;
        }
        return true;
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JTextField getTxtNombre() {return txtNombre;}
    public JTextField getTxtRut() {return txtRut;}
    public JTextField getTxtCorreo() {return txtCorreo;}
}
