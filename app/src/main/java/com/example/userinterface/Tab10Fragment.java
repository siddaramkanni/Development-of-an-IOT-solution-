package com.example.userinterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Tab10Fragment extends Fragment {
    private static final String TAG = "Tab10Fragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab10_fragment, container, false);
        super.onCreate(savedInstanceState);
        inflater.inflate(R.layout.tab10_fragment, container, false);


        return view;
    }
}