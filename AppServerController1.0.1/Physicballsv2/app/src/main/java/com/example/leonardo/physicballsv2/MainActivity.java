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
    Button button = null;
    TextView  tv = null;
    boolean found = false;



    @Override
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

    public void newIntent(){
        Intent i =new Intent(MainActivity.this, menu.class);
        i.putExtra("HttpRequester", "sda");
        startActivity(i);
    }

    public void setTextInfo(String txt){
        tv.setText(txt);
    }
    public void setFound(boolean f){
        this.found = f;
    }
}
