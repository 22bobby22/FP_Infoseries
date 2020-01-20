package com.mzapatam.infoseries.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
}
