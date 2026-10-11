package controlador;

import dao.LibroDAO;
import dao.PrestamoDAO;
import model.Libro;
import vista.VistaReportes;

import java.sql.SQLException;
import java.util.List;

public class ControladorReportes {

    private VistaReportes vistaReportes;
    private ControladorMenu controladorMenu;

    public ControladorReportes(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.vistaReportes = new VistaReportes(this);
    }

    public void generarReporte(String tipoReporte) {
        if (tipoReporte == null) {
            vistaReportes.mostrarError("Selecciona un tipo de reporte");
            return;
        }

        StringBuilder resultado = new StringBuilder();
        try {
            switch (tipoReporte) {
                case "Libros mas prestados" -> {
                    for (Object[] fila : new PrestamoDAO().contarPrestamosPorLibro()) {
                        resultado.append(fila[0]).append(" - ").append(fila[1]).append(" prestamo(s)\n");
                    }
                }
                case "Estudiantes con prestamos atrasados" -> {
                    for (Object[] fila : new PrestamoDAO().listarAtrasados()) {
                        resultado.append(fila[0]).append(" - ").append(fila[1])
                                .append(" (venció el ").append(fila[2]).append(")\n");
                    }
                }
                case "Inventario de libros" -> {
                    List<Libro> libros = new LibroDAO().listarLibros();
                    if (libros != null) {
                        for (Libro libro : libros) {
                            resultado.append(libro.getTitulo()).append(" - stock: ").append(libro.getStock()).append("\n");
                        }
                    }
                }
                default -> resultado.append("Reporte no reconocido");
            }
            vistaReportes.getAreaResultado().setText(resultado.length() == 0 ? "Sin resultados" : resultado.toString());
        } catch (SQLException e) {
            vistaReportes.mostrarError("Error al generar el reporte: " + e.getMessage());
        }
    }

    public void volver() {
        vistaReportes.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
