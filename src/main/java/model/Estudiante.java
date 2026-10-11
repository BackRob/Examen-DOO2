package model;

public class Estudiante extends Usuario {
    private String curso;

    public Estudiante(int id, String nombre, String rut, String curso, String correo,String contrasenia) {
        super(id, nombre, rut, correo, contrasenia);
        this.curso = curso;

    }
    public Estudiante(int id, String nombre, String rut, String correo, String contrasenia) {
        super(id, nombre, rut, correo, contrasenia);
        this.curso = null;
    }


    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String getRol() {return "estudiante";}

    @Override
    public boolean esBiblotecario() {return false;}

    @Override
    public String toString() {
        return "Estudiante{} " + super.toString();
    }



}
