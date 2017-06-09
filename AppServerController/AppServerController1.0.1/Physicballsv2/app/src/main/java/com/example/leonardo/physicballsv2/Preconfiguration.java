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

    public static ArrayList<Object> escenarios;
    public ArrayList<String> scenarios;
    public int scenarioselected =  1;
    public int numpantallas;
    public int px;
    public int py;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preconfiguration);
        requestData();
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

    private void requestData(){
        //HttpRequester.scc.requestScenario();
        scenarios = new ArrayList<String>();
        /*
        for(Object scene : escenarios){
            Escenario aux = (Escenario) scene;
            scenarios.add(aux.getTitulo());
        }
        */
        //DUMMY
        for(int i=0;i<5;i++){
            scenarios.add("Escenario"+i);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.scenarioselected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

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
            menu.numpant = this.numpantallas;
            menu.co.setPlantillax(px);
            menu.co.setPlantillay(py);
            Configuration.numpantallas = this.numpantallas;
            Log.e("socket open?","asd "+ServerControllerSocket.sc.isConnected());
            ServerControllerSocket.openServer(px,py);
            Log.e("numpantallas", this.numpantallas + "");
            Intent i = new Intent(Preconfiguration.this, Configuration.class);
            startActivity(i);
        }
    }

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
