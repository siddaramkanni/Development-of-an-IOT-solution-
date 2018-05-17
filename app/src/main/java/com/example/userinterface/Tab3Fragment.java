package com.example.userinterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created by User on 2/28/2017.
 */

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";
    //private final static String TAG = MainActivity.class.getSimpleName();   //TAG represents the name of the activity class

    TextView infoIp;
    TextView textViewState, textViewPrompt;

    static final int UdpServerPORT = 1024;
    UdpServerThread udpServerThread;

    int[] mes = new int[256];

    //int[] myIntArray = new int[3];
    public static String global_deviceID;
    public static String device_list[] = new String[4];             //Global variable to maintain all the device list that sends discover response
    public static String join_device_list[] = new String[4];
    public static int device_count;
    public static int join_device_count;
    public static boolean device_flag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);
        super.onCreate(savedInstanceState);
        inflater.inflate(R.layout.tab3_fragment,container,false);
        infoIp = (TextView) view.findViewById(R.id.infoip);
        textViewState = (TextView) view.findViewById(R.id.state);
        textViewPrompt = (TextView)view.findViewById(R.id.prompt);

        infoIp.setText(getIpAddress());             //to get the device ip addresses
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onStart() {
        udpServerThread = new UdpServerThread(UdpServerPORT);
        udpServerThread.start();
        super.onStart();
    }

    @Override
    public void onStop() {
        if(udpServerThread != null){
            udpServerThread.setRunning(false);
            udpServerThread = null;
        }

        super.onStop();
    }

    private void updateState(final String state){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewState.setText(state);
            }
        });
    }

    private void updatePrompt(final String prompt){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewPrompt.append(prompt);
            }
        });
    }

    private class UdpServerThread extends Thread{

        int serverPort;
        DatagramSocket socket;

        boolean running;

        public UdpServerThread(int serverPort) {
            super();
            this.serverPort = serverPort;
        }

        public void setRunning(boolean running){
            this.running = running;
        }

        @Override
        public void run() {

            running = true;
           // while(true) {
                try {

                    updateState("Starting UDP Server");
                    socket = new DatagramSocket(serverPort);

                    updateState("UDP Receiver is running");
                    Log.e(TAG, "UDP Receiver is running");

                    while (running) {
                        byte[] buf = new byte[256];
                        int length;
                        // receive request
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);
                        //buf.length = 0;
                        //buf[0] = 0;
                        //socket.setSoTimeout(5000);                                                   //Extra line of code
                        socket.receive(packet);     //this code block the program flow

                        length = packet.getLength();

                        if (length != 0) {

                            InetAddress address = packet.getAddress();
                            buf = packet.getData();
                            int i;
                            String message = " ";
                            for (i = 0; i < buf.length; i++) {  //This is to print the characters
                                message += (char) buf[i];
                            }

                            for(i = 0 ;  i < buf.length; i++) {   //This is to print the numbers received
                                mes[i] = Integer.parseInt(String.valueOf(buf[i]));
                            }


                            //To process the received message

                            //Loop to process discover message
                            if(mes[0]== -83)//Discover response type
                            {
                                int device_ID = mes[3];
                                String str_device_ID = Integer.toString(device_ID);
                                updatePrompt("Discover Response Received from: "+ str_device_ID + "\n");
                                device_flag = true;

                                for(i=0;i<=device_count;i++)
                                {
                                    if(device_list[i] == str_device_ID){
                                        device_flag = false;
                                    }
                                }
                                if(device_flag){ //To store the hardware address and device ID from the received packet
                                    device_list[device_count] = String.valueOf(buf[3]);
                                    join_device_list[join_device_count] = String.valueOf(buf[3]);
                                    device_count++;
                                    join_device_count++;
                                }

                            }

                            //To process the join response message
                            else if(mes[0]== -65)//Join response type
                            {
                                int device_ID = mes[3];
                                String str_device_ID = Integer.toString(device_ID);
                                updatePrompt("Join Response Received from: "+ str_device_ID+ "\n");

                                for(i=0;i<join_device_count;i++)
                                {
                                    if(join_device_list[i] == str_device_ID){
                                        join_device_list[i] = " ";
                                    }
                                }
                            }

                            //Event response processing
                            else if(mes[0]== 12)    //Event response type
                            {
                                int device_ID = mes[4];
                                int device_ID1 = mes[5];
                                int device_ID2 = mes[5];
                                int device_ID4 = mes[8];
                                String str_device_ID = Integer.toString(device_ID);
                                String str_device_ID1 = Integer.toString(device_ID1);
                                String str_device_ID2 = Integer.toString(device_ID2);
                                String str_device_ID4 = Integer.toString(device_ID4);

                                //source_address = mes[3];
                                if(mes[6] == 0)
                                {
                                        updatePrompt("Dawn Received from ID" + str_device_ID+" "+ str_device_ID1 +" " + str_device_ID2);
                                }

                                else if(mes[6] == 1 )
                                {
                                    updatePrompt("Dusk Received from ID" + str_device_ID+" "+ str_device_ID1 +" " + str_device_ID2);
                                }

                                else if(mes[6] ==2)
                                {
                                    updatePrompt("Time Received from ID" + str_device_ID+" "+ str_device_ID1 +" " + str_device_ID2);
                                }
                                else if(mes[6] ==3 )
                                {
                                    updatePrompt("Temperature Received from ID" + str_device_ID+" "+ str_device_ID1 +" " + str_device_ID2);
                                }
                                else if( mes[6] ==4)
                                {
                                    updatePrompt("Weather Received from ID" + str_device_ID+": Temperature "+ str_device_ID1 +": Moisture " + str_device_ID2);
                                }
                                else if (mes[6] ==7)
                                {
                                    if (mes[7] ==3)
                                    {
                                        updatePrompt("Temperature Received from ID" + str_device_ID4);
                                    }
                                    else if (mes[7] ==4)
                                    {
                                        updatePrompt("Humidity Received from ID" + str_device_ID4);
                                    }
                                    if (mes[7] ==5)
                                    {
                                        updatePrompt("Pressure Received from ID" + str_device_ID4);
                                    }
                                    else if (mes[7] ==6)
                                    {
                                        updatePrompt("CO2 Received from ID" + str_device_ID4);
                                    }
                                    if (mes[7] ==7)
                                    {
                                        updatePrompt("TVOC Received from ID" + str_device_ID4);
                                    }
                                    else if (mes[7] ==8)
                                    {
                                        updatePrompt("Fire Received from ID" + str_device_ID4);
                                    }
                                }
                            }

                            else {      //Just to display the received message with the IP address
                                updatePrompt("Data received from " + address + ", Message is" + message + "\n");
                            }
                            //String dString = "i got your message";
                            //buf = dString.getBytes();
                            //packet = new DatagramPacket(buf, buf.length, address, port);
                            //socket.send(packet);
                        }
                    }

                    Log.e(TAG, "UDP Server ended");

                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (socket != null) {
                        socket.close();
                        Log.e(TAG, "socket.close()");
                    }
                }
        }
    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}





