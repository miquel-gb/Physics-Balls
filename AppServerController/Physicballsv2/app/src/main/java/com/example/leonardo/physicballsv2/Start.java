package com.example.leonardo.physicballsv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //ArrayList<Pantalla> pantallas = menu.pantallas;
        //HttpRequester.sendConf(pantallas);
    }

    /**
     *
     * @param view
     *
     * Arranca el servidor para que los modulos puedan conectarse
     */
    public void start(View view){
        HttpRequester.scc.startServer();
    }
    /**
     *
     * @param view
     *
     * Para el servidor para que los modulos puedan conectarse
     */
    public void stop(View view){
        HttpRequester.scc.stopServer();
    }
}

