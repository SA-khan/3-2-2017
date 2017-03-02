package com.example.saad.jspart3;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by saad on 12/28/2016.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    public WordAdapter(Activity context, ArrayList<Word> list) {
        super(context, 0, list);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        Word currentWord = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }


        TextView title = (TextView) convertView.findViewById(R.id.titlee);
        title.setText(currentWord.getmTitle());

        TextView company = (TextView) convertView.findViewById(R.id.companyy);
        company.setText(currentWord.getmCompany());

        TextView post_Date = (TextView) convertView.findViewById(R.id.post_date);
        post_Date.setText(currentWord.getmPostDate());

        TextView versionNumberView = (TextView) convertView.findViewById(R.id.location);
        versionNumberView.setText(currentWord.getmCountry() + ", " + currentWord.getmCity());

        ImageView source = (ImageView) convertView.findViewById(R.id.jobImage);

        try {
            if (currentWord.getmSource().equals("")) {

                source.setImageResource(R.drawable.job);
                source.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            else if (currentWord.getmSource().equals("Rozee")) {

                source.setImageResource(R.drawable.rozee_pk);
                source.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            else if (currentWord.getmSource().equals("Job Seekicious")) {

                source.setImageResource(R.drawable.logo1);
                source.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            else if (currentWord.getmSource().equals("Mustakbil")) {

                source.setImageResource(R.drawable.mulogo2);
                source.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            else{

            }
        }
        catch (Exception ex){
            source.setImageResource(R.drawable.job);
            source.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        return convertView;
    }
}
