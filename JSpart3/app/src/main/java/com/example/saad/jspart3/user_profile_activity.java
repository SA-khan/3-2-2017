package com.example.saad.jspart3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import static com.example.saad.jspart3.R.drawable.anonymous;

public class user_profile_activity extends AppCompatActivity {

    final ArrayList<String> joblist = new ArrayList<String>();
    Intent myintent;
    Firebase ref;
    Firebase jobRef;
    String key;
    ListView lissView;
    String FULL_NAME = "";
    String PICTURE;
    String Email_Address;
    int flag = 0;
    String pictureURL;

    ImageView rootView;
    TextView userName;
    TextView userEmail;
    TextView signupAs;
    TextView gender;
    TextView dOb;
    TextView country;
    TextView city;
    Button searchJob;
    Button postJob;
    TextView mypostedjob;
    ImageView userMessage;
    ImageView userResume;
    ImageView userCareer;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final CollapsingToolbarLayout tb = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        rootView = (ImageView) findViewById(R.id.user_profile_dp);
        userName = (TextView)findViewById(R.id.text_user1);
        userEmail = (TextView)findViewById(R.id.text_user1_email);
        signupAs = (TextView)findViewById(R.id.text_user1_signup_as);
        gender = (TextView)findViewById(R.id.text_user1_gender);
        dOb = (TextView)findViewById(R.id.text_user1_dOb);
        country = (TextView)findViewById(R.id.text_user1_country);
        city = (TextView)findViewById(R.id.text_user1_city);
        searchJob = (Button)findViewById(R.id.search_job_btn);
        postJob = (Button)findViewById(R.id.post_job_btn);
        mypostedjob = (TextView)findViewById(R.id.mypostedjobs);
        userMessage = (ImageView)findViewById(R.id.userMessage);
        userResume = (ImageView)findViewById(R.id.userResume);
        userCareer = (ImageView)findViewById(R.id.userCareer);
        lissView = (ListView)findViewById(R.id.lissView);


        myintent = getIntent();

        String employees_list_data = myintent.getStringExtra("data");
        String employeer_key_data = myintent.getStringExtra("employer_key");
        if(employees_list_data == null){
        key = myintent.getStringExtra("email");
        final String key2 = key.replace(".","/");
        Email_Address = key;}
        else {
            flag = 1;
            key = employees_list_data.replace("()","/");
            Email_Address = key;
            Log.d("Emai Address: ",Email_Address);
        }
        if(flag == 1){

        }
        ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/"+key.replace(".","/")+"/");
        Firebase image = ref.child("Image_URL");
        image.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                pictureURL = value;
                String v = value.replace("\"","");
                try {
                    //toolbar_layout.setBackground(Drawable.createFromStream(v));
                    Picasso.with(user_profile_activity.this).load(v).into(rootView);

                }

                catch (Exception e) {
                    Log.e("Error?????????????????", e.getMessage());
                    e.printStackTrace();
                }
                PICTURE = value;
                Log.d("data_image",value);
                //rootView.bringToFront();

                try {
                    URL muUrl = new URL(value);
                    HttpURLConnection con = (HttpURLConnection)muUrl.openConnection();
                    con.setDoInput(true);
                    con.connect();

                    InputStream inputStream = con.getInputStream();
                    Bitmap bbm = BitmapFactory.decodeStream(inputStream);
                    toolbar.setBackgroundDrawable(Drawable.createFromStream(inputStream, null));
                    tb.setBackgroundDrawable(Drawable.createFromStream(inputStream, null));
                    tb.setBackgroundDrawable(Drawable.createFromPath(value));

                    //toolbar.setBackground(Drawable.createFromStream(inputStream, "saad"));
                    /*InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String completeData = null;
                    String tempData = "";
                    while ((tempData = bufferedReader.readLine())!= null){
                        completeData+=tempData;
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);*/
                    //CollapsingToolbarLayout toolbar_layout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
                    //toolbar_layout.setBackground(Drawable.createFromPath(value));
                    //toolbar_layout.setBackgroundResource(R.drawable.anonymous);
                }
                catch (Exception ex){

                }




            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase name = ref.child("First_Name");
        name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String value = dataSnapshot.getValue(String.class);
                userName.append(value);
                Firebase last = ref.child("Last_Name");
                last.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String vvalue = dataSnapshot.getValue(String.class);
                        userName.append(" "+vvalue);
                        FULL_NAME = value+" "+vvalue;


                        //if(actionBar!=null){
                            actionBar.setTitle(FULL_NAME);
                        //}

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase email = ref.child("Email_Address");
        email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                userEmail.append(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase signup = ref.child("Is_Employee");
        signup.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("Data1",value);
                if(value.equals("true")){
                    postJob.setVisibility(View.GONE);
                    mypostedjob.setVisibility(View.GONE);
                    lissView.setVisibility(View.GONE);
                    signupAs.append("Employee");
                }
                else {
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(user_profile_activity.this,android.R.layout.simple_list_item_1,joblist);
                    searchJob.setVisibility(View.GONE);
                    signupAs.append("Employer");
                    try {
                        jobRef = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/"+key.replace(".","/")+"/Posted_Jobs/Job IDs/");
                        jobRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ///////////
                                //Log.d("data----"," "+dataSnapshot.getValue(String.class));
                                //Log.d("dada", dataSnapshot.getValue().toString());
                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                    final String clubkey = childSnapshot.getKey();
                                    //Log.d("jobb____ID",childSnapshot.getValue(String.class));

                                    Firebase data = jobRef.child(clubkey);
                                    data.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            final String value = dataSnapshot.getValue(String.class);

                                            Firebase jobName = new Firebase("https://js-part-3.firebaseio.com/Jobs/"+value+"/");
                                            Firebase jobTitle = jobName.child("JobTitle");
                                            jobTitle.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String jb = dataSnapshot.getValue(String.class);
                                                    joblist.add(jb+" ,"+value);
                                                    //country.append(jb);
                                                    adapter.notifyDataSetChanged();

                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {

                                                }
                                            });
                                            Log.d("Job<><><><><>",value);

                                        }

                                        @Override
                                        public void onCancelled(FirebaseError firebaseError) {

                                        }
                                    });


                                }

                                lissView.setAdapter(adapter);
                                //////////
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        lissView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String data = joblist.get(position);
                                Intent intent = new Intent(user_profile_activity.this, employer_job.class);
                                intent.putExtra("Jobkey", data);
                                //Log.d("dadaddaa",data);
                                startActivity(intent);
                            }
                        });
                    }
                    catch (Exception ex){

                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase sex = ref.child("Is_Male");
        sex.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("data2",value);
                if(value.equals("true"))
                    gender.append("Male");
                else
                    gender.append("Female");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase dateOf = ref.child("Date_of_Birth");
        dateOf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                dOb.append(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase watan = ref.child("Country");
        watan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                country.append(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Firebase sheher = ref.child("City");
        sheher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                city.append(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase cv = ref.child("CV_URL");
                cv.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        if(value == null){
                            fab.setVisibility(View.GONE);
                        }
                        else {
                            Uri webpage = Uri.parse(value);
                            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
        Button fab2 = (Button) findViewById(R.id.fab2);
        fab2.bringToFront();
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });
        Button fab3 = (Button) findViewById(R.id.fab3);
        fab3.bringToFront();
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        searchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(user_profile_activity.this,SearchJob1.class);
                myintent.putExtra("email",Email_Address);
                startActivity(myintent);
            }
        });
        postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(user_profile_activity.this,PostJob1.class);
                myintent.putExtra("email",Email_Address);
                startActivity(myintent);
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myintent = new Intent(user_profile_activity.this,DP_Activity.class);
                myintent.putExtra("Image_URL", pictureURL);
                startActivity(myintent);
            }
        });
        userMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(user_profile_activity.this,userMessages.class);
                myintent.putExtra("Image_URL", pictureURL);
                myintent.putExtra("Email_Address", Email_Address);
                startActivity(myintent);
            }
        });
        userResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase cv = ref.child("CV_URL");
                cv.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        if(value == null){
                            fab.setVisibility(View.GONE);
                        }
                        else {
                            Uri webpage = Uri.parse(value);
                            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });
        userCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(user_profile_activity.this,userCareer.class);
                myintent.putExtra("Image_URL", pictureURL);
                myintent.putExtra("Email_Address", Email_Address);
                //myintent.putExtra("Image_URL", pictureURL);
                startActivity(myintent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.title_bar_gray)));
        actionBar.setTitle(FULL_NAME);
        actionBar.show();
    }
}
