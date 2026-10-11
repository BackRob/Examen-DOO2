package model;

public class Sesion {
    private static Usuario usuario;
    private static Sesion intancia;

    private Sesion(){
        usuario = null;
    }



    //patron singleton
    public static Sesion getIntancia() {
        if(intancia == null){
            intancia = new Sesion();
        }
        return intancia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if(usuario != null){
            Sesion.usuario = usuario;
        }else{
            System.err.println("No se puede establecer el usuario");
        }
    }

    //para cerrar la sesion
    public void cerrarSesion(){this.usuario = null;}

}
