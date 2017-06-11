package com.example.leonardo.physicballsv2;

/**
 * Created by Leonardo on 19/04/2017.
 */

public class Pantalla {
    /**
    Identificador de la pantalla
     */
    public int idpantalla;
    /**
     Posicion x de la pantalla
     */
    public int posicionx;
    /**
     Posicion y de la pantalla
     */
    public int posiciony;

    /**
     *
     * @param idpantalla
     *
     * Inicializa una pantalla con un id por parametro y unas coordenadas default
     */
    public Pantalla(int idpantalla){
        this.idpantalla = idpantalla;
        setPosicionx(999);
        setPosiciony(999);
    }

    /**
     *
     * @return
     *
     * Devuelve el id de la pantalla
     */
    public int getIdpantalla() {
        return idpantalla;
    }

    /**
     *
     * @param idpantalla
     *
     * Fija el id de la pantalla
     */
    public void setIdpantalla(int idpantalla) {
        this.idpantalla = idpantalla;
    }

    /**
     *
     * @return
     *
     * Devuelve la coordenada x de la pantalla
     */
    public int getPosicionx() {
        return posicionx;
    }

    /**
     *
     * @param posicionx
     *
     * Fija la coordenada x de la pantalla
     */
    public void setPosicionx(int posicionx) {
        this.posicionx = posicionx;
    }

    /**
     *
     * @return
     *
     * Devuelve la coordanada y de la pantalla
     */
    public int getPosiciony() {
        return posiciony;
    }

    /**
     *
     * @param posiciony
     *
     * Fija la coordenada y de la pantalla
     */
    public void setPosiciony(int posiciony) {
        this.posiciony = posiciony;
    }

}

