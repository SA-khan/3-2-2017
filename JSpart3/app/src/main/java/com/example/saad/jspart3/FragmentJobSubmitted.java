package com.example.saad.jspart3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saad.jspart3.R;

/**
 * Created by saad on 2/25/2017.
 */
public class FragmentJobSubmitted extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_job_submitted, container, false);
        //return view;
    }
}
