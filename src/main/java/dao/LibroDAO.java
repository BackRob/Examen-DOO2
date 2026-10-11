package dao;

import model.Categoria;
import model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibroDAO {


    public List<Libro> listarLibros() throws SQLException {
        Map<Integer, Categoria> categoriaMap = armarCategoriaMap();

        String sql = "select * from libros";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();
            List<Libro> libros = new ArrayList<>();

            while (resultado.next()) {
                int id = resultado.getInt(1);
                String titulo = resultado.getString(2);
                String autor = resultado.getString(3);
                String isbn = resultado.getString(4);
                String editorial = resultado.getString(5);
                int stock = resultado.getInt(6);
                int idCategoria = resultado.getInt(7);
                Categoria categoria = categoriaMap.get(idCategoria);
                Libro libroAux = new Libro(id, titulo, autor, isbn, editorial, stock, categoria);
                libros.add(libroAux);
            }
            if (libros.isEmpty()) {return null;}
            return libros;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexionBD.cerrar(sentencia, resultado);
        }
    }

    public void crearLibro(Libro libro) throws SQLException {
        String sql = "insert into libros (titulo,autor,isbn,editorial,stock,id_categoria) values (?,?,?,?,?,?)";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, libro.getTitulo());
            sentencia.setString(2, libro.getAutor());
            sentencia.setString(3, libro.getIsbn());
            sentencia.setString(4, libro.getEditorial());
            sentencia.setInt(5, libro.getStock());
            sentencia.setInt(6, libro.getCategoria().getId());
            sentencia.execute();
            resultado = sentencia.getGeneratedKeys();
            if (resultado.next()) {
                int id = resultado.getInt(1);
                libro.setIdLibro(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexionBD.cerrar(sentencia, resultado);
        }
    }

    public void editarLibro(Libro libro) throws SQLException {
        String sql = "update libros set titulo=?, autor=?, isbn=?, editorial=?, stock=?, id_categoria=? where id=?";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, libro.getTitulo());
            sentencia.setString(2, libro.getAutor());
            sentencia.setString(3, libro.getIsbn());
            sentencia.setString(4, libro.getEditorial());
            sentencia.setInt(5, libro.getStock());
            sentencia.setInt(6, libro.getCategoria().getId());
            sentencia.setInt(7, libro.getIdLibro());
            int fila = sentencia.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexionBD.cerrar(sentencia);
        }
    }

    public void eliminarLibro(int id) throws SQLException {
        String sql = "delete from libros where id = ?";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            sentencia.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexionBD.cerrar(sentencia);
        }
    }


    private Map<Integer, Categoria> armarCategoriaMap() throws SQLException {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Map<Integer, Categoria> categoriaMap = new HashMap<>();
        for (Categoria categoria : categoriaDAO.listarCategorias()) {
            categoriaMap.put(categoria.getId(), categoria);
        }
        return categoriaMap;
    }

}
