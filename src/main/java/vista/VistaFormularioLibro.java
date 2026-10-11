package vista;

import controlador.ControladorGestionDeLibros;
import model.Categoria;
import util.Validador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VistaFormularioLibro extends JDialog {
    private JPanel panel1;
    private JLabel lblTitulo1;
    private JTextField txtTitulo;
    private JLabel lblAutor;
    private JTextField txtAutor;
    private JLabel lblIsbn;
    private JTextField txtIsbn;
    private JLabel lblEditorial;
    private JTextField txtEditorial;
    private JLabel lblStock;
    private JSpinner spinnerStock;
    private JLabel lblCategoria;
    private JComboBox<Categoria> comboCategoria;
    private JPanel panelBotones;
    private JButton btnGuardar;
    private JButton btnCancelar;

    private ControladorGestionDeLibros controladorGestionDeLibros;
    private Boolean esCrear;

    //si datosLibro es null estamos agregando, si trae datos (de la fila seleccionada) estamos editando
    public VistaFormularioLibro(ControladorGestionDeLibros controladorGestionDeLibros, Object[] datosLibro, DefaultComboBoxModel<Categoria> modeloComboCategoria) {
        this.controladorGestionDeLibros = controladorGestionDeLibros;
        this.esCrear = datosLibro == null;
        setTitle(datosLibro == null ? "Agregar Libro" : "Editar Libro");
        setContentPane(panel1);
        setModal(true);
        comboCategoria.setModel(modeloComboCategoria);
        spinnerStock.setModel(new SpinnerNumberModel(0, 0, 9999, 1));
        llenarCampos(datosLibro);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validarCampos()) {
                    return;
                }
                try {
                    Integer idLibro = null;

                    if (datosLibro != null) {
                         idLibro = (Integer) datosLibro[0];

                    }

                    controladorGestionDeLibros.guardarLibro(idLibro, VistaFormularioLibro.this);
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

    //valida que los campos de texto no esten vacios y que se haya elegido una categoria
    private boolean validarCampos() {
        if (!Validador.esTextoValido(txtTitulo.getText())) {
            mostrarError("El titulo es obligatorio");
            return false;
        }
        if (!Validador.esTextoValido(txtAutor.getText())) {
            mostrarError("El autor es obligatorio");
            return false;
        }
        if (!Validador.esTextoValido(txtIsbn.getText())) {
            mostrarError("El ISBN es obligatorio");
            return false;
        }
        if (!Validador.esTextoValido(txtEditorial.getText())) {
            mostrarError("La editorial es obligatoria");
            return false;
        }
        if (comboCategoria.getSelectedItem() == null) {
            mostrarError("Selecciona una categoria");
            return false;
        }
        return true;
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JTextField getTxtTitulo() {return txtTitulo;}
    public JTextField getTxtAutor() {return txtAutor;}
    public JTextField getTxtIsbn() {return txtIsbn;}
    public JTextField getTxtEditorial() {return txtEditorial;}
    public JSpinner getSpinnerStock() {return spinnerStock;}
    public JComboBox getComboCategoria() {return comboCategoria;}

    public void llenarCampos(Object[] datosLibro) {
        if (datosLibro!=null){
            txtTitulo.setText(datosLibro[1].toString());
            txtAutor.setText(datosLibro[2].toString());
            txtIsbn.setText(datosLibro[3].toString());
            txtEditorial.setText(datosLibro[4].toString());
            spinnerStock.setValue(Integer.parseInt(datosLibro[5].toString()));
            comboCategoria.setSelectedItem(datosLibro[6].toString());

        }
    }
}
