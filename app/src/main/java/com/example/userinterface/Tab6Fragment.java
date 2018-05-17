
package com.example.userinterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

//public class Tab6Fragment extends Fragment {
//  private static final String TAG = "Tab6Fragment";


public class Tab6Fragment extends Fragment {


    private static final String TAG = "Tab6Fragment";
    TextView textViewPrompt;

    private Button button15;
     EditText editText10 ;
     EditText editText11 ;
     EditText editText12 ;
     EditText editText13 ;


    int[] record = new int[14];
    DatagramSocket socket;
    InetAddress address;
    String dstAddress;
    int dstPort = 1024;
    //Tab2Fragment.UdpClientHandler handler;
    public byte[] buf = new byte[14];


    private void updatePrompt(final String prompt){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewPrompt.append(prompt);
            }
        });
    }

   /* public UdpClientThread(String addr, String mes, Tab2Fragment.UdpClientHandler handler) {
        super();
        dstAddress = addr;
        dstPort = 1024;
        mes1 = mes;
        this.handler = handler;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab6_fragment, container, false);
        //super.onCreate(savedInstanceState);
        inflater.inflate(R.layout.tab6_fragment, container, false);
        // getActivity().setContentView(R.layout.activity_main);

        button15 = (Button) view.findViewById(R.id.button15);
        button15.setOnClickListener(ruleConnectOnClickListener);

        textViewPrompt = (TextView) view.findViewById(R.id.prompt);

        editText10 = (EditText) view.findViewById(R.id.editText10);
        editText11 = (EditText) view.findViewById(R.id.editText11);
        editText12 = (EditText) view.findViewById(R.id.editText12);
        editText13 = (EditText) view.findViewById(R.id.editText13);

        //get the spinner from the xml.
        Spinner dropdown1 = (Spinner) view.findViewById(R.id.spinner101);
        //create a list of items for the spinner.
        String[] items1 = new String[]{"TIME", "TEMP", "DUSK", "DAWN","N/A"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items1);
        //set the spinners adapter to the previously created one.
        dropdown1.setAdapter(adapter1);

        //set spinner method
        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        record[3] = 1;
                        break;
                    case 1:
                        record[3] = 2;
                        break;
                    case 2:
                        record[3] = 3;
                        break;
                    case 3:
                        record[3] = 4;
                        break;
                    case 4:
                        record[3] = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //get the spinner from the xml.
        Spinner dropdown2 = (Spinner) view.findViewById(R.id.spinner102);
        //create a list of items for the spinner.
        String[] items2 = new String[]{">", "<", "=", "N/A"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items2);
        //set the spinners adapter to the previously created one.
        dropdown2.setAdapter(adapter2);

        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        record[4] = 1;
                        break;
                    case 1:
                        record[4] = 2;
                        break;
                    case 2:
                        record[4] = 3;
                        break;
                    case 3:
                        record[4] = 4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //get the spinner from the xml.
        Spinner dropdown3 = (Spinner) view.findViewById(R.id.spinner105);
        //create a list of items for the spinner.
        String[] items3 = new String[]{">", "<", "=", "N/A"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item, items3);
        //set the spinners adapter to the previously created one.
        dropdown3.setAdapter(adapter3);

        dropdown3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        record[9] = 1;
                        break;
                    case 1:
                        record[9] = 2;
                        break;
                    case 2:
                        record[9] = 3;
                        break;
                    case 3:
                        record[9] = 4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //get the spinner from the xml.
        Spinner dropdown4 = (Spinner) view.findViewById(R.id.spinner100);
        //create a list of items for the spinner.
        String[] items4 = new String[]{ "DEVICE 1", "DEVICE 2","DEVICE 3","DEVICE 4","DEVICE 5"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items4);
        //set the spinners adapter to the previously created one.
        dropdown4.setAdapter(adapter4);

        dropdown4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //get the spinner from the xml.
        Spinner dropdown5 = (Spinner) view.findViewById(R.id.spinner103);
        //create a list of items for the spinner.
        String[] items5 = new String[]{ "&", "N/A"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items5);
        //set the spinners adapter to the previously created one.
        dropdown5.setAdapter(adapter5);

        dropdown5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        record[7] = 1;
                        break;
                    case 1:
                        record[7] = 2;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //get the spinner from the xml.
        Spinner dropdown = (Spinner) view.findViewById(R.id.spinner104);
        //create a list of items for the spinner.
        String[] items = new String[]{"TIME", "TEMP", "DUSK", "DAWN","N/A"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        record[8] = 1;
                        break;
                    case 1:
                        record[8] = 2;
                        break;
                    case 2:
                        record[8] = 3;
                        break;
                    case 3:
                        record[8] = 4;
                        break;
                    case 4:
                        record[8] = 5;
                        break;
                    case 5:
                        record[8] = 6;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


            /*button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "your toast text", Toast.LENGTH_LONG).show();
                }
            });*/

        return view;
    }

    View.OnClickListener ruleConnectOnClickListener =
            new View.OnClickListener() {
                //button3.ruleClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    editText10.getText().toString();
                    editText11.getText().toString();
                    editText12.getText().toString();
                    editText13.getText().toString();


                    int crc = 0;
                    record[0] = 11;
                    record[1] = 12;
                    record[12]= 0;
                    record[5] = 1;//Integer.parseInt(editText10.toString());//These arrays are hardcoded as we didn't get the values from services team
                    record[6] = 2;//Integer.parseInt(editText10.toString());
                    record[10] =3;//Integer.parseInt(editText10.toString());
                    record[11] =4;//Integer.parseInt(editText10.toString());

                    //HERE calculate





                    for(int e=0;e<13;e++)
                    {
                        crc = crc + record[e];
                    }
                    crc = crc%256;
                    record[13] =  (255-crc);


                    /*Editable host2 = editText10.getText();
                    Editable host5 = editText11.getText();
                    Editable host4 = editText12.getText();
                    Editable host6 = editText13.getText();*/

                    //Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
                    //String spinnerStr = items.getSelectedItem().toString();

                    //record[4] = 0;
                    //updatePrompt("Sending ");
                    //for(int a=0;a<4;a++) {
                      //  updatePrompt(record[a] + " ");
                    //}
                    //updatePrompt("\n");


                    Toast.makeText(getActivity(), "RULE Message Transmitted", Toast.LENGTH_SHORT).show();
                    dstAddress= Tab1Fragment.bridge_ip;




                    ByteBuffer suh = ByteBuffer.allocate(record.length*4);
                    IntBuffer intBuffer = suh.asIntBuffer();
                    intBuffer.put(record);

                    byte[] any= suh.array();

                    for(int i=0; i<14;i++) {
                        buf[i] = any[i*4+3];
                    }

                    /*To send the data
                        udpClientThread = new UdpClientThread(
                                editTextAddress.getText().toString(),
                                message1.getText().toString(),
                                udpClientHandler);
                        udpClientThread.run();
                        Toast.makeText(getActivity(), "Message Transmitted", Toast.LENGTH_SHORT).show();
                        buttonConnect.setEnabled(false);
                    To here*/

                    //sendState("connecting...");

                    //running = true;

                    try {

                        dstAddress= Tab1Fragment.bridge_ip;
                        socket = new DatagramSocket();
                        address = InetAddress.getByName(dstAddress);



                        DatagramPacket packet =
                                new DatagramPacket(buf, buf.length, address, dstPort);
                        socket.send(packet);

                        //sendState("connected");

                        // get response
                        //packet = new DatagramPacket(buf, buf.length);
                        //socket.receive(packet);

                        // String line = new String(packet.getData(), 0, packet.getLength());

                        //  handler.sendMessage(
                        //        Message.obtain(handler, Tab2Fragment.UdpClientHandler.UPDATE_MSG, line));

                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if(socket != null){
                            socket.close();
                            //handler.sendEmptyMessage(Tab2Fragment.UdpClientHandler.UPDATE_END);
                        }
                    }


                    /*
                    List<String> responsePing = new ArrayList<String>();
                    ArrayAdapter<String> adapterList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                            responsePing);

                    try {
                        String cmdPing = "ping -c 4 " + host;

                        Runtime r = Runtime.getRuntime();
                        Process p = r.exec(cmdPing);
                        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String inputLine;
                        //  bridge_ip=inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            responsePing.add(inputLine);
                            state.setAdapter(adapterList);
                        }

                        Toast.makeText(getActivity(), "ping message sent", Toast.LENGTH_SHORT).show();
                        temp= responsePing.get(0); /// to get the Ip string from the sring size of 9


                        bridge_ip=temp.substring(19,31);


                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }*/
                }
            };



}

