package com.mzapatam.infoseries.models;

import java.util.ArrayList;
import java.util.List;

public class Pelicula {
    private String productora;
    private String nombre;
    private String imagen;
    private String descripcion;
    private String duracion;
    private long fecha;
    private String categorias;

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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        return "[" + productora + ", " + nombre + ", " + imagen + ", " + descripcion + ", " + duracion + ", " + fecha + "]";
    }
}
