package com.example.leonardo.physicballsv2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Preconfiguration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //public static ArrayList<Object> escenarios;
    /**
    Arraylist de escenarios de la aplicacion para el spinner
     */
    public ArrayList<String> scenarios;
    /**
     Indice del escenario seleccionado
     */
    public int scenarioselected =  1;
    /**
     Array de string de escenarios que han llegaado de respuesta al servidor
     */
    public static String [] scenario;
    /**
     Numero de pantallas que se dispone
     */
    public int numpantallas;
    /**
     Anchura de la dimension
     */
    public int px;
    /**
     Altura de la dimension
     */
    public int py;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preconfiguration);
        processScenarioData();
        Spinner spinner = (Spinner) findViewById(R.id.escenariospinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                R.layout.item_escenario,
                scenarios);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        NumberPicker np = (NumberPicker) findViewById(R.id.np);
        setNumberPickerTextColor(np, Color.CYAN);
        np.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(999);
        if(menu.init){
            np.setValue(menu.co.getPantallas().size());
            numpantallas = menu.co.getPantallas().size();
        }

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                 numpantallas = newVal;
            }
        });
        NumberPicker np2 = (NumberPicker) findViewById(R.id.np2);
        setNumberPickerTextColor(np2, Color.CYAN);
        np2.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np2.setMaxValue(999);

        if(menu.init){
            np2.setValue(menu.co.getPlantillax());
            px = menu.co.getPlantillax();
        }
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np2.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                px = newVal;
            }
        });
        NumberPicker np3 = (NumberPicker) findViewById(R.id.np3);
        setNumberPickerTextColor(np3, Color.CYAN);
        np3.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np3.setMaxValue(999);
        Log.e("Valueinit","entering");
        if(menu.init){
            Log.e("Valueinit","true");
            np3.setValue(menu.co.getPlantillay());
            py = menu.co.getPlantillay();
        }
        //Gets whether the selector wheel wraps when reaching the min/max value.
        np3.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                py = newVal;
            }
        });
    }

    /**
     Recoje la informacion obtenida por el servidor y la trata: si hay escenarios,
     se vuelca la informacion al arraylist, sino se queda un escenario default.
     */
    private void processScenarioData(){
        //HttpRequester.scc.requestScenario();
        scenarios = new ArrayList<String>();

        if(scenario != null){
            for(String scene : scenario){
                //Escenario aux = (Escenario) scene;
                scenarios.add(scene);
            }
        }else{
            scenarios.add("Default");
        }


        /*
        //DUMMY
        for(int i=0;i<5;i++){
            scenarios.add("Escenario"+i);
        }
        */
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.scenarioselected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
    Envia una peticion al servidor de openserver con las dimensiones,pantallas y escenario
    si el producto de altura y anchura de la dimension es mayor que el numero de pantallas.
    Si se envia la peticion procede con la actividad de configuracion para configurar cada
    pantalla.
     */
    public void goConf(View view){
        /*
        for(Object n :escenarios){
            Escenario na = (Escenario)n;
            if(scenarios.get(this.scenarioselected).equalsIgnoreCase(((Escenario) n).getTitulo())){
                menu.co.setScenario(na);
                Log.e("Escenario",na.getTitulo());
            }
        }
        */
        //Dummy

        Escenario e = new Escenario(scenarios.get(this.scenarioselected));
        menu.co.setScenario(e);
        Log.e("Escenario",scenarios.get(this.scenarioselected));
        if(px*py<numpantallas){
            Toast.makeText(this,"La preconfiguración no es correcta ",Toast.LENGTH_LONG).show();
        }else {

            boolean same = false;
            if(menu.init){
                Log.e("goconf","entered init true");
                Log.e("goconf","pantallas (numpantallas - menucco)" + numpantallas + " - " + menu.co.getPantallas().size());
                if(numpantallas== menu.co.getPantallas().size()){
                    Log.e("goconf","entered numpantallas truue" + numpantallas + " - " + menu.co.getPantallas().size());
                    if(this.px == menu.co.getPlantillax()){
                        Log.e("goconf","entered x true");
                       if(this.py == menu.co.getPlantillay()){
                           Log.e("goconf","entered y true");
                            Configuration.modifier = true;
                           //menu.numpant = this.numpantallas;
                           menu.co.setPlantillax(px);
                           menu.co.setPlantillay(py);
                           Configuration.numpantallas = this.numpantallas;
                           Log.e("socket open?","asd "+ServerControllerSocket.sc.isConnected());
                           //ServerControllerSocket.openServer(px,py);
                           Log.e("numpantallas", this.numpantallas + "");
                           same = true;
                           Configuration.modifier = true;
                           Intent i = new Intent(Preconfiguration.this, Configuration.class);
                           startActivity(i);
                       }
                   }
                }
                if(!same) {
                    Log.e("goconf","entered not same");
                    //menu.numpant = this.numpantallas;
                    menu.co.setPlantillax(px);
                    menu.co.setPlantillay(py);
                    Configuration.numpantallas = this.numpantallas;
                    Log.e("socket open?", "asd " + ServerControllerSocket.sc.isConnected());
                    Configuration.modifier = false;
                    ServerControllerSocket.openServer(px, py,scenarios.get(this.scenarioselected));
                    Log.e("numpantallas", this.numpantallas + "");
                    Intent i = new Intent(Preconfiguration.this, Configuration.class);
                    startActivity(i);
                }
            }else{
                Log.e("goconf","entered init false");
                if(!same) {
                    Log.e("goconf","entered not same");
                    //menu.numpant = this.numpantallas;
                    menu.co.setPlantillax(px);
                    menu.co.setPlantillay(py);
                    Configuration.numpantallas = this.numpantallas;
                    Log.e("socket open?", "asd " + ServerControllerSocket.sc.isConnected());
                    Configuration.modifier = false;
                    ServerControllerSocket.openServer(px, py,scenarios.get(this.scenarioselected));
                    Log.e("numpantallas", this.numpantallas + "");
                    Intent i = new Intent(Preconfiguration.this, Configuration.class);
                    startActivity(i);
                }
            }

        }
    }

    /**
     *
     * @param numberPicker
     * @param color
     * @return
     *
     * Realiza un set color de los numeros del numberpicker.
     */
    public boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalAccessException e){
                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
        return false;
    }
}
