package com.example.leonardo.physicballsv2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    /**
     * Boton para buscar server
     */
    Button button = null;
    /**
     * TextView que muestra el status del server
     */
    TextView  tv = null;
    /**
     * Booleana que indica si ha encontrado o no servidor
     */
    boolean found = false;



    @Override
    /**
    Pide permisos de internet, inicializa el boton y el textview y crea un listener del boton de cuando es clickeado
    ejecuta el método search server
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Permisos para activar conexiones a internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        button = (Button) findViewById(R.id.btn1_search);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on clic
                try {
                    searchServer();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        tv  = (TextView) findViewById(R.id.tv1_info);

    }
    /**
    Busqueda del servidor mediante el httprequester, en cuanto se ejecute la busqueda, si ha encontrado servidor, procedera
    a ejecutar el intent menu, sino enviara un aviso de que no se ha encontrado server.
     */
    public void searchServer() throws ExecutionException, InterruptedException {
                HttpRequester hr = new HttpRequester();
                hr.setOption(1);
                try {hr.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            this.found = hr.isFound();
            //this.found =true;// hr.getFound();
        if(found){
            /*
            new Thread(new Runnable(){
                @Override
                public void run() {
                    HttpRequester.registerinServer();
                }
            }).start();
            */
            //ServerControllerSocket.Register();
            this.setTextInfo("Server found");
            //intent
            Intent i =new Intent(MainActivity.this, menu.class);
            i.putExtra("pantallas", "sda");
            startActivity(i);
        }else{
            this.setTextInfo("No server found");
        }

    }

    /**
    Hace un set text del status del server para saber si se ha encontrado server o no en el textview
     */
    public void setTextInfo(String txt){
        tv.setText(txt);
    }

}
