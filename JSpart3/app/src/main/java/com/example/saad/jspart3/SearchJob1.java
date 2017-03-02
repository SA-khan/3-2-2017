package com.example.saad.jspart3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchJob1 extends AppCompatActivity {

    Button btn1;
    String key;
    TextView userEmail;
    EditText jobTitle;
    EditText city;
    EditText state;
    EditText country;
    ImageView dp;
    Firebase ref;

    private static final String TAG = "MainActivity" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job1);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/");

        jobTitle = (EditText)findViewById(R.id.jobTitle);
        city = (EditText)findViewById(R.id.city);
        state = (EditText)findViewById(R.id.state);
        country = (EditText)findViewById(R.id.country);
        userEmail = (TextView)findViewById(R.id.anonymousEmail);
        dp = (ImageView)findViewById(R.id.anonymousDp);
        btn1 = (Button)findViewById(R.id.btn1);

        try {
            Intent myIntent = getIntent();
            String email = myIntent.getStringExtra("email");
            key = email;
            String NewEmail = email.replace(".", "/");
            Firebase New_Email_Firebase = ref.child(NewEmail);
            Firebase key = New_Email_Firebase.child("First_Name");
            key.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    userEmail.setText(value);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            Firebase key2 = New_Email_Firebase.child("Image_URL");
            key2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    String v = value.replace("\"","");
                    try {
                        //dp.setImageBitmap(picture(v));
                       // dp.setImageURI(Uri.parse(v));
                        Picasso.with(SearchJob1.this).load(Uri.parse(v)).into(dp);
                    }
                    /*
                        InputStream in = new java.net.URL(v).openStream();
                        dp.setImageBitmap(BitmapFactory.decodeStream(in));
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }*/
                    catch (Exception e) {
                            Log.e("Error?????????????????", e.getMessage());
                            e.printStackTrace();
                        }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }
        catch (Exception ex){
            //finish();
            //Toast.makeText(getApplicationContext(),"Please First Sign In",Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getApplicationContext(),SignIn1.class));
            //Log.d("","");
        }



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((TextUtils.isEmpty(jobTitle.getText().toString()) && (TextUtils.isEmpty(country.getText().toString())) && (TextUtils.isEmpty(city.getText().toString())))){
                    Toast.makeText(SearchJob1.this,"Fields are empty..",Toast.LENGTH_SHORT).show();
                }
                else if(((TextUtils.isEmpty(country.getText().toString())) || (TextUtils.isEmpty(city.getText().toString())))){
                    Toast.makeText(SearchJob1.this,"Fields are empty..",Toast.LENGTH_SHORT).show();
                }
                else{
                Intent myIntent = new Intent(SearchJob1.this, JobList.class);
                    String title1 = jobTitle.getText().toString();
                    String country1 = country.getText().toString();
                    String city1 = city.getText().toString();
                    String state1 = state.getText().toString();
                    String a = title1.trim();
                    String aa = a.replace(" ", "%20");
                    String b = country1.trim();
                    String bb = b.replace(" ","_");
                    String c = city1.trim();
                    String cc = c.replace(" ", "_");
                    String d = state1.trim();
                    String dd = d.replace(" ", "_");
                myIntent.putExtra("title", title1);
                myIntent.putExtra("country", bb);
                myIntent.putExtra("city", cc);
                myIntent.putExtra("state", dd);
                myIntent.putExtra("email",key);
                startActivity(myIntent);
                }
            }
        });

    }
    public Bitmap picture(String path){
        try {
            URL myURL = new URL(path);
            HttpURLConnection connection = (HttpURLConnection)myURL.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String completeData = "";
            String tempData = null;
            while ((tempData = bufferedReader.readLine())!= null){
                completeData += tempData;
            }
            Bitmap picture = BitmapFactory.decodeFile(completeData);
            return picture;
        }
        catch (Exception ex){
            return null;
        }
    }

}
