package model;

import java.time.LocalDate;

public class Prestamo {

    //cantidad de dias que dura un prestamo, se usa para calcular la fecha de devolucion automaticamente
    public static final int DIAS_PRESTAMO = 7;

    private Integer id;
    private int idEstudiante;
    private String nombreEstudiante;
    private int idLibro;
    private String tituloLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    //constructor completo, lo usa el DAO al leer desde la base de datos
    public Prestamo(Integer id, int idEstudiante, String nombreEstudiante, int idLibro, String tituloLibro,
                     LocalDate fechaPrestamo, LocalDate fechaDevolucion, boolean devuelto) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.idLibro = idLibro;
        this.tituloLibro = tituloLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    //constructor simple para registrar un prestamo nuevo: la fecha de hoy y el vencimiento se calculan solos
    public Prestamo(int idEstudiante, int idLibro) {
        this.idEstudiante = idEstudiante;
        this.idLibro = idLibro;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = LocalDate.now().plusDays(DIAS_PRESTAMO);
        this.devuelto = false;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public int getIdEstudiante() {return idEstudiante;}
    public void setIdEstudiante(int idEstudiante) {this.idEstudiante = idEstudiante;}
    public String getNombreEstudiante() {return nombreEstudiante;}
    public void setNombreEstudiante(String nombreEstudiante) {this.nombreEstudiante = nombreEstudiante;}
    public int getIdLibro() {return idLibro;}
    public void setIdLibro(int idLibro) {this.idLibro = idLibro;}
    public String getTituloLibro() {return tituloLibro;}
    public void setTituloLibro(String tituloLibro) {this.tituloLibro = tituloLibro;}
    public LocalDate getFechaPrestamo() {return fechaPrestamo;}
    public void setFechaPrestamo(LocalDate fechaPrestamo) {this.fechaPrestamo = fechaPrestamo;}
    public LocalDate getFechaDevolucion() {return fechaDevolucion;}
    public void setFechaDevolucion(LocalDate fechaDevolucion) {this.fechaDevolucion = fechaDevolucion;}
    public boolean isDevuelto() {return devuelto;}
    public void setDevuelto(boolean devuelto) {this.devuelto = devuelto;}

    //poliformismo simple de estado, usado para pintar la columna "Estado" en las tablas
    public String getEstado() {
        if (devuelto) return "Devuelto";
        if (fechaDevolucion != null && LocalDate.now().isAfter(fechaDevolucion)) return "Atrasado";
        return "En préstamo";
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", idEstudiante=" + idEstudiante +
                ", idLibro=" + idLibro +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                ", devuelto=" + devuelto;
    }
}
