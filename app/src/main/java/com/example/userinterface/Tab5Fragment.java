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
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;


public class Tab5Fragment extends Fragment {
    private static final String TAG = "Tab5Fragment";

    Button join, refresh;
    int dstPort= 1024;
    //String mes="BE";
    String dstAddress;
    DatagramSocket socket;
    InetAddress address;
    public byte[] buf = new byte[10];

    Spinner dropdown1;
    ArrayAdapter adapter1;

    String[] item1;
    //boolean update_flag = false;
    int sidda;
    int loke;
    int[] jyoth = new int[10];
    int j=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab5_fragment, container, false);

        //Drop down list of the discovered device when updated
        super.onCreate(savedInstanceState);
        inflater.inflate(R.layout.tab5_fragment, container, false);
        join = (Button) view.findViewById(R.id.join);
        join.setOnClickListener(buttonConnectOnClickListener);
        Spinner dropdown1 = (Spinner) view.findViewById(R.id.spinner1);
        item1 = new String []{" "," "," "," "," "," "," "};
        adapter1 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item, item1);
        dropdown1.setAdapter(adapter1);
        refresh = (Button) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(buttonConnectOnClickListener1);

        //set spinner method
        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        sidda = 1;
                        break;
                    case 1:
                        sidda = 2;
                        break;
                    case 2:
                        sidda = 3;
                        break;
                    case 3:
                        sidda = 4;
                        break;
                    case 4:
                        sidda = 5;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return view;

    }

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {


                    if (Tab3Fragment.device_count == 0) {
                        Toast.makeText(getActivity(), "No devices updated", Toast.LENGTH_SHORT).show();
                    }

                    if (Tab3Fragment.device_count > 0) {
                        loke = Integer.parseInt(Tab3Fragment.device_list[sidda - 1]);
                        Toast.makeText(getActivity(), "Join Message Transmitted", Toast.LENGTH_SHORT).show();

                    int crc = 0;

                    //The packet is slightly hardcoded as the join request is fixed length
                    jyoth[0] = 190;//type
                    jyoth[1] = 8;//size
                    jyoth[2] = 255;//Broadcast add
                    jyoth[3] = 1;//source add
                    jyoth[4] = loke;//hardware add
                    jyoth[5] = loke;//device ID
                    jyoth[6] = 0;//slot
                    jyoth[7] = 0;//frame
                    jyoth[8] = 0;//sequence ID
                    for (int h = 0; h < 9; h++) {
                        crc = crc + jyoth[h];
                    }
                    crc = crc % 256;
                    crc = 255 - crc;
                    jyoth[9] = crc;

                    // buf[]= new jyoth[0].toString().getBytes();
                   /* for(int i=0; i<10;i++) {
                      //  any[i] = String.valueOf(jyoth[i]);
                        buf[i] = Byte.parseByte(Arrays.toString(jyoth));
                    } */

                    ByteBuffer suh = ByteBuffer.allocate(jyoth.length * 4);  //// The Boss ////
                    IntBuffer intBuffer = suh.asIntBuffer();
                    intBuffer.put(jyoth);

                    byte[] any = suh.array();

                    for (int i = 0; i < 10; i++) {
                        buf[i] = any[i * 4 + 3];
                    }

                    /*
                    ByteBuffer suh = ByteBuffer.allocate(10);
                    //suh.putInt(jyoth);
                    byte buf[] = jyoth.array();
                    */

                    try {
                        /*dstAddress= Tab1Fragment.bridge_ip;
                        socket = new DatagramSocket();
                        address = InetAddress.getByName(dstAddress);
                        buf = mes.getBytes();
                        DatagramPacket packet =
                                new DatagramPacket(buf, buf.length, address, dstPort);
                        socket.send(packet);*/


                        dstAddress = Tab1Fragment.bridge_ip;
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
                                new DatagramPacket(buf, 10, address, dstPort);
                        socket.send(packet);


                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (socket != null) {
                            socket.close();
                        }
                    }
                }
            }
            };

    View.OnClickListener buttonConnectOnClickListener1 =
            new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();


                    //item1 = new String [Tab3Fragment.device_count+1];
                    for(int j=0;j<Tab3Fragment.join_device_count;j++) {
                        item1[j] = Tab3Fragment.join_device_list[j];
                    }
                    adapter1.notifyDataSetChanged();
                }
            };
}
