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
    public static boolean modifier = false;
    public ArrayList buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        //this.numpantallas = menu.numpant;
        if(menu.init){
            if(modifier){
                pantallas=menu.co.getPantallas();
                loadlastpantallas();
            }else{
                pantallas = new ArrayList<Pantalla>();
                loadnewpantallas();
            }
        } else {
            pantallas = new ArrayList<Pantalla>();
            loadnewpantallas();
        }


    }

    public void loadnewpantallas(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.screenlist);
        LinearLayout layouttool = (LinearLayout) findViewById(R.id.toolscreen);

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
                    EditText et3 = (EditText) findViewById(R.id.idposy);
                    if(paux.getPosicionx()!=999){
                        et2.setText(paux.getPosicionx()+"");
                    }
                    if(paux.getPosicionx()!=999){
                        et3.setText(paux.getPosiciony()+"");
                    }
                    //EditText et4 = (EditText) findViewById(R.id.idescenario);
                    //et4.setText(paux.getEscenario()+"");
                    //EditText et5 = (EditText) findViewById(R.id.idtipo);
                    //et5.setText(paux.getTipo()+"");
                }
            });
            button.setText(n+"");
            layout.addView(button);

        }
        Button buttongp = new Button(this);
        buttongp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(view);
            }
        });
        buttongp.setText("Guardar");
        layouttool.addView(buttongp);
        Button buttongc = new Button(this);
        buttongc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveConf(view);
            }
        });
        buttongc.setText("Guardar Configuracion");
        layouttool.addView(buttongc);
    }

    public void loadlastpantallas(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.screenlist);
        LinearLayout layouttool = (LinearLayout) findViewById(R.id.toolscreen);
        buttons = new ArrayList();
        for(int n= 0;n<menu.co.getPantallas().size();n++){
           // Pantalla p = menu.co.getPantallas().get(n);
           // pantallas.add(p);
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
                    EditText et3 = (EditText) findViewById(R.id.idposy);

                        et2.setText(paux.getPosicionx()+"");


                        et3.setText(paux.getPosiciony()+"");

                    //EditText et4 = (EditText) findViewById(R.id.idescenario);
                    //et4.setText(paux.getEscenario()+"");
                    //EditText et5 = (EditText) findViewById(R.id.idtipo);
                    //et5.setText(paux.getTipo()+"");
                }
            });
            Pantalla paux = pantallas.get(0);
            EditText et2 = (EditText) findViewById(R.id.idposx);
            EditText et3 = (EditText) findViewById(R.id.idposy);

            et2.setText(paux.getPosicionx()+"");


            et3.setText(paux.getPosiciony()+"");
            button.setText(n+"");
            layout.addView(button);
            buttons.add(button);
        }
        Button buttongp = new Button(this);
        buttongp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(view);
            }
        });
        buttongp.setText("Mover Pantalla");
        layouttool.addView(buttongp);
        Button buttongc = new Button(this);
        buttongc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeData(view);
            }
        });
        buttongc.setText("Eliminar Pantalla");
        layouttool.addView(buttongc);
    }

    public void removeData(View view){
        if(pantallas.size()>0){
        int index = this.auxid;
        Pantalla paux = pantallas.get(index);

        LinearLayout layout = (LinearLayout) findViewById(R.id.screenlist);
        int count = layout.getChildCount();
        View v = null;
        boolean less=false;
        Log.e("COUNT",count+"");

            for(int i=0; i<count; i++) {
                v =  layout.getChildAt(i);
                if(i == index) {
                    layout.removeViewAt(i);
                    int[] w = new int[2];
                    w[0]=paux.getPosicionx();
                    w[1]=paux.getPosiciony();
                    HttpRequester.scc.removeWindow(w);
                    pantallas.remove(index);
                    menu.co.setPantallas(this.pantallas);
                    less = true;
                }
                /*else{
                    if(less){
                        int aux = i-1;
                        Button baux = (Button) buttons.get(i);
                        baux.setText(aux+"");
                    }
                }*/

                //do something with your child element
            }
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
                    if(modifier){
                        //http change screen

                        Pantalla paux2 = pantallas.get(x);
                        paux2.setPosicionx(paux.getPosicionx());
                        paux2.setPosiciony(paux.getPosiciony());

                        paux.setPosicionx(Integer.parseInt(String.valueOf(et2.getText())));
                        paux.setPosiciony(Integer.parseInt(String.valueOf(et3.getText())));

                        int[] a = new int[2];
                        a[0]=paux.getPosicionx();
                        a[1]=paux.getPosiciony();
                        int[] b = new int[2];
                        a[0]=paux2.getPosicionx();
                        a[1]=paux2.getPosiciony();

                        HttpRequester.scc.moveWindow(a,b);
                        menu.co.setPantallas(this.pantallas);
                    }else{
                        abort = true;
                    }
                }
            }
        }

        if(abort){
            if(!modifier) {
                Log.d("some", "abort true");
                Toast.makeText(this, "Not saved, posiciones repetidas ", Toast.LENGTH_LONG).show();
            }
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

        for(int x = 0; x < pantallas.size();x++){
            Pantalla p = pantallas.get(x);
            if(p.getPosicionx() < 0){
                abort=true;
            }
            if(p.getPosicionx() >= menu.co.getPlantillax()){
                abort=true;
            }
            if(p.getPosiciony() < 0){
                abort=true;
            }
            if(p.getPosiciony() >= menu.co.getPlantillay()){
                abort=true;
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
