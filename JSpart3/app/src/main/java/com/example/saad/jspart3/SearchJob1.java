package com.example.saad.jspart3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.ArrayList;

public class SearchJob1 extends AppCompatActivity {

    Button btn1;
    String key;
    TextView userEmail;
    EditText jobTitle;
    EditText city;
    EditText state;
    EditText country;
    ImageView dp;
    Spinner countrylist;
    Spinner citylist;
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
        countrylist = (Spinner) findViewById(R.id.jobCountryList);
        citylist = (Spinner) findViewById(R.id.jobCityList);
        citylist.setVisibility(View.GONE);

        /*ComboBox mycombo = new ComboBox(this);
        mycombo._text.setText("Type here");
        mycombo.setVisibility(View.VISIBLE);*/

        String[] countries = new String[]{"Select Country..", "Pakistan", "India", "United States"};
        ArrayAdapter<String> county_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        countrylist.setAdapter(county_adapter);

        /*String[] cities = new String[]{};
        ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(SearchJob1.this, android.R.layout.simple_spinner_dropdown_item, cities);
        citylist.setAdapter(city_adapter);*/

        countrylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = getIntent();
                finish();
                startActivity(intent);*/
                System.gc();
                if(countrylist.getSelectedItem() == "Pakistan") {
                    citylist.setVisibility(View.VISIBLE);
                    String[] cities = new String[]{"Select City..","Karachi", "Hyderabad", "Lahore", "Islamabad"};
                    ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(SearchJob1.this, android.R.layout.simple_spinner_dropdown_item, cities);
                    citylist.setAdapter(city_adapter);
                }
                else if(countrylist.getSelectedItem() == "India"){
                    citylist.setVisibility(View.VISIBLE);
                    String[] cities = new String[]{"Select City..","Mumbai", "Calcutta", "New Dehli", "Sri Nagar"};
                    ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(SearchJob1.this, android.R.layout.simple_spinner_dropdown_item, cities);
                    citylist.setAdapter(city_adapter);
                }
                else if(countrylist.getSelectedItem() == "United States"){
                    citylist.setVisibility(View.VISIBLE);
                    String[] cities = new String[]{"Select City..","Diego", "San Francisco", "New York", "Los Angelos"};
                    ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(SearchJob1.this, android.R.layout.simple_spinner_dropdown_item, cities);
                    citylist.setAdapter(city_adapter);
                }
                else {
                    citylist.setVisibility(View.GONE);
                    String[] cities = new String[]{};
                    ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(SearchJob1.this, android.R.layout.simple_spinner_dropdown_item, cities);
                    citylist.setAdapter(city_adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

                if((TextUtils.isEmpty(jobTitle.getText().toString()) && (TextUtils.isEmpty(countrylist.getSelectedItem().toString())) && (TextUtils.isEmpty(citylist.getSelectedItem().toString())))){
                    Toast.makeText(SearchJob1.this,"Fields are empty..",Toast.LENGTH_SHORT).show();
                }
                else if(((TextUtils.isEmpty(countrylist.getSelectedItem().toString())) || (TextUtils.isEmpty(citylist.getSelectedItem().toString())))){
                    Toast.makeText(SearchJob1.this,"Fields are empty..",Toast.LENGTH_SHORT).show();
                }
                else{
                Intent myIntent = new Intent(SearchJob1.this, JobList.class);
                    String title1 = jobTitle.getText().toString();
                    String country1 = countrylist.getSelectedItem().toString();
                    String city1 = citylist.getSelectedItem().toString();
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


    public class ComboBox extends LinearLayout {

        private AutoCompleteTextView _text;
        private ImageButton _button;

        public ComboBox(Context context) {
            super(context);
            this.createChildControls(context);
        }

        public ComboBox(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.createChildControls(context);
        }

        private void createChildControls(Context context) {
            this.setOrientation(HORIZONTAL);
            this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));

            _text = new AutoCompleteTextView(context);
            _text.setSingleLine();
            _text.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_NORMAL
                    | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
                    | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
            _text.setRawInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            this.addView(_text, new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT, 1));

            _button = new ImageButton(context);
            _button.setImageResource(android.R.drawable.arrow_down_float);
            _button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    _text.showDropDown();
                }
            });
            this.addView(_button, new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
        }

        /**
         * Sets the source for DDLB suggestions.
         * Cursor MUST be managed by supplier!!
         * @param source Source of suggestions.
         * @param column Which column from source to show.
         */
        public void setSuggestionSource(Cursor source, String column) {
            String[] from = new String[] { column };
            int[] to = new int[] { android.R.id.text1 };
            SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this.getContext(),
                    android.R.layout.simple_dropdown_item_1line, source, from, to);
            // this is to ensure that when suggestion is selected
            // it provides the value to the textbox
            cursorAdapter.setStringConversionColumn(source.getColumnIndex(column));
            _text.setAdapter(cursorAdapter);
        }

        /**
         * Gets the text in the combo box.
         *
         * @return Text.
         */
        public String getText() {
            return _text.getText().toString();
        }

        /**
         * Sets the text in combo box.
         */
        public void setText(String text) {
            _text.setText(text);
        }
    }

}
