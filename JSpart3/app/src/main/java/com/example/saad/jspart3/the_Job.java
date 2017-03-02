package com.example.saad.jspart3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.net.URI;
import java.net.URL;
import java.util.Map;

public class the_Job extends AppCompatActivity {

    Firebase jobRef;
    Firebase ref;
    Button apply;

    TextView job1Title;
    TextView getKey;
    TextView getTitle;
    TextView getcompany;
    TextView getcity;
    TextView getstate;
    TextView getcountry;
    TextView getformattedLocation;
    TextView getsource;
    TextView getdate;
    TextView getsnippet;
    TextView geturl;
    TextView getsponsored;
    TextView getexpired;
    TextView getcareer;
    TextView getcategory;
    TextView getqualification;
    TextView getnumber;
    TextView getsalary;
    TextView getskill;
    TextView getminexperience;
    TextView getmaxexperience;
    TextView getdepartment;
    TextView getcomment;

    String jobTitle;
    String company;
    String city;
    String state;
    String country;
    String formattedLocation;
    String source;
    String date;
    String snippet;
    String url;
    String latitude;
    String longitude;
    String jobkey;
    String sponsored;
    String expired;
    String formattedLocationFull;
    String formattedRelativeTime;
    String career;
    String category;
    String qualification;
    String number;
    String salary;
    String skill;
    String minExperience;
    String maxExperience;
    String department;
    String comment;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_job);
        Firebase.setAndroidContext(this);
        jobRef = new Firebase("https://js-part-3.firebaseio.com/Jobs/");
        ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/");


        apply = (Button)findViewById(R.id.btnApply);

        job1Title = (TextView)findViewById(R.id.job1title);
        getKey = (TextView)findViewById(R.id.getjobkey);
        getTitle = (TextView)findViewById(R.id.getjobtitle);
        getcompany = (TextView)findViewById(R.id.getcompany_);
        getcity = (TextView)findViewById(R.id.getcity);
        getstate = (TextView)findViewById(R.id.getState);
        getcountry = (TextView)findViewById(R.id.getCountry);
        getformattedLocation = (TextView)findViewById(R.id.getformattedLocation);
        getsource = (TextView)findViewById(R.id.getSource);
        getdate = (TextView)findViewById(R.id.getdate);
        getsnippet = (TextView)findViewById(R.id.getSnippet);
        getsponsored = (TextView)findViewById(R.id.getSponsord);
        getexpired = (TextView)findViewById(R.id.getExpied);
        getcareer = (TextView)findViewById(R.id.getcareer);
        getcategory = (TextView)findViewById(R.id.getcategory);
        getqualification = (TextView)findViewById(R.id.getqualification);
        getnumber = (TextView)findViewById(R.id.getnumber);
        getsalary = (TextView)findViewById(R.id.getsalary);
        getskill = (TextView)findViewById(R.id.getskill);
        getminexperience = (TextView)findViewById(R.id.getminExperience);
        getmaxexperience = (TextView)findViewById(R.id.getmaxExperience);
        getdepartment = (TextView)findViewById(R.id.getdepartment);
        getcomment = (TextView)findViewById(R.id.getcomment);


        final Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null){
             jobTitle = (String) b.get("jobtitle");
             company = (String) b.get("company");
             city = (String) b.get("city");
             state = (String) b.get("state");
            country = (String) b.get("country");
            formattedLocation = (String) b.get("formattedLocation");
            source = (String) b.get("source");
            date = (String) b.get("data");
            snippet = (String) b.get("snippet");
            url = (String) b.get("url");
            latitude = (String) b.get("latitude");
            longitude = (String) b.get("longitude");
            jobkey = (String) b.get("jobkey");
            sponsored = (String) b.get("sponsored");
            expired = (String) b.get("expired");
            formattedLocationFull = (String) b.get("formattedLocationFull");
            formattedRelativeTime = (String) b.get("formattedRelativeTime");
            career = (String)b.get("career");
            category = (String)b.get("category");
            qualification = (String)b.get("qualification");
            number = (String)b.get("number");
            salary = (String)b.get("salary");
            skill = (String)b.get("skill");
            minExperience = (String)b.get("minExperience");
            maxExperience = (String)b.get("maxExperience");
            department = (String)b.get("department");
            comment = (String)b.get("comment");
            key = (String)b.get("email");


        }

        job1Title.setText(jobTitle);
        getKey.setText(jobkey);
        getTitle.setText(jobTitle);
        getcompany.setText(company);
        getcity.setText(city);
        getstate.setText(state);
        getcountry.setText(country);
        getformattedLocation.setText(formattedLocationFull);
        getsource.setText(source);
        getdate.setText(date);
        getsnippet.setText(snippet);
        getsponsored.setText(sponsored);
        getexpired.setText(expired);
        getcareer.setText(career);
        getcategory.setText(category);
        getqualification.setText(qualification);
        getnumber.setText(number);
        getsalary.setText(salary);
        getskill.setText(skill);
        getminexperience.setText(minExperience);
        getmaxexperience.setText(maxExperience);
        getdepartment.setText(department);
        getcomment.setText(comment);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(url);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent1.resolveActivity(getPackageManager())!= null){
                      startActivity(intent1);
                }
                else{

                    Firebase jobk = jobRef.child(jobkey);
                    Firebase emloyerkey = jobk.child("User_Posted");
                    emloyerkey.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            Firebase myentry = ref.child(value.replace(".","/")+"/Users_Applied/"+jobkey+"/");
                            Firebase newEntry = myentry.child(key.replace(".","()"));
                            newEntry.setValue("true");
                            /*FragmentJobSubmitted myfragment = new FragmentJobSubmitted();
                            getSupportFragmentManager().beginTransaction().add(R.id.jobSubmitted, myfragment).addToBackStack(null).commit();*/


                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }
        });

    }

}
