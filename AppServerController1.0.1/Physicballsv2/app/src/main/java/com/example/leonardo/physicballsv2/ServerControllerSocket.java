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
 * @author Liam-Portatil
 */
public class ServerControllerSocket extends Thread {

    /**
     * Global parameters
     */
    private static final int PORT = HttpRequester.PORT;
    public static Socket sc;
    public static ObjectOutputStream out;
    public static ObjectInputStream in;
    private static boolean live = true;
    private static boolean connected = false;

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
                            Preconfiguration.escenarios = ((Peticion) o).getData();
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {

            }
        }
    }


    public static void openServer(int x, int y) {
        //ServerControllerSocket.Register();

        try {
            Peticion p = new Peticion("open_map");
            p.pushData(x);
            p.pushData(y);
            out.flush();
            out.reset();
            out.writeObject(p);
            Log.e("Send data","Send openserver");
        } catch (IOException ex) {
            Log.e("Send data","ERROR OPEN SERVER");
        }

    }

    public static void setPlantilla(int[][] plantilla) {
        try {
            Peticion p = new Peticion("set_plantilla");
            p.pushData(plantilla);
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    public void startServer() {
        try {
            Peticion p = new Peticion("open_server");
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    public void getSetting() {
        try {
            Peticion p = new Peticion("get_settings");
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }

    public static void Register() {

        try {
            out.writeObject("server_controller");
        } catch (IOException ex) {

        }

    }



    public void sendConf(ConfigurationObject co){
            //openServer(co.getPlantillax(),co.getPlantillay());
            setPlantilla(parserObjectToServer(co));
    }

    public int [][] parserObjectToServer(ConfigurationObject co){
        int[][] coorpant = new int[co.getPlantillax()*co.getPlantillay()][2];
        for(int i = 0; i < co.getPantallas().size();i++){
            Pantalla paux = co.getPantallas().get(i);
            coorpant[i][0] = paux.getPosicionx();
            coorpant[i][1] = paux.getPosiciony();
        }
        return coorpant;
    }

    public void requestScenario(){
        try {
            Peticion p = new Peticion("/getScenario");
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }


    public void echo(String str) {
        try {
            Peticion p = new Peticion("echo");
            p.pushData(str);
            out.writeObject(p);
        } catch (IOException ex) {

        }
    }


    /**
     * Finds the IP of the server using the available port
     *
     * @return
     */
    public InetAddress getServerIP() {
        InetAddress ip = null;
        try {
            DatagramSocket c = new DatagramSocket();
            c.setBroadcast(true);
            c.setSoTimeout(5000);
            byte[] sendData = "/ping".getBytes();
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), PORT);
                c.send(sendPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }
                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 11111);
                        c.send(sendPacket);
                    } catch (Exception e) {
                        System.err.println("ERROR SENDING BROADCAST PACKET");
                    }
                }
            }
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);

            String message = new String(receivePacket.getData()).trim();
            if (message.equals("/ping")) {
                ip = receivePacket.getAddress();
                System.out.println("DISCOVERED IP: " + ip);
            }
            //Close the port!
            c.close();
        } catch (IOException ex) {
            System.err.println("BROADCAST TIMED OUT");
        }
        return ip;
    }

    public boolean isConnected() {
        return connected;
    }
}
