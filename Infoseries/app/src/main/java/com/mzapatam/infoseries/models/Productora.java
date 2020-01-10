package com.mzapatam.infoseries.models;

import java.util.Date;

public class Productora {
    private String nombre;
    private String imagen;
    private String descripcion;
    private int producciones;
    private long fecha;

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

    public int getProducciones() {
        return producciones;
    }

    public void setProducciones(int producciones) {
        this.producciones = producciones;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "[" + nombre + ", " + imagen + ", " + descripcion + ", " + producciones + ", " + "fecha" + "]";
    }
}
