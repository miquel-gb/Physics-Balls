package com.example.leonardo.physicballsv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Configuration extends AppCompatActivity  {

    public static int numpantallas;
    public ArrayList<Pantalla> pantallas;
    public int auxid=0;
    public boolean tipo=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        //this.numpantallas = menu.numpant;
        if(menu.init){
            pantallas=menu.pantallas;
        } else {
            pantallas = new ArrayList<Pantalla>();
        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.screenlist);
        for(int n= 0;n<numpantallas;n++){
            Pantalla p = new Pantalla(n);
            pantallas.add(p);
            Button button = new Button(this);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button b = (Button)view;
                    String buttonText = b.getText().toString();
                    int index = Integer.parseInt(buttonText);
                    Pantalla paux = pantallas.get(index);
                    //FOCUS ON THIS ID
                    auxid = index;
                    //SET TEXT PROPERTIES
                    TextView tv1 = (TextView) findViewById(R.id.titlepantalla);
                    tv1.setText("Pantalla "+auxid);
                    EditText et2 = (EditText) findViewById(R.id.idposx);
                    et2.setText(paux.getPosicionx()+"");
                    EditText et3 = (EditText) findViewById(R.id.idposy);
                    et3.setText(paux.getPosiciony()+"");
                    //EditText et4 = (EditText) findViewById(R.id.idescenario);
                    //et4.setText(paux.getEscenario()+"");
                    //EditText et5 = (EditText) findViewById(R.id.idtipo);
                    //et5.setText(paux.getTipo()+"");
                }
            });
            button.setText(n+"");
            layout.addView(button);
        }

    }

    public void saveData(View view){


        Pantalla paux = pantallas.get(this.auxid);
        //SET TEXT PROPERTIES



        EditText et2 = (EditText) findViewById(R.id.idposx);
        EditText et3 = (EditText) findViewById(R.id.idposy);
        Spinner s = (Spinner) findViewById(R.id.escenariospinner);
        //EditText et4 = (EditText) findViewById(R.id.idescenario);
        //EditText et5 = (EditText) findViewById(R.id.idtipo);

        boolean abort =false;
        for(int x = 0; x < pantallas.size();x++){
            if(Integer.parseInt(String.valueOf(et2.getText())) == pantallas.get(x).getPosicionx() && Integer.parseInt(String.valueOf(et3.getText())) == pantallas.get(x).getPosiciony()){
                if(this.auxid !=x){
                    abort = true;
                }
            }
        }

        if(abort){
            Log.d("some","abort true");
            Toast.makeText(this,"Not saved, posiciones repetidas ",Toast.LENGTH_LONG).show();

        }else{

            paux.setPosicionx(Integer.parseInt(String.valueOf(et2.getText())));
            paux.setPosiciony(Integer.parseInt(String.valueOf(et3.getText())));
            //paux.setEscenario(scenarios.get(scenarioselected));
            //paux.setTipo(this.tipo);
            this.tipo=false;
            Log.d("some","abort false");
            //pantallas.set(auxid,paux);
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();

        }
    }

    public void saveConf(View view){
        boolean abort=false;
        //Coordenadas correctas
        for(int x = 0; x < pantallas.size();x++){
            Pantalla p = pantallas.get(x);
            for(int y = x+1; y < pantallas.size();y++){
                Pantalla p2 = pantallas.get(y);
                if(p.getPosicionx() == p2.getPosicionx() && p.getPosiciony() == p2.getPosiciony()){
                    abort = true;
                }
            }
        }
        //Tipos correctos
        /*
        ArrayList numbers= new ArrayList();
        int init = 0;
        boolean redundante=false;
        for(int x = 0; x < pantallas.size();x++){
            Pantalla p = pantallas.get(x);
            for(int n = 0;n < numbers.size();n++){
                if((int)numbers.get(n)==p.getIdpanel()){
                    redundante = true;
                }
            }
            if(!redundante){
                if(p.isTipo()){
                    init ++;
                }
                for(int y = x+1; y < pantallas.size() ;y++){
                    Pantalla p2 = pantallas.get(y);
                    if(p.getIdpanel() == p2.getIdpanel()){
                        if(p2.isTipo()){
                            init++;
                        }
                    }
                }
                if(init!=1){
                    abort = true;
                }
                numbers.add(p.getIdpanel());
            }
        }
        */

        //Plantilla suits with coordenates
        int minx = -1000;
        int maxx = -1000;
        int miny = -1000;
        int maxy = -1000;

        for(int x = 0; x < pantallas.size();x++){
            Pantalla p = pantallas.get(x);
            if(p.getPosicionx() < minx){
                minx=p.getPosicionx();
            }
            if(p.getPosicionx() > maxx){
                maxx=p.getPosicionx();
            }
            if(p.getPosiciony() < miny){
                miny=p.getPosiciony();
            }
            if(p.getPosiciony() > maxy){
                maxy=p.getPosiciony();
            }
        }







        if(abort){
            Toast.makeText(this,"La configuración no es correcta ",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Configuración guardada ",Toast.LENGTH_LONG).show();
            //menu.pantallas = this.pantallas;
            menu.co.setPantallas(this.pantallas);
            menu.init = true;
        }
    }
}
