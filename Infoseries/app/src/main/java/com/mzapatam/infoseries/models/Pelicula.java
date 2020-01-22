package com.mzapatam.infoseries.models;

import java.io.Serializable;

public class Pelicula extends Contenido implements Serializable {
    private String productora;
    private String duracion;
    private String categorias;

    public String getProductora() {
        return productora;
    }

    public void setProductora(String productora) {
        this.productora = productora;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        return "[" + productora + ", " + getNombre() + ", " + getImagen() + ", " + getDescripcion() + ", " + duracion + ", " + getFecha() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;

        Pelicula pelicula = (Pelicula) o;

        return (this.getNombre().equals(pelicula.getNombre())
                && this.getImagen().equals(pelicula.getImagen())
                && this.duracion.equals(pelicula.getDuracion())
                && this.getDescripcion().equals(pelicula.getDescripcion())
                && this.getFecha() == pelicula.getFecha()
                && this.productora.equals(pelicula.getProductora())
                && this.categorias.equals((pelicula.getCategorias())));
    }
}
