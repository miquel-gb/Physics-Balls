package com.example.leonardo.physicballsv2;

/**
 * Created by Leonardo on 25/03/2017.
 */


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by Leonardo on 11/02/2017.
 */

public class HttpRequester extends AsyncTask<Void,Void,Void>  {
    public static Socket sock;

    public static final int PORT = 11111;     // server details
    private static String HOST ="null";

    public static ServerControllerSocket scc;

    public static String statusserver = "Initializating the media";
    public static boolean found = false;

    public static int option = -1;

    public static ArrayList<String> data;

    HttpRequester(){

    }


    @Override
    protected Void doInBackground(Void... params) {
        if(option==1){
            if(getServerIP() != null){
                this.HOST = getServerIP().getHostAddress();
                makeContact();
                this.found = true;
            }
        }else if(option == 2){
            this.getConfData();
        }
        return null;
    }

    public static void registerinServer(){
        scc = new ServerControllerSocket(sock);
    }
    /*
        1-Connect to server
        2-Get Data Configuration
        3-Set Data Configuration
        4-Run
        5-Stop
     */
    public void setOption(int n){
        this.option=n;
    }

    public static boolean isFound() {
        return found;
    }

    public InetAddress getServerIP() {

        InetAddress ip = null;

        this.statusserver = "Preparando reconocimiento de red";
        // Find the server using UDP broadcast
        try {
            //Open a random port to send the package
            DatagramSocket c = new DatagramSocket();
            c.setBroadcast(true);
            c.setSoTimeout(15000);

            byte[] sendData = "/ping".getBytes();
            this.statusserver = "Enviando paquetes para reconocimiento de red";
            //Try the 255.255.255.255 first
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), HttpRequester.PORT);
                c.send(sendPacket);
                System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Broadcast the message over all the network interfaces
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

                    // Send the broadcast package!
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, HttpRequester.PORT);
                        c.send(sendPacket);
                    } catch (Exception e) {
                        System.err.println("ERROR SENDING BROADCAST PACKET");
                    }

                    System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                }
            }

            System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

            //Wait for a response
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);

            //We have a response
            System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
            this.statusserver = "Recibiendo paquetes de reconocimiento de red";
            //Check if the message is correct
            String message = new String(receivePacket.getData()).trim();
            if (message.equals("/ping")) {
                ip = receivePacket.getAddress();
                System.out.println("DISCOVERED IP: " + ip);
            }
            this.statusserver = "Descubierto servicio de "+ ip;
            //Close the port!
            c.close();
        } catch (IOException ex) {
            System.err.println("BROADCAST TIMED OUT");
        }
        return ip;
    }

    private void makeContact()
    {
        this.statusserver = "Conectando con el servidor";
        try {
            sock = new Socket(HOST, PORT);
            this.statusserver = "Conectado";
            this.found = true;
            registerinServer();
        }
        catch(Exception e)
        {  System.out.println(e);
            this.statusserver = ("Error al conectar");
        }
    }  // end of makeContact()


    public static ArrayList getConfData(){
        try {
            data = new ArrayList<String>();
            //out.print("/requestconfdata");
            String txt ="";
            do{
                //txt = in.readLine();
                data.add(txt);
            }while (txt != "/end");
            return data;
        }catch (Exception e){
            //Log.d(e.toString());
            return null;
        }
    }
    public static String getStatusserver(){
        return  statusserver;
    }


    public static void start(){
        //Send start request
        scc.start();
    }

    public static void stop(){
        //Send stop request
    }

    public static boolean getFound(){
        return found;
    }
}
