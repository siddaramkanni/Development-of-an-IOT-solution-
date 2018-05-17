package com.example.userinterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.userinterface.Tab3Fragment.device_list;

public class Tab7Fragment extends Fragment {
    private static final String TAG = "Tab7Fragment";

    ArrayAdapter<String> m_adapter;
    ArrayList<String> m_listItems = new ArrayList<String>();

    Button update1;
    ListView deviceText;
    int number_of_devices = 0;
    String[] Tab_list = new String[10];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab7_fragment, container, false);
        //inflater.inflate(R.layout.tab7_fragment,container,false);
       // super.onCreate(savedInstanceState);
        update1 = (Button) view.findViewById(R.id.update);
        deviceText = (ListView) view.findViewById(R.id.textDevice);
        m_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, m_listItems);
        deviceText.setAdapter(m_adapter);
        update1.setOnClickListener(update1OnClickListener);
        //setRetainInstance(true);
        return view;
    }

        //update.setOnClickListener(new View.OnClickListener() {
    View.OnClickListener update1OnClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(Tab3Fragment.device_count > 0) {

                    m_listItems.add("updated list");
                    m_adapter.notifyDataSetChanged();

                    for (int i = 0; i < Tab3Fragment.device_count; i++) {
                        String input = Tab3Fragment.device_list[i];
                        m_listItems.add(input);
                        m_adapter.notifyDataSetChanged();
                    }
                    m_listItems.add("\n");
                    m_adapter.notifyDataSetChanged();
               // }
                }
              //  update1.setEnabled(false);

        };


}
/*


        Button bt;
        EditText et;
        ListView lv;
        int number_of_devices = 0;
final String device_list[] = new String[15];

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button) findViewById(R.id.button1);
        et = (EditText) findViewById(R.id.editText1);
        lv = (ListView) findViewById(R.id.listView1);
        m_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, m_listItems);
        lv.setAdapter(m_adapter);
final String input = et.getText().toString();

        bt.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {



        boolean device_not_found = true;
        String input = et.getText().toString();

        if (null != input && input.length() > 0) {
        for(int j =0;j<(number_of_devices+1);j++) {
        if ( input.equals( device_list[j])) {
        device_not_found = false;
        break;
        }
        }
        if (device_not_found)
        {
        //flag = false;
        m_listItems.add(input);
        device_list[number_of_devices] = input;
        number_of_devices++;
        m_adapter.notifyDataSetChanged();
        }
        }
        }

        });
        }
        }


        */

