package com.mzapatam.infoseries.models;

import java.io.Serializable;
import java.util.Date;

public class Productora extends Contenido implements Serializable {
    private int producciones;

    public int getProducciones() {
        return producciones;
    }

    public void setProducciones(int producciones) {
        this.producciones = producciones;
    }

    @Override
    public String toString() {
        return "[" + getNombre() + ", " + getImagen() + ", " + getDescripcion() + ", " + producciones + ", " + getFecha() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;

       Productora productora = (Productora) o;

        return (this.getNombre().equals(productora.getNombre())
                && this.getImagen().equals(productora.getImagen())
                && this.producciones == productora.getProducciones()
                && this.getDescripcion().equals(productora.getDescripcion())
                && this.getFecha() == productora.getFecha());
    }
}
