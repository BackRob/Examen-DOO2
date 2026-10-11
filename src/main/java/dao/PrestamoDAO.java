package dao;

import model.Prestamo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    //registra el prestamo en la tabla prestamos (fecha_prestamo = hoy, fecha_devolucion = hoy + DIAS_PRESTAMO)
    public void crearPrestamo(Prestamo prestamo) throws SQLException {
        String sql = "insert into prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) values (?,?,?,?,?)";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sentencia.setInt(1, prestamo.getIdEstudiante());
            sentencia.setInt(2, prestamo.getIdLibro());
            sentencia.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            sentencia.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            sentencia.setBoolean(5, prestamo.isDevuelto());
            sentencia.execute();
            resultado = sentencia.getGeneratedKeys();
            if (resultado.next()) {
                prestamo.setId(resultado.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexionBD.cerrar(sentencia, resultado);
        }
    }

    //lista todos los prestamos de todos los estudiantes (para la vista del bibliotecario)
    public List<Prestamo> listarTodos() throws SQLException {
        String sql = "select p.id, p.id_estudiante, es.nombre, p.id_libro, li.titulo, p.fecha_prestamo, p.fecha_devolucion, p.devuelto " +
                "from prestamos p " +
                "join estudiantes es on p.id_estudiante = es.id " +
                "join libros li on p.id_libro = li.id " +
                "order by p.fecha_prestamo desc";
        return listarConSql(sql);
    }

    //lista los prestamos de un estudiante en particular (Mis Prestamos / Historial)
    public List<Prestamo> listarPorEstudiante(int idEstudiante) throws SQLException {
        String sql = "select p.id, p.id_estudiante, es.nombre, p.id_libro, li.titulo, p.fecha_prestamo, p.fecha_devolucion, p.devuelto " +
                "from prestamos p " +
                "join estudiantes es on p.id_estudiante = es.id " +
                "join libros li on p.id_libro = li.id " +
                "where p.id_estudiante = ? " +
                "order by p.fecha_prestamo desc";
        return listarConSql(sql, idEstudiante);
    }

    //lista solo los prestamos activos (no devueltos) de un estudiante (para Devolver Libro)
    public List<Prestamo> listarActivosPorEstudiante(int idEstudiante) throws SQLException {
        String sql = "select p.id, p.id_estudiante, es.nombre, p.id_libro, li.titulo, p.fecha_prestamo, p.fecha_devolucion, p.devuelto " +
                "from prestamos p " +
                "join estudiantes es on p.id_estudiante = es.id " +
                "join libros li on p.id_libro = li.id " +
                "where p.id_estudiante = ? and p.devuelto = false " +
                "order by p.fecha_prestamo desc";
        return listarConSql(sql, idEstudiante);
    }

    private List<Prestamo> listarConSql(String sql, Integer... parametros) throws SQLException {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                sentencia.setInt(i + 1, parametros[i]);
            }
            resultado = sentencia.executeQuery();
            List<Prestamo> prestamos = new ArrayList<>();
            while (resultado.next()) {
                prestamos.add(new Prestamo(
                        resultado.getInt("id"),
                        resultado.getInt("id_estudiante"),
                        resultado.getString("nombre"),
                        resultado.getInt("id_libro"),
                        resultado.getString("titulo"),
                        resultado.getDate("fecha_prestamo").toLocalDate(),
                        resultado.getDate("fecha_devolucion").toLocalDate(),
                        resultado.getBoolean("devuelto")
                ));
            }
            return prestamos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexionBD.cerrar(sentencia, resultado);
        }
    }

    //marca el prestamo como devuelto
    public void marcarDevuelto(int idPrestamo) throws SQLException {
        String sql = "update prestamos set devuelto = true where id = ?";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idPrestamo);
            sentencia.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexionBD.cerrar(sentencia);
        }
    }

    //devuelve el id del libro prestado
    public int idLibroPrestamo(int idPrestamo) throws SQLException {
        String sql = "SELECT id_libro from prestamos where id = ?";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idPrestamo);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                return resultado.getInt("id_libro");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConexionBD.cerrar(sentencia, resultado);
        }
        return -1;
    }


    //reporte "Libros mas prestados": Object[]{titulo, cantidadDePrestamos}
    public List<Object[]> contarPrestamosPorLibro() throws SQLException {
        String sql = "select li.titulo, count(*) as cantidad " +
                "from prestamos p join libros li on p.id_libro = li.id " +
                "group by li.id, li.titulo " +
                "order by cantidad desc";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();
            List<Object[]> filas = new ArrayList<>();
            while (resultado.next()) {
                filas.add(new Object[]{resultado.getString("titulo"), resultado.getInt("cantidad")});
            }
            return filas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexionBD.cerrar(sentencia, resultado);
        }
    }

    //reporte "Estudiantes con prestamos atrasados": Object[]{nombreEstudiante, titulo, fechaDevolucion} mejor que mapear todo
    public List<Object[]> listarAtrasados() throws SQLException {
        String sql = "select es.nombre, li.titulo, p.fecha_devolucion " +
                "from prestamos p " +
                "join estudiantes es on p.id_estudiante = es.id " +
                "join libros li on p.id_libro = li.id " +
                "where p.devuelto = false and p.fecha_devolucion < curdate() " +
                "order by p.fecha_devolucion";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();
            List<Object[]> filas = new ArrayList<>();
            while (resultado.next()) {
                filas.add(new Object[]{resultado.getString("nombre"), resultado.getString("titulo"), resultado.getDate("fecha_devolucion").toLocalDate()});
            }
            return filas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexionBD.cerrar(sentencia, resultado);
        }
    }
}
