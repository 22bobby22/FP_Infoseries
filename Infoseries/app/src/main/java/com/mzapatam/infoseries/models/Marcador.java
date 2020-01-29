package com.mzapatam.infoseries.models;

public class Marcador {
    private String nombre;
    private String tipo;


    public Marcador(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
