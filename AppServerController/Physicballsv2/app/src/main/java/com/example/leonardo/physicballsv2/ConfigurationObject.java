package com.example.leonardo.physicballsv2;

import java.util.ArrayList;

/**
 * Created by Leonardo on 08/05/2017.
 */

public class ConfigurationObject
{
    /**
     * Escenario de la configuracion
     */
    private Escenario scenario;
    /**
     * Pantallas de la configuracion
     */
    private ArrayList<Pantalla> pantallas;
    /**
     * anchura de la dimension
     */
    private int plantillax;
    /**
     * altura de la dimension
     */
    private int plantillay;

    /**
     * Constructor de la configuracion
     */
    public ConfigurationObject(){
    }

    /**
     *
     * @return
     *
     * Devuelve la anchura de la dimension
     */
    public int getPlantillax() {
        return plantillax;
    }

    /**
     *
     * @param plantillax
     *
     * Fija la anchura de la dimension
     */
    public void setPlantillax(int plantillax) {
        this.plantillax = plantillax;
    }

    /**
     *
     * @return
     *
     * Devuelve la anchura de la dimension
     */
    public int getPlantillay() {
        return plantillay;
    }

    /**
     *
     * @param plantillay
     *
     * Fija la anchura de la dimension
     */
    public void setPlantillay(int plantillay) {
        this.plantillay = plantillay;
    }

    /**
     *
     * @return
     *
     * Devuelve las pantallas de la configuracion
     */
    public ArrayList<Pantalla> getPantallas() {
        return pantallas;
    }

    /**
     *
     * @param pantallas
     *
     * Fija las pantallas de la configuracion
     */
    public void setPantallas(ArrayList<Pantalla> pantallas) {
        this.pantallas = pantallas;
    }

    /**
     *
     * @return
     *
     * Devuelve el escenario de la configuracion
     */
    public Object getScenario() {
        return scenario;
    }

    /**
     *
     * @param scenario
     *
     * Fija el escenario de la configuracion
     */
    public void setScenario(Escenario scenario) {
        this.scenario = scenario;
    }
}
