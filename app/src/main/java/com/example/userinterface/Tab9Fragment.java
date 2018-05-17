package com.example.userinterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Tab9Fragment extends Fragment {
    private static final String TAG = "Tab9Fragment";

    //  private Button btnTEST;
    int dstPort= 1024;
    private Button button;
    private EditText editText3;
    private ListView state;
    //public static String bridge_ip;
    public String temp;
    String dstAddress;
    DatagramSocket socket;
    InetAddress address;
    public byte[] buf = new byte[7];

    int[] record = new int[7];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab9_fragment, container, false);

        button = (Button) view.findViewById(R.id.button3);
        button.setOnClickListener(buttonConnectOnClickListener);

        Spinner dropdown1 = (Spinner) view.findViewById(R.id.spinner1);
        String[] item1 = new String[]{"DEVICE 1", "DEVICE 2", "DEVICE 3", "DEVICE 4", "DEVICE 5"};
        ArrayAdapter adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, item1);
        dropdown1.setAdapter(adapter1);

        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        record[2] = 30;
                        break;
                    case 1:
                        record[2] = 31;
                        break;
                    case 2:
                        record[2] = 32;
                        break;
                    case 3:
                        record[2] = 33;
                        break;
                    case 4:
                        record[2] = 34;
                        break;
                    case 5:
                        record[2] = 35;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Spinner dropdown2 = (Spinner) view.findViewById(R.id.spinner);
        String[] item2 = new String[]{"TIME", "TEMP"};
        ArrayAdapter adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, item2);
        dropdown2.setAdapter(adapter2);

        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        record[4] = 0;
                        break;
                    case 1:
                        record[4] = 1;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Toast.makeText(getActivity(), "ping message sent", Toast.LENGTH_SHORT).show();
        return view;
    }

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Toast.makeText(getActivity(), "Status Message Transmitted", Toast.LENGTH_SHORT).show();
                    dstAddress= Tab1Fragment.bridge_ip;

                    int crc = 0;

                    record[0] = 13;  //Status message type
                    record[1] = 5;
                    record[3] = 1;
                    record[5] = 0;

                    for(int e=0;e<6;e++)
                    {
                        crc = crc + record[e];
                    }
                    crc = crc%256;
                    record[6] = (255-crc);

                    ByteBuffer suh = ByteBuffer.allocate(record.length*4);  //// The Boss ////
                    IntBuffer intBuffer = suh.asIntBuffer();
                    intBuffer.put(record);

                    byte[] any= suh.array();

                    for(int i=0; i<7;i++) {
                        buf[i] = any[i*4+3];
                    }

                    try
                    {
                        /*dstAddress= Tab1Fragment.bridge_ip;
                        socket = new DatagramSocket();
                        address = InetAddress.getByName(dstAddress);
                        buf = mes.getBytes();
                        DatagramPacket packet =
                                new DatagramPacket(buf, buf.length, address, dstPort);
                        socket.send(packet);*/



                        dstAddress= Tab1Fragment.bridge_ip;
                        socket = new DatagramSocket();
                        address = InetAddress.getByName(dstAddress);
                        //buf = message.getBytes();
                        //int idata=174;
                        // discdata[0]= -82;
                        // discdata[1]=4;
                          /* discdata[2]=0;
                           discdata[3]=-18;
                           discdata[4]=0;
                           discdata[5]=95; */
                        // String temp = new String(discdata);
                        //String temp = bytes.toString(record);
                        //String mes = "Hi";

                        // buf = temp.getBytes();
                        DatagramPacket packet =
                                new DatagramPacket(buf, 7, address, dstPort);
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



                }
            };
}