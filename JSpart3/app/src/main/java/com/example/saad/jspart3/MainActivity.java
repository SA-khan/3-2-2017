package com.example.saad.jspart3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView saying1;
    protected int splashTime = 5000;
    String[] quotes = {"Become a CEO of your career destiny.","Choose a job you love, and you will never have to work a day in your life. confucious.","Your career is your business. Its time for you to manage it as a CEO.","Be so good they can't ignore you. Steve Martin","Its a beautiful thing when a career and a passion come together."};
    int timer = 0;

    Button signin_btn;
    Button signup_btn;

    TextView searchJob1_btn;
    TextView postJob1_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saying1 = (TextView)findViewById(R.id.quote);
        final Thread saying = new Thread(){
            @Override
            public void run() {
                try {
                    for (timer = 0 ; timer < 10 ; timer++){
                        int waited = 0;
                        while (waited < splashTime){
                            Thread.sleep(100);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                       saying1.setText(quotes[timer]);
                                    }
                                    catch (Exception ex){

                                    }
                                }
                            });
                            waited += 50;
                            if(timer == 8){
                                timer = 0;
                            }

                        }
                    }
                }
                catch (Exception ex){

                }
            }
        };
        saying.start();

        signin_btn = (Button)findViewById(R.id.sign_in_btn);
        signup_btn = (Button)findViewById(R.id.signup_btn);
        searchJob1_btn = (TextView) findViewById(R.id.searchJob1_btn);
        postJob1_btn = (TextView) findViewById(R.id.postJob1_btn);

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,_signinActivity.class);
                startActivity(intent);
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,activity_welcome.class);
                startActivity(intent);
            }
        });

        searchJob1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchJob1.class);
                startActivity(intent);
            }
        });
        postJob1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PostJob1.class);
                startActivity(intent);
            }
        });
    }
}
