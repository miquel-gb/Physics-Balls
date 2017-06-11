package com.example.leonardo.physicballsv2;

/**
 * Created by Leonardo on 20/04/2017.
 */

public class Escenario {
    /**
     * Titulo del escenario
     */
    String titulo;
    /**
     * Descripcion del escenario
     */
    String info;

    /**
     *
     * @param titulo
     *
     * Constructor del escenario
     */
    public Escenario(String titulo) {
        this.titulo = titulo;
    }

    /**
     *
     * @return
     *
     * Devuelve el titulo del escenario
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     *
     * @param titulo
     *
     * Fija el titulo del escenario
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     *
     * @return
     *
     * Devuelve la descripcion del escenario
     */
    public String getInfo() {
        return info;
    }

    /**
     *
     * @param info
     *
     * Fija la descripcion del escenario
     */
    public void setInfo(String info) {
        this.info = info;
    }
}
