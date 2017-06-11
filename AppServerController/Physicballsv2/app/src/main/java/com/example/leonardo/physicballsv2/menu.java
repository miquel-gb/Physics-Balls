package com.example.leonardo.physicballsv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /**
    Indica si hay una configuracion valida realizada
     */
    public static boolean init = false;
    /**
    Objeto que contiene la configuracion de las pantallas y el escenario
     */
    public static ConfigurationObject co;
    //public static int numpant = 1;

    //public boolean initregister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!init) {
            co = new ConfigurationObject();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        new Thread(new Runnable() {
            public void run() {
                //wait, put here some sleep
                try {

                    ServerControllerSocket.Register();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                HttpRequester.scc.requestScenario();
                        }
                    });

                }catch(Exception e){
                    Log.e("ERROR", e.toString());

                }
            }}).start();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_init) {
            // Handle the camera action
            if(init){
                checkObject(this.co);
                HttpRequester.scc.sendConf(this.co);
                Intent i =new Intent(menu.this, Start.class);
                startActivity(i);
            }else{
                Toast.makeText(this,"No se ha iniciado una configuración ",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_manage) {
                Intent i =new Intent(menu.this, Preconfiguration.class);
                startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     *
     * @param co
     *
     * Metodo que permite ver la configuracion de las pantallas y el escenario por consola
     */
    public void checkObject(ConfigurationObject co){
        Escenario e =(Escenario) this.co.getScenario();
        Log.e("Titulo escenario: ", e.getTitulo());
//        Log.e("Titulo escenario: ", e.getInfo());
        for(int i = 0; i < co.getPantallas().size() ; i++){
            Pantalla paux = co.getPantallas().get(i);
            Log.e("Pantalla "+i+" x:",paux.getPosicionx()+"");
            Log.e("Pantalla "+i+" y:",paux.getPosiciony()+"");
        }
    }
}
