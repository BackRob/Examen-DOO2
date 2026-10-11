package controlador;

import dao.CategoriaDAO;
import dao.LibroDAO;
import model.Categoria;
import model.Libro;
import vista.VistaFormularioLibro;
import vista.VistaGestionDeLibros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorGestionDeLibros {

    private VistaGestionDeLibros vistaGestionDeLibros;
    private ControladorMenu controladorMenu;

    public ControladorGestionDeLibros(ControladorMenu controladorMenu) throws SQLException {
        this.controladorMenu = controladorMenu;
        this.vistaGestionDeLibros = new VistaGestionDeLibros(this);
        cargarLibros();
    }

    //Llenar modelo de tabla con datos
    public DefaultTableModel cargarModeloLibros() throws SQLException {
        DefaultTableModel modeloLibros = vistaGestionDeLibros.getModeloTabla();
        modeloLibros.setRowCount(0);

        ArrayList<Libro> libros = (ArrayList<Libro>) new LibroDAO().listarLibros();

        for (Libro libro : libros) {
            modeloLibros.addRow(new Object[]{libro.getIdLibro(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getIsbn(),
                libro.getEditorial(),
                libro.getStock(),
                libro.getCategoria()});
        }
        return modeloLibros;
    }

    //carga los libros a la tabla
    private void cargarLibros() throws SQLException {
        vistaGestionDeLibros.setModeloTabla(cargarModeloLibros());
    }

    //abre el formulario para crear
    public void vistaAgregarLibro() throws SQLException {
        new VistaFormularioLibro(this, null, cargarCategorias());
    }

    public void editarLibro(int filaSeleccionada) throws SQLException {
        if (filaSeleccionada < 0) {
            vistaGestionDeLibros.mostrarError("Selecciona un libro de la tabla");
            return;
        }
        Object[] datosLibro = obtenerDatosFila(filaSeleccionada);
        new VistaFormularioLibro(this, datosLibro, cargarCategorias());
    }

    public void eliminarLibro(int filaSeleccionada) throws SQLException {
        if (filaSeleccionada < 0) {
            vistaGestionDeLibros.mostrarError("Selecciona un libro de la tabla");
            return;
        }
        new LibroDAO().eliminarLibro(obtenerIdLibroSeleccionado(filaSeleccionada));
        cargarLibros();
    }

    //llamado desde VistaFormularioLibro al apretar Guardar, pasando la propia ventana para leer sus campos
    public void guardarLibro(Integer id, VistaFormularioLibro vistaFormularioLibro) throws SQLException {
        Libro libro = obtenerLibro(id, vistaFormularioLibro);
        if (id == null) {
            new LibroDAO().crearLibro(libro);
        } else {
            new LibroDAO().editarLibro(libro);
        }
        cargarLibros();
    }




    public DefaultComboBoxModel cargarCategorias() throws SQLException {
        DefaultComboBoxModel modeloCategorias = new DefaultComboBoxModel();
        for (Categoria cat : new CategoriaDAO().listarCategorias()) {
            modeloCategorias.addElement(cat);
        }
        return modeloCategorias;
    }


    private int obtenerIdLibroSeleccionado(int filaSeleccionada) {
        Object[] libro = (Object[]) obtenerDatosFila(filaSeleccionada);
        return (int) libro[0];
    }


    private Object[] obtenerDatosFila(int fila) {
        int columnas = vistaGestionDeLibros.getModeloTabla().getColumnCount();
        Object[] datos = new Object[columnas];
        for (int i = 0; i < columnas; i++) {
            datos[i] = vistaGestionDeLibros.getModeloTabla().getValueAt(fila, i);
        }
        return datos;
    }

    public void volver() {
        vistaGestionDeLibros.dispose();
        controladorMenu.mostrarVistaMenu();
    }

    private Libro obtenerLibro(Integer id, VistaFormularioLibro vistaFormularioLibro) {
        String titulo = vistaFormularioLibro.getTxtTitulo().getText();
        String autor = vistaFormularioLibro.getTxtAutor().getText();
        String isbn = vistaFormularioLibro.getTxtIsbn().getText();
        String editorial = vistaFormularioLibro.getTxtEditorial().getText();
        int stock = (int) vistaFormularioLibro.getSpinnerStock().getValue();
        Categoria categoria = (Categoria) vistaFormularioLibro.getComboCategoria().getSelectedItem();
        return new Libro(id, titulo, autor, isbn, editorial, stock, categoria);
    }






}
