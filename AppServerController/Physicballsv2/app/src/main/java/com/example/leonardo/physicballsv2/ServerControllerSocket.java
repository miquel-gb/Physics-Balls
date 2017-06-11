/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.leonardo.physicballsv2;

import android.util.Log;
import android.widget.Toast;


import org.physicballs.items.Peticion;
import org.physicballs.items.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class ServerControllerSocket extends Thread {

    /**
     * Puerto del servidor
     */
    private static final int PORT = HttpRequester.PORT;
    /**
     * Socket del servidor
     */
    public static Socket sc;
    /**
     * Output de la aplicacion que permite enviar objetos al servidor
     */
    public static ObjectOutputStream out;
    /**
     * Input de la aplicacion que permite recibir objetos del servidor
     */
    public static ObjectInputStream in;
    /**
     * Booleano de vide del thread de escucha al servidor
     */
    private static boolean live = true;
    /**
     * Booleano que indica si se esta conectado
     */
    private static boolean connected = false;
    /**
     * Array de string de los escenarios
     */
    private static String [] scenarios;

    /**
     * Server Controller socket constructor
     */
    public ServerControllerSocket(Socket socketConnection) {
        try {
            //HttpRequester hr= new HttpRequester();
            //hr.execute().get();
            //Socket socketConnection = hr.getSocket();
            //Socket socketConnection = new Socket(getServerIP().getHostAddress(), 11111);
            sc = socketConnection;
            out = new ObjectOutputStream(sc.getOutputStream());
            in = new ObjectInputStream(sc.getInputStream());
            //Register();
            this.start();
            this.connected = true;
        } catch (IOException ex) {
            //this.connected = false;
            System.out.println("refused server connection");
            this.connected = false;
        }

    }

    @Override
    public void run() {
        while (live) {
            try {
                Object o = in.readObject();
                if (o instanceof Status) {
                    if (((Status) o).ID >= 500) {
                        //Se ha producido un error
                        System.out.println(((Status) o).description);
                        Log.e("Status", "500: " + ((Status) o).description);
                        live = false;
                    } else if(((Status) o).ID == 2){
                        System.out.println(((Status) o).description);
                        Log.e("Status", "2: " + ((Status) o).description);
                    } else if(((Status) o).ID == 1){
                        System.out.println(((Status) o).description);
                        Log.e("Status", "1: " + ((Status) o).description);
                    }
                } else if (o instanceof Peticion) {
                    switch (((Peticion) o).getAccion()) {
                        case "get_settings" :
                            //System.out.println(((String) ((Peticion) o).getObject(0)));
                            //Preconfiguration.escenarios = ((Peticion) o).getData();
                            break;
                        case "get_scenarios":
                            //Send String[] scenarios;
                            scenarios = (String[]) ((Peticion) o).getData().get(0);
                            Preconfiguration.scenario  = scenarios;
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {

            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @param scenario
     *
     * Abre el servidor con una altura, anchura y escenario
     */
    public static void openServer(int x, int y,String scenario) {
        //ServerControllerSocket.Register();

        try {
            Peticion p = new Peticion("open_map");
            p.pushData(x);
            p.pushData(y);
            p.pushData(scenario);
            out.flush();
            out.reset();
            out.writeObject(p);
            Log.e("Send data","Send openserver");
        } catch (IOException ex) {
            Log.e("Send data","ERROR OPEN SERVER");
        }

    }

    /**
     *
     * @param plantilla
     *
     * Envia la configuracion de las pantallas al servidor
     */
    public static void setPlantilla(int[][] plantilla) {
        try {
            Peticion p = new Peticion("set_plantilla");
            p.pushData(plantilla);
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    /**
     * Inicia el server
     */
    public void startServer() {
        try {
            Peticion p = new Peticion("open_server");
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    /**
     * Para el servidor
     */
    public void stopServer() {
        try {
            Peticion p = new Peticion("close_server");
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    /**
     * Coje los settings del servidor
     */
    public void getSetting() {
        try {
            Peticion p = new Peticion("get_settings");
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }


    /**
     * Realiza un registro en el servidor
     */
    public static void Register() {

        try {
            out.writeObject("server_controller");
        } catch (IOException ex) {

        }

    }


    /**
     *
     * @param co
     *
     * Envia la configuracion al servidor
     */
    public void sendConf(ConfigurationObject co){
            //openServer(co.getPlantillax(),co.getPlantillay());
            setPlantilla(parserObjectToServer(co));
    }

    /**
     *
     * @param co
     * @return
     *
     * Parsear el objeto de la aplicacion al objeto del servidor
     */
    public int [][] parserObjectToServer(ConfigurationObject co){
        int[][] coorpant = new int[co.getPlantillax()*co.getPlantillay()][2];
        for(int i = 0; i < co.getPantallas().size();i++){
            Pantalla paux = co.getPantallas().get(i);
            coorpant[i][0] = paux.getPosicionx();
            coorpant[i][1] = paux.getPosiciony();
        }
        return coorpant;
    }


    /**
     *
     * @param w1
     * @param w2
     *
     * Mueve las pantallas de lugar
     */
    public void moveWindow(int[] w1, int[] w2) {
        try {
            Peticion p = new Peticion("move_window");
            p.pushData(w1);
            p.pushData(w2);
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    /**
     *
     * @param w
     *
     * Elimina una pantalla
     */
    public void removeWindow(int[] w){
        try {
            Peticion p = new Peticion("remove_window");
            p.pushData(w);
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    /**
     *
     * Coje los escenarios del servidor
     */
    public void requestScenario(){
        try {
            Peticion p = new Peticion("get_scenarios");
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }


    /**
     *
     * @param str
     *
     * Comunicacion directa con el servidor
     */
    public void echo(String str) {
        try {
            Peticion p = new Peticion("echo");
            p.pushData(str);
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }
}
