package util;

import dao.LibroDAO;
import dao.PrestamoDAO;
import dao.UsuarioDAO;
import model.Prestamo;

import java.sql.SQLException;

public class GestorPrestamos {
    private static GestorPrestamos gestorPrestamos;
    private static final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final LibroDAO libroDAO = new LibroDAO();

    //Constructor
    private GestorPrestamos() {


    }

    //singleton
    public static GestorPrestamos getInstance() {
        if (gestorPrestamos== null){
            gestorPrestamos = new GestorPrestamos();
        }
        return gestorPrestamos;
    }

    //registrar prestamo, debe descontar stock, registrar el prestamo nuevo, con estudiante y libro
    public synchronized void  registrarPrestamo(int idEstudiante, int idLibro) throws SQLException {
        prestamoDAO.crearPrestamo(new Prestamo(idEstudiante, idLibro));
        libroDAO.descontarStock(idLibro);

    }

    public synchronized void devolverPrestamo(int idPrestamo) throws SQLException {
        prestamoDAO.marcarDevuelto(idPrestamo);
        libroDAO.sumarStock(prestamoDAO.idLibroPrestamo(idPrestamo));

    }




}
