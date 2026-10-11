package controlador;

import dao.LibroDAO;
import model.Libro;
import vista.VistaConsultarLibros;

import java.sql.SQLException;
import java.util.List;

public class ControladorConsultarLibros {

    private VistaConsultarLibros vistaConsultarLibros;
    private ControladorMenu controladorMenu;

    public ControladorConsultarLibros(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaConsultarLibros = new VistaConsultarLibros(this);
        cargarLibros();
    }

    private void cargarLibros() {
        mostrarLibros(obtenerLibros());
    }

    //filtra en memoria por titulo o autor (si el texto viene vacio, muestra todos)
    public void buscar(String texto) {
        List<Libro> libros = obtenerLibros();
        if (texto != null && !texto.isBlank()) {
            String textoBusqueda = texto.trim().toLowerCase();
            libros.removeIf(libro ->
                    !libro.getTitulo().toLowerCase().contains(textoBusqueda) &&
                    !libro.getAutor().toLowerCase().contains(textoBusqueda));
        }
        mostrarLibros(libros);
    }

    private List<Libro> obtenerLibros() {
        try {
            List<Libro> libros = new LibroDAO().listarLibros();
            return libros != null ? libros : new java.util.ArrayList<>();
        } catch (SQLException e) {
            vistaConsultarLibros.mostrarError("Error al cargar los libros: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    private void mostrarLibros(List<Libro> libros) {
        vistaConsultarLibros.getModeloTabla().setRowCount(0);
        for (Libro libro : libros) {
            vistaConsultarLibros.getModeloTabla().addRow(new Object[]{
                    libro.getIdLibro(), libro.getTitulo(), libro.getAutor(),
                    libro.getIsbn(), libro.getEditorial(), libro.getStock(), libro.getCategoria()
            });
        }
    }

    public void volver() {
        vistaConsultarLibros.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
