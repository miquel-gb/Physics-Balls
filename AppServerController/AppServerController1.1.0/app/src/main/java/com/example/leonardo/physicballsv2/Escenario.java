package com.example.leonardo.physicballsv2;

/**
 * Created by Leonardo on 20/04/2017.
 */

public class Escenario {
    String titulo;
    String info;

    public Escenario(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
