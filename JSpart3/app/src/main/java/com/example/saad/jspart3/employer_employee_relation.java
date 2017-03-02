package com.example.saad.jspart3;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Map;

public class employer_employee_relation extends FragmentActivity {

    Intent myintent;
    Firebase ref;
    Firebase EmployeeId;

    String employeer_key_data;
    String employer_Jobkey;
    TextView eeEmployerName;
    ImageView eeEmployerDP;

    String user1;
    String employee_Email;
    TextView eeEmployeeName;
    ImageView eeEmployeeDP;
    TextView eeEmployeeAccount;
    TextView eeEmployeeEmail;
    TextView eeEmployeeDOB;
    TextView eeEmployeeGender;
    TextView eeEmployeeCity;
    TextView eeEmployeeCountry;

    ImageView eeEmployeeSkype;
    ImageView eeEmployeeMessage;
    ImageView eeEmployeeCV;

    LinearLayout myEmployeeTable;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        try{
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_employer_employee_relation);
            //setContentView(R.layout.fragment_chat_activity2);

            eeEmployerName = (TextView)findViewById(R.id.eeEmployerName);
            eeEmployerDP = (ImageView)findViewById(R.id.eeEmployerDP);
            eeEmployeeName = (TextView)findViewById(R.id.eeEmployeeName);
            eeEmployeeDP = (ImageView)findViewById(R.id.eeEmployeeDP);
            eeEmployeeAccount = (TextView) findViewById(R.id.eeEmployeeAccount);
            eeEmployeeEmail = (TextView) findViewById(R.id.eeEmployeeEmail);
            eeEmployeeDOB = (TextView) findViewById(R.id.eeEmployeeDOB);
            eeEmployeeGender = (TextView) findViewById(R.id.eeEmployeeGender);
            eeEmployeeCity = (TextView) findViewById(R.id.eeEmployeeCity);
            eeEmployeeCountry = (TextView) findViewById(R.id.eeEmployeeCountry);
            eeEmployeeSkype = (ImageView)findViewById(R.id.eeEmployeeSkype);
            eeEmployeeMessage = (ImageView)findViewById(R.id.eeEmployeeMessage);
            eeEmployeeCV = (ImageView)findViewById(R.id.eeEmployeeCV);


            myEmployeeTable = (LinearLayout)findViewById(R.id.eeEmployeeTable);


            Firebase.setAndroidContext(this);
            ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database");
            myintent = getIntent();
            user1 = myintent.getStringExtra("data");
            String employees_list_data = myintent.getStringExtra("data");
            employeer_key_data = myintent.getStringExtra("employer_key");
            employer_Jobkey    = myintent.getStringExtra("jobkey");

            Firebase employerId = ref.child(employeer_key_data);
            employerId.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String,String> map = dataSnapshot.getValue(Map.class);
                    eeEmployerDP.bringToFront();
                    Picasso.with(employer_employee_relation.this).load(map.get("Image_URL")).into(eeEmployerDP);
                    eeEmployerName.setText(map.get("First_Name"));

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            Log.d("employee-->>"," "+employees_list_data);
            EmployeeId = ref.child(employees_list_data.replace("()","/"));
            EmployeeId.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String,String> map = dataSnapshot.getValue(Map.class);
                    eeEmployeeName.setText(map.get("First_Name")+" "+map.get("Last_Name"));
                    Picasso.with(employer_employee_relation.this).load(map.get("Image_URL")).into(eeEmployeeDP);
                    eeEmployeeDP.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(map.get("Is_Employee").equals("true")) {
                        eeEmployeeAccount.setText("Employee");
                    }
                    else {
                        eeEmployeeAccount.setText("Employer");
                    }
                    employee_Email = map.get("Email_Address");
                    eeEmployeeEmail.setText(employee_Email);
                    eeEmployeeDOB.setText(map.get("Date_of_Birth"));
                    if(map.get("Is_Male").equals("true")) {
                        eeEmployeeGender.setText("Male");
                    }
                    else{
                        eeEmployeeGender.setText("Female");
                    }
                    eeEmployeeCity.setText(map.get("City"));
                    eeEmployeeCountry.setText(map.get("Country"));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }
        catch (Exception ex){
            Log.d("Exception: "," "+ex.getMessage());
        }
        try {

            eeEmployeeSkype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initiateSkypeUri(employer_employee_relation.this, "assaykhan");
                }
            });
        }catch (Exception ex){}
        eeEmployeeMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Firebase jobkey = ref.child(employeer_key_data+"/"+employer_Jobkey+"/");

                   /* Firebase messageKey = EmployeeId.child("Messages/"+employer_Jobkey);
                    String generatedKey = messageKey.push().getKey();
                    Firebase employerKey = messageKey.child(employeer_key_data);
                    Firebase employeeMessagekey = employerKey.child(generatedKey);
                    Firebase source = employeeMessagekey.child("Source");
                    source.setValue(employeer_key_data);
                    Firebase destination = employeeMessagekey.child("Destination");
                    destination.setValue(employee_Email);
                    Firebase message = employeeMessagekey.child("Message");
                    message.setValue("The message");
                    Time now = new Time();
                    now.setToNow();
                    Firebase date = employeeMessagekey.child("Posted_Date");
                    date.setValue(now);*/
/*
                getSupportFragmentManager().beginTransaction().add(R.id.eeEmployeeTable,new chatActivity2(),"test").addToBackStack(null).commit();
                Fragment fragment = new chatActivity2();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.eeEmployeeTable, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
*/
// Check that the activity is using the layout version with
                // the fragment_container FrameLayout
               /* if (findViewById(R.id.fragment_container) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.
                    if (savedInstanceState != null) {
                        return;
                    }

                    // Create a new Fragment to be placed in the activity layout
                    getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.eeEmployeeTable, new chatActivity2()).addToBackStack(null).commit();
                    // In case this activity was started with special instructions from an
                    // Intent, pass the Intent's extras to the fragment as arguments
                    //firstFragment.setArguments(getIntent().getExtras());
*/
                // Add the fragment to the 'fragment_container' FrameLayout

                // }

                Intent myintent = new Intent(getApplicationContext(), _chatActivity.class);
                myintent.putExtra("Sender",employeer_key_data);
                myintent.putExtra("user1", user1);
                myintent.putExtra("Reciever", employee_Email);
                startActivity(myintent);
                        /*Firebase messageKey = EmployeeId.child("Messages");
                        String generatedKey = messageKey.push().getKey();
                        Firebase RandomKey = messageKey.child(generatedKey);
                        Firebase source = RandomKey.child("Source");
                        source.setValue(employeer_key_data);
                        Firebase destination = RandomKey.child("Destination");
                        destination.setValue(employee_Email);
                        Firebase message = RandomKey.child("Message");
                        message.setValue("You have been shortlisted for the interview.");
                        Time now = new Time();
                        now.setToNow();
                        Firebase date = RandomKey.child("Posted_Date");
                        date.setValue(now);
                        Toast.makeText(getApplicationContext(), "Message Send", Toast.LENGTH_SHORT).show();*/
                        /*Intent myintent = new Intent(employer_employee_relation.this, ChatActivity.class);
                        startActivity(myintent);*/


            }
        });
        eeEmployeeCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase cv = EmployeeId.child("CV_URL");
                cv.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        if(value == null){
                            eeEmployeeCV.setVisibility(View.GONE);
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

    }
    /**
     * Initiate the actions encoded in the specified URI.
     */
    public void initiateSkypeUri(Context myContext, String mySkypeUri) {

        // Make sure the Skype for Android client is installed.
        if (!isSkypeClientInstalled(myContext)) {
            goToMarket(myContext);
            return;
        }

        // Create the Intent from our Skype URI.
        Uri skypeUri = Uri.parse(mySkypeUri);
        Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);

        // Restrict the Intent to being handled by the Skype for Android client only.
        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Initiate the Intent. It should never fail because you've already established the
        // presence of its handler (although there is an extremely minute window where that
        // handler can go away).
        myContext.startActivity(myIntent);

        return;
    }

    public boolean isSkypeClientInstalled(Context myContext) {
        PackageManager myPackageMgr = myContext.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }
    public void goToMarket(Context myContext) {
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(myIntent);

        return;
    }
}
