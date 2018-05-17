package com.example.userinterface;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import java.net.SocketException;
import java.net.UnknownHostException;

public class Tab4Fragment extends Fragment {
    private static final String TAG = "Tab4Fragment";

     Button discovery;
    int dstPort= 1024;
    //int mes=174;
    String dstAddress;
    DatagramSocket socket;
    InetAddress address;
    public byte[] buf = {-82,4,0,1,0,76};  //We hardcoded checksum also as the discover request packet is constant
   // public byte[] buf = {-83,4,0,1,0,95};  //// discovery response
   // public byte[] buf = {-83,4,0,3,0,95};  //// discovery response
   // private byte[] discdata = new byte[6];

   // String message = "stackoverflow \u00AE";



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab4_fragment, container, false);
        super.onCreate(savedInstanceState);
        inflater.inflate(R.layout.tab4_fragment, container, false);

        discovery = (Button) view.findViewById(R.id.discovery);
        discovery.setOnClickListener(buttonConnectOnClickListener);
        //
      //  setRetainInstance(true);
        //

                return view;
    }
   /* public void onClickDis(View view) {
        //  Toast.makeText(getActivity(), "TESTING BUTTON CLICK 3",Toast.LENGTH_SHORT).show();
        byte[] buf = new byte[256];
        dstAddress = '192.168.1.12';
        try
        {
            socket = new DatagramSocket();
            address = InetAddress.getByName(dstAddress);
            dstPort = 1024;
            mes = "0xAE";
            buf = mes.getBytes();
            DatagramPacket packet =
                    new DatagramPacket(buf, buf.length, address, dstPort);
            socket.send(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
            }
        }



    } */
   View.OnClickListener buttonConnectOnClickListener =
           new View.OnClickListener() {

               @Override
               public void onClick(View arg0) {
                   Toast.makeText(getActivity(), "Message Transmitted", Toast.LENGTH_SHORT).show();

                      try
                       {
                           dstAddress= Tab1Fragment.bridge_ip;
                           socket = new DatagramSocket();
                           address = InetAddress.getByName(dstAddress);
                           //buf = message.getBytes();

                           DatagramPacket packet =
                                   new DatagramPacket(buf, 6, address, dstPort);
                           socket.send(packet);
                           Toast.makeText(getActivity(), "Message Transmitted", Toast.LENGTH_SHORT).show();


                   } catch (SocketException e) {
                       e.printStackTrace();
                   } catch (UnknownHostException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   } finally {
                       if(socket != null){
                           socket.close();
                       }
                   }
               }
           };



   }
