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

    /**
     * Socket de conexion con el server
     */
    public static Socket sock;

    /**
     * Puerto del servidor
     */
    public static final int PORT = 11111;     // server details
    /**
     * Ip del servidor
     */
    private static String HOST ="null";

    /**
     * Manager de la interfaz del AppServerController con el servidor
     */
    public static ServerControllerSocket scc;

    /**
     * Status de la conexion
     */
    public static String statusserver = "Initializating the media";
    /**
     * Booleano que indica si se ha encontrado un server
     */
    public static boolean found = false;

    /**
     * Indice de la opcion selecciona del execute del httprequester
     */
    public static int option = -1;

    /**
     * Array de String del metodo de getSettings
     */
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
        }else if (option == 3){
           // scc.requestScenario();
        }
        return null;
    }

    /**
     * Se inicializa la interfaz con el socket para poder comenzar la comucicacion
     * de la app con el servidor.
     */
    public static void registerinServer(){
        scc = new ServerControllerSocket(sock);
    }
    /**
     * Opcion del execute
        1-Connect to server
        2-Get Data Configuration
        3-Set Data Configuration
        4-Run
        5-Stop
     */

    public void setOption(int n){
        this.option=n;
    }

    /**
     *
     * @return
     *
     * Indica si se ha encontrado server
     */
    public static boolean isFound() {
        return found;
    }

    /**
     *
     * @return
     *
     * Hace una busqueda por la red wifi enviado un comando /ping a toda la red.
     * Luego espera si recibe una respuesta /ping y devuelve la direccion del
     * server.
     */
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

    /**
     * Realiza una conexion al servidor mediante ip y puerto.
     * Si encuentra llama al metodo de iniciar la interfaz de comunicacion entre servidor
     * y aplicacion, sino indica que no se ha podido conectar
     */
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

    /**
     *
     * @return
     *
     * Devuelve la configuracion que esta en el servidor
     */
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
}
