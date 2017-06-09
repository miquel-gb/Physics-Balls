package com.example.leonardo.physicballsv2;

/**
 * Created by Leonardo on 19/04/2017.
 */

public class Pantalla {
    public int idpantalla;
    public int posicionx;
    public int posiciony;

    public Pantalla(int idpantalla){
        this.idpantalla = idpantalla;
        setPosicionx(999);
        setPosiciony(999);
    }

    public int getIdpantalla() {
        return idpantalla;
    }

    public void setIdpantalla(int idpantalla) {
        this.idpantalla = idpantalla;
    }

    public int getPosicionx() {
        return posicionx;
    }

    public void setPosicionx(int posicionx) {
        this.posicionx = posicionx;
    }

    public int getPosiciony() {
        return posiciony;
    }

    public void setPosiciony(int posiciony) {
        this.posiciony = posiciony;
    }

}

