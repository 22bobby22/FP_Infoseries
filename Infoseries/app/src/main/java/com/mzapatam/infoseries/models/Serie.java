package com.mzapatam.infoseries.models;

import java.io.Serializable;

public class Serie extends Contenido implements Serializable {
    private String productora;
    private int temporadas;
    private String categorias;

    public String getProductora() {
        return productora;
    }

    public void setProductora(String productora) {
        this.productora = productora;
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    @Override
    public String toString() {
        return "[" + productora + ", " + getNombre() + ", " + getImagen() + ", " + getDescripcion() + ", " + temporadas + ", " + getFecha() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;

        Serie serie = (Serie) o;

        return (this.getNombre().equals(serie.getNombre())
                && this.getImagen().equals(serie.getImagen())
                && this.temporadas == serie.getTemporadas()
                && this.getDescripcion().equals(serie.getDescripcion())
                && this.getFecha() == serie.getFecha()
                && this.productora.equals(serie.getProductora())
                && this.categorias.equals((serie.getCategorias())));
    }
}
