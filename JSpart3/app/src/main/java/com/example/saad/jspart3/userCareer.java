package com.example.saad.jspart3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;

public class userCareer extends AppCompatActivity {

    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_career);

        Intent myintent = getIntent();
        String email = myintent.getStringExtra("Email_Address");

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/");
        Firebase child1 = ref.child(email.replace(".","/"));


    }
}
