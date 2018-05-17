package com.example.userinterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button button2;
    private EditText editText3 ;
    private ListView state;
    public static String bridge_ip;
    public String temp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        button2 = (Button) view.findViewById(R.id.button2);
        editText3 = (EditText) view.findViewById(R.id.editText3);
        state = (ListView) view.findViewById(R.id.state);
        button2.setOnClickListener(buttonConnectOnClickListener);

        return view;
    }


    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Editable host = editText3.getText();
             List<String> responsePing = new ArrayList<String>();
             ArrayAdapter<String> adapterList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                     responsePing);

             try {
                 String cmdPing = "ping -c 4 " + host;

                 Runtime r = Runtime.getRuntime();
                 Process p = r.exec(cmdPing);
                 BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                 String inputLine;
                 while ((inputLine = in.readLine()) != null) {
                     responsePing.add(inputLine);
                     state.setAdapter(adapterList);
                 }

                 Toast.makeText(getActivity(), "ping message sent", Toast.LENGTH_SHORT).show();
                 temp= responsePing.get(0);                                                         // to get the Ip string from the sring size of 9

               //    bridge_ip=temp.substring(19,31);    /// To support the Ip address given in the edit text
                 bridge_ip=temp.substring(17,30);        /// To support the dns / netbios  address given in the edit text

             } catch (Exception e) {
                 Toast.makeText(getActivity(), "Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();

             }
         }
     };



}
