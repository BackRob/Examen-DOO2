package dao;

import model.Categoria;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listarCategorias()throws SQLException {
        String sql = "select * from categorias";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();
            List<Categoria> categorias = new ArrayList<>();

            while (resultado.next()) {
                int id = resultado.getInt(1);
                String nombre = resultado.getString(2);
                Categoria categoriasAux = new Categoria(id,nombre);
                categorias.add(categoriasAux);
            }
            if (categorias.isEmpty()) {return null; }
            return categorias;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            ConexionBD.cerrar(sentencia, resultado);
        }
    }

    public void crearCategoria(String nombre)throws SQLException {
        String sql = "insert into categorias (nombre) values (?)";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, nombre);
            sentencia.execute();
            resultado = sentencia.getGeneratedKeys();
            if (resultado.next()) {
                int id = resultado.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConexionBD.cerrar(sentencia,resultado);
        }
    }

    public void editarCategoria(int id, String nombre)throws SQLException {
        String sql = "update categorias set nombre = ? where id = ?";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            sentencia.setInt(2, id);
            sentencia.execute();
            resultado = sentencia.getGeneratedKeys();
            if (resultado.next()) {
                //update correcto
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConexionBD.cerrar(sentencia,resultado);
        }
    }

    public void eliminarCategoria(int id)throws SQLException {
        String sql = "delete from categorias where id = ?";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            sentencia.execute();
            resultado = sentencia.getGeneratedKeys();
            if (resultado.next()) {
                //delete correcto
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            ConexionBD.cerrar(sentencia,resultado);
        }

    }



}
