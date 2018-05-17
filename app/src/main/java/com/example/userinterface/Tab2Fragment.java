package com.example.userinterface;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

    EditText editTextAddress, message1;
    Button buttonConnect;
    TextView textViewState, textViewRx;

    UdpClientHandler udpClientHandler;
    UdpClientThread udpClientThread;
    private Button btnTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        editTextAddress = (EditText) view.findViewById(R.id.address);
        message1 = (EditText) view.findViewById(R.id.message1);
        buttonConnect = (Button) view.findViewById(R.id.connect);
        textViewState = (TextView)view.findViewById(R.id.state);
        textViewRx = (TextView)view.findViewById(R.id.received);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        udpClientHandler = new UdpClientHandler(this);
        setRetainInstance(true);
        return view;
    }

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    udpClientThread = new UdpClientThread(
                            editTextAddress.getText().toString(),
                            message1.getText().toString(),
                            udpClientHandler);
                     udpClientThread.run();
                    Toast.makeText(getActivity(), "Message Transmitted", Toast.LENGTH_SHORT).show();
                    buttonConnect.setEnabled(false);
                }
            };

    private void updateState(String state){
        textViewState.setText(state);
    }

    private void updateRxMsg(String rxmsg){
        textViewRx.append(rxmsg + "\n");
    }

    private void clientEnd(){
        udpClientThread = null;
        buttonConnect.setEnabled(true);

    }

    public static class UdpClientHandler extends Handler {
        public static final int UPDATE_STATE = 0;
        public static final int UPDATE_MSG = 1;
        public static final int UPDATE_END = 2;
        private Tab2Fragment parent;

        public UdpClientHandler(Tab2Fragment parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_STATE:
                    parent.updateState((String)msg.obj);
                    break;
                case UPDATE_MSG:
                    parent.updateRxMsg((String)msg.obj);
                    break;
                case UPDATE_END:
                    parent.clientEnd();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
}
