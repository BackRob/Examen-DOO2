package dao;

import model.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO{

    public void crearEstudiante(Estudiante estudiante) throws SQLException{
        String sql = "insert into estudiantes (nombre,rut,curso,correo) values (?,?,?,?)";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionBD.getConexion();
            sentencia=conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, estudiante.getNombre());
            sentencia.setString(2, estudiante.getRut());
            sentencia.setString(3,estudiante.getCurso());
            sentencia.setString(4,estudiante.getCorreo());
            sentencia.execute();
            resultado = sentencia.getGeneratedKeys();
            if (resultado.next()) {
                estudiante.setId(resultado.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConexionBD.cerrar(sentencia,resultado);
        }


    }

    public List<Estudiante> listarEstudiantes(){
        List<Estudiante> estudiantes=new ArrayList<>();
        String sql = "select * from estudiantes";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            sentencia.executeQuery();
            resultado = sentencia.getResultSet();
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String rut = resultado.getString("rut");
                String curso = resultado.getString("curso");
                String correo = resultado.getString("correo");
                estudiantes.add(new Estudiante(id, nombre, rut,curso,correo,null));
            }
            return estudiantes;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConexionBD.cerrar(sentencia,resultado);
        }
    }


    public List<Estudiante> listarEstudiantesContrasena(){
        List<Estudiante> estudiantes=new ArrayList<>();
        String sql = "select es.*,  usu.contraseña " +
                "from estudiantes es "+
                "LEFT JOIN usuarios usu on es.rut=usu.rut";
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.getConexion();
            sentencia = conexion.prepareStatement(sql);
            sentencia.executeQuery();

            resultado = sentencia.getResultSet();
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre1 = resultado.getString("nombre");
                String rut1 = resultado.getString("rut");
                String curso1 = resultado.getString("curso");
                String correo1 = resultado.getString("correo");
                String contrasenia1 = resultado.getString("contraseña");
                estudiantes.add(new Estudiante(id, nombre1, rut1,curso1,correo1,contrasenia1));

            }
            return estudiantes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConexionBD.cerrar(sentencia,resultado);
        }

    }


}
