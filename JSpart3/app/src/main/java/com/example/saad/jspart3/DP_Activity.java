package com.example.saad.jspart3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DP_Activity extends AppCompatActivity {

    ImageView src;
    ImageView cancelImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp_);

        src = (ImageView)findViewById(R.id.dpImage);
        cancelImage = (ImageView)findViewById(R.id.cancelImage);

        Intent myintent = getIntent();
        String pictureURLrecieved = myintent.getStringExtra("Image_URL");
        Picasso.with(DP_Activity.this).load(pictureURLrecieved).into(src);
        src.setScaleType(ImageView.ScaleType.FIT_CENTER);
        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
