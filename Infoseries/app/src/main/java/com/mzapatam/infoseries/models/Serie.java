package com.mzapatam.infoseries.models;

public class Serie {
    private String productora;
    private String nombre;
    private String imagen;
    private String descripcion;
    private int temporadas;
    private long fecha;

    public String getProductora() {
        return productora;
    }

    public void setProductora(String productora) {
        this.productora = productora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setDuracion(int temporadas) {
        this.temporadas = temporadas;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "[" + productora + ", " + nombre + ", " + imagen + ", " + descripcion + ", " + temporadas + ", " + fecha + "]";
    }
}
