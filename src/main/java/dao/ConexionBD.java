package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConexionBD {

    private static ConexionBD instancia;
    private static final String ARCHIVO = "db.properties";
    private static Connection conexion;

    //Constructor
    private ConexionBD() throws SQLException {
        conectar();
    }

    //Singleton
    public static ConexionBD getIntancia() throws SQLException {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    //obtener la conexion
    public static synchronized Connection getConexion() throws SQLException {
        if(conexion == null || !conexion.isValid(2)){
            conectar();
        }
        return conexion;
    }



    //Connectar
    private static void conectar() throws SQLException {
        //Lectura de archivo con credenciales
        Properties datos = new Properties();
        try (FileInputStream archivo = new FileInputStream(ARCHIVO)) {
            datos.load(archivo);
        } catch (IOException e) {
            throw new SQLException("No se encontro el archivo " + ARCHIVO + " (copia db.properties.example y pon tus datos)");
        }

        //Conexion
        String url = datos.getProperty("db.url");
        String user = datos.getProperty("db.user");
        String password = datos.getProperty("db.password");
        conexion = DriverManager.getConnection(url, user, password);
    }

    //cierra lo que se haya abierto, se llama desde el finally de los DAO
    public static void cerrar(Statement sentencia) {
        try {
            if (sentencia != null) sentencia.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }

    public static void cerrar(Statement sentencia, ResultSet resultado) {
        try {
            if (resultado != null) resultado.close();
            if (sentencia != null) sentencia.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }

    public static void cerrar() {
        try {
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }


}
