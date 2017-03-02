package com.example.saad.jspart3;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by saad on 2/16/2017.
 */
public class MessageAdaptor extends ArrayAdapter<MessageWord> {
    public MessageAdaptor(Activity context, ArrayList<MessageWord> list) {
        super(context, 0, list);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        MessageWord currentWord = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_layout, parent, false);
        }





        TextView versionNameView = (TextView) convertView.findViewById(R.id.sender);
        versionNameView.setText(currentWord.getSender());

        TextView versionNumberView = (TextView) convertView.findViewById(R.id.date);
        versionNumberView.setText(currentWord.getPostdate());

        return convertView;
    }
}
