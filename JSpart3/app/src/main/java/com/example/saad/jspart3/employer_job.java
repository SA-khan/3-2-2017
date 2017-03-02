package com.example.saad.jspart3;

import android.content.Intent;
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

import java.util.Map;

public class employer_job extends AppCompatActivity {

    Firebase ref;

    TextView activityHeading;
    TextView activityTotal;
    Button editJob;

    TextView id;
    TextView title;
    TextView desc;
    TextView category;
    TextView company;
    TextView domain;
    TextView location;
    TextView country;
    TextView city;
    TextView coordinates;
    TextView career;
    TextView qualification;
    TextView number;
    TextView salary;
    TextView skill;
    TextView postDate;
    TextView expiry;
    TextView minExperience;
    TextView maxExperience;
    TextView department;
    TextView comment;
    TextView postedFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_job);

        activityHeading = (TextView)findViewById(R.id.activityHeading);
        activityTotal = (TextView)findViewById(R.id.appliedTotal);
        editJob = (Button)findViewById(R.id.editEmployerJobPosted);
        id = (TextView)findViewById(R.id.myEmployerJobKey);
        title = (TextView)findViewById(R.id.myEmployerJobTitle);
        desc = (TextView)findViewById(R.id.myEmployerJobDescription);
        category = (TextView)findViewById(R.id.myEmployerJobCategory);
        company = (TextView)findViewById(R.id.myEmployerJobCompany);
        domain = (TextView)findViewById(R.id.myEmployerJobCompanyDomain);
        location = (TextView)findViewById(R.id.myEmployerJobLocation);
        country = (TextView)findViewById(R.id.myEmployerJobCountry);
        city = (TextView)findViewById(R.id.myEmployerJobCity);
        coordinates = (TextView)findViewById(R.id.myEmployerJobCoordinates);
        career = (TextView)findViewById(R.id.myEmployerJobCareerLevel);
        qualification = (TextView)findViewById(R.id.myEmployerJobQualification);
        number = (TextView)findViewById(R.id.myEmployerJobInNumbers);
        salary = (TextView)findViewById(R.id.myEmployerJobSalary);
        skill = (TextView)findViewById(R.id.myEmployerJobSkill);
        postDate = (TextView)findViewById(R.id.myEmployerJobPostedDate);
        expiry = (TextView)findViewById(R.id.myEmployerJobExpired);
        minExperience = (TextView)findViewById(R.id.myEmployerJobMinExperience);
        maxExperience = (TextView)findViewById(R.id.myEmployerJobMaximumExperience);
        department = (TextView)findViewById(R.id.myEmployerJobDepartment);
        comment = (TextView)findViewById(R.id.myEmployerJobComment);
        postedFrom = (TextView)findViewById(R.id.myEmployerJobPostedThrough);

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://js-part-3.firebaseio.com/");
        Intent myIntent = getIntent();
        final String log = myIntent.getStringExtra("Jobkey");
        Log.d("Dar", log);

        final String[] job1 = log.split(",");
        Firebase n = ref.child("Jobs");
        final Firebase fireJob = n.child(job1[1]);
        Firebase fireUser = fireJob.child("User_Posted");
        fireUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("Employee))))))", value);

                Firebase m = ref.child("SignUp_Database");
                Firebase userPosted = m.child(value);
                Firebase userJob = userPosted.child("Users_Applied");
                Firebase reference = userJob.child(job1[1]);
                userJob.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            //Log.d("data09090",child.getValue().toString());
                            Log.d("data09090", child.getKey().toString());
                            String vc = child.getKey().toString();
                            if(vc.equals(job1[1])){
                            long vcc = child.getChildrenCount();
                            Log.d("counter_________", "" + vcc);
                                activityTotal.setText("" + vcc);
                            }


                        }
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


        fireJob.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Map<String, String> map = dataSnapshot.getValue(Map.class);
                    id.append(job1[1]);
                    title.append(map.get("JobTitle"));
                    desc.append(map.get("JobDescription"));
                    category.append(map.get("JobCategory"));
                    company.append(map.get("JobCompany"));
                    domain.append(map.get("JobDomain"));
                    location.append(map.get("JobLocation"));
                    country.append(map.get("JobCountry"));
                    city.append(map.get("JobCity"));
                    coordinates.append(map.get("JobLatitude") + ", " + map.get("JobLongitude"));
                    career.append(map.get("EmployeeCareerLevel"));
                    qualification.append(map.get("EmployeesEducation"));
                    number.append(map.get("EmployeeInNumber"));
                    salary.append(map.get("EmployeeSalary"));
                    skill.append(map.get("EmployeeSkillSet"));
                    postDate.append(map.get("Post_Date"));
                    expiry.append(map.get("Expiry"));
                    minExperience.append(map.get("Minimum_Experience"));
                    maxExperience.append(map.get("Maximum_Experience"));
                    department.append(map.get("Department"));
                    comment.append(map.get("comment"));
                    postedFrom.append(map.get("source"));
                }
                catch (Exception ex){
                    Log.d("Exception: "," "+ex.getMessage());
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
                activityHeading.setText(log);
                activityTotal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent = new Intent(employer_job.this,employees_list.class);
                        newIntent.putExtra("Jobkey",log);
                        startActivity(newIntent);


            }

        });
    }
}