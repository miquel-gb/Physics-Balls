package com.example.leonardo.physicballsv2;

import java.util.ArrayList;

/**
 * Created by Leonardo on 08/05/2017.
 */

public class ConfigurationObject
{
    private Escenario scenario;
    private ArrayList<Pantalla> pantallas;
    private int plantillax;
    private int plantillay;

    public ConfigurationObject(){
    }

    public int getPlantillax() {
        return plantillax;
    }

    public void setPlantillax(int plantillax) {
        this.plantillax = plantillax;
    }

    public int getPlantillay() {
        return plantillay;
    }

    public void setPlantillay(int plantillay) {
        this.plantillay = plantillay;
    }

    public ArrayList<Pantalla> getPantallas() {
        return pantallas;
    }

    public void setPantallas(ArrayList<Pantalla> pantallas) {
        this.pantallas = pantallas;
    }

    public Object getScenario() {
        return scenario;
    }

    public void setScenario(Escenario scenario) {
        this.scenario = scenario;
    }
}
