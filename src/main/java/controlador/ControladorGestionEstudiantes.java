package controlador;

import dao.EstudianteDAO;
import dao.LibroDAO;
import model.Estudiante;
import model.Libro;
import vista.VistaFormularioEstudiante;
import vista.VistaGestionEstudiantes;
import vista.VistaHistorialPrestamos;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorGestionEstudiantes {

    private VistaGestionEstudiantes vistaGestionEstudiantes;
    private ControladorMenu controladorMenu;

    public ControladorGestionEstudiantes(ControladorMenu controladorMenu) throws SQLException {
        this.controladorMenu = controladorMenu;
        this.vistaGestionEstudiantes = new VistaGestionEstudiantes(this);
        cargarEstudiantes();
    }

    public DefaultTableModel cargarModeloEstudiantes() throws SQLException {
        DefaultTableModel modeloLibros = vistaGestionEstudiantes.getModeloTabla();
        modeloLibros.setRowCount(0);

        ArrayList<Estudiante> estudiantes = (ArrayList<Estudiante>) new EstudianteDAO().listarEstudiantesContrasena();

        for (Estudiante estudiante : estudiantes) {
            modeloLibros.addRow(new Object[]{estudiante.getId(),
                    estudiante.getNombre(),
                    estudiante.getRut(),
                    estudiante.getCurso(),
                    estudiante.getCorreo(),
                    estudiante.getContraseña()

            });
        }
        return modeloLibros;
    }

    //carga los libros a la tabla
    private void cargarEstudiantes() throws SQLException {
        vistaGestionEstudiantes.getTabla().setModel(cargarModeloEstudiantes());
    }



    public void verPrestamos(int filaSeleccionada) {
        if (filaSeleccionada < 0) {
            vistaGestionEstudiantes.mostrarError("Selecciona un estudiante de la tabla");
            return;
        }
        int idEstudiante = (int) vistaGestionEstudiantes.getModeloTabla().getValueAt(filaSeleccionada, 0);
        new VistaHistorialPrestamos(idEstudiante);
        //TODO: dentro de VistaHistorialPrestamos.getModeloTabla() cargar los prestamos de ese estudiante con el DAO
    }

    public void editarEstudiante(int filaSeleccionada) {
        if (filaSeleccionada < 0) {
            vistaGestionEstudiantes.mostrarError("Selecciona un estudiante de la tabla");
            return;
        }
        Object[] datosEstudiante = obtenerDatosFila(filaSeleccionada);
        new VistaFormularioEstudiante(this, datosEstudiante);
    }

    //TODO: tomar el id de la fila seleccionada (columna 0) y eliminar el estudiante con el DAO, luego recargar la tabla
    public void eliminarEstudiante(int filaSeleccionada) throws SQLException {
        if (filaSeleccionada < 0) {
            vistaGestionEstudiantes.mostrarError("Selecciona un estudiante de la tabla");
            return;
        }
        cargarEstudiantes();
    }

    //llamado desde VistaFormularioEstudiante al apretar Guardar (actualizar ya deberia estar hecho con el DAO antes de llegar aqui)
    public void guardarEstudiante() throws SQLException {
        cargarEstudiantes();
    }

    private Object[] obtenerDatosFila(int fila) {
        int columnas = vistaGestionEstudiantes.getModeloTabla().getColumnCount();
        Object[] datos = new Object[columnas];
        for (int i = 0; i < columnas; i++) {
            datos[i] = vistaGestionEstudiantes.getModeloTabla().getValueAt(fila, i);
        }
        return datos;
    }

    public void volver() {
        vistaGestionEstudiantes.dispose();
        controladorMenu.mostrarVistaMenu();
    }
}
