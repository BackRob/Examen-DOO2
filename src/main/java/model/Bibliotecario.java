package model;

public class Bibliotecario extends Usuario {

    public Bibliotecario(int id, String nombre, String rut, String correo, String contrasenia) {
        super(id, nombre, rut, correo, contrasenia);
    }


    @Override
    public String getRol(){return "bibliotecario";}

    @Override
    public boolean esBiblotecario() {return true;}

    @Override
    public String toString() {
        return "Bibliotecario{} " + super.toString();
    }

}
