package com.example.saad.jspart3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class chatActivity2 extends Fragment {

    MultiAutoCompleteTextView mtv;
    Button SendMessage;
    public chatActivity2() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chat_activity2, container, false);


    }

}
