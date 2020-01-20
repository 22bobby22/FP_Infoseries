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
}
