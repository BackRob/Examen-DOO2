package dao;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {


    //crear usuario
    public void create(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre,rut,correo,contraseña,rol) VALUES (?,?,?,?,?)";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet claves = null;

        try {

            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getRut());
            sentencia.setString(3, usuario.getCorreo());
            sentencia.setString(4, usuario.getContraseña());
            sentencia.setString(5, usuario.getRol());
            sentencia.executeUpdate();

            //id AUTO_INCREMENT
            claves = sentencia.getGeneratedKeys();
            if (claves.next()) {
                usuario.setId(claves.getInt(1));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el pedido: " + e.getMessage(), e);

        } finally {
            ConexionBD.cerrar(sentencia, claves);
        }
    }


    //listartodos
    public List<Usuario> listar() throws SQLException {
        String sql = "SELECT * FROM usuarios";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            resultado = sentencia.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();

            while (resultado.next()) {
                int id = resultado.getInt(1);
                String nombre = resultado.getString(2);
                String rut = resultado.getString(3);
                String correo = resultado.getString(4);
                String contraseña =  resultado.getString(5);
                String rol = resultado.getString(6);
                Usuario usuarioAux = Usuario.crearUsuario(rol,id,nombre,rut,correo,contraseña);
                usuarios.add(usuarioAux);
            }
            if (usuarios.isEmpty()) {return null; }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            ConexionBD.cerrar(sentencia, resultado);
        }

    }


    //Leer un usuario en particular
    public Usuario autenticar(String rut, String contraseña) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE rut=? AND contraseña=?";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {

            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, rut);
            sentencia.setString(2, contraseña);
            sentencia.executeQuery();

            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                Usuario usuario =  Usuario.crearUsuario(
                        resultado.getString("rol"),
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("rut"),
                        resultado.getString("correo"),
                        resultado.getString("contraseña")
                );

                if (usuario != null) {
                    return usuario;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Credenciales Incorrectas: " + e.getMessage(), e);

        } finally {
            ConexionBD.cerrar(sentencia, resultado);
        }


        return null;
    }


}
