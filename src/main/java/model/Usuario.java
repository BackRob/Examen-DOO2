package model;

public abstract class Usuario {
    private int id;
    private String nombre;
    private String rut;
    private String correo;
    private String contraseña;


    public Usuario(int id, String nombre, String rut, String correo, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
        this.contraseña = contrasenia;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getRut() {return rut;}
    public void setRut(String rut) {this.rut = rut;}
    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}
    public String getContraseña() {return contraseña;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}

    //Poliformismo
    public abstract String getRol();
    public abstract boolean esBiblotecario();

    //Creacion de usuario delegar responsabilidad.
    public static Usuario crearUsuario(String rol,int id, String nombre, String rut, String correo, String contrasenia){
        if (rol==null){return null;}
        return switch (rol.toUpperCase()){
            case "BIBLIOTECARIO"-> new Bibliotecario(id, nombre, rut, correo, contrasenia);
            case "ESTUDIANTE"-> new Estudiante(id, nombre, rut, correo, contrasenia);
            default -> null;
        };

    }


    @Override
    public String toString() {
        return "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", rut='" + rut + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }


}

