package com.example.saad.jspart3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class employees_list extends AppCompatActivity {

    ListView myview;
    Firebase ref;
    Firebase userRef;

    final ArrayList<String> employeelist = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    String email_employer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);
        myview = (ListView)findViewById(R.id.employeesListView);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://js-part-3.firebaseio.com/");
        userRef = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/");

        adapter = new ArrayAdapter<String>(employees_list.this, android.R.layout.simple_list_item_1,employeelist);

        Intent newIntent = getIntent();
        String job = newIntent.getStringExtra("Jobkey");
        final String[] job1 = job.split(",");
        Firebase n = ref.child("Jobs");
        Firebase fireJob = n.child(job1[1]);
        Firebase fireUser = fireJob.child("User_Posted");
        fireUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                email_employer = value;
                Log.d("Employee))))))",email_employer);

                Firebase m = ref.child("SignUp_Database");
                Firebase userPosted = m.child(email_employer);
                Firebase userJob = userPosted.child("Users_Applied");
                Firebase reference = userJob.child(job1[1]);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            //Log.d("data09090",child.getValue().toString());
                            Log.d("data09090",child.getKey().toString());
                            String vc = child.getKey().toString();
                            employeelist.add(vc);
                            adapter.notifyDataSetChanged();

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
        myview.setAdapter(adapter);
        myview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = employeelist.get(position);
                Intent myIntent = new Intent(employees_list.this, employer_employee_relation.class);
                myIntent.putExtra("data" , data);
                myIntent.putExtra("employer_key" , email_employer);
                myIntent.putExtra("jobkey",job1[1]);
                startActivity(myIntent);
            }
        });
        /*Firebase m = ref.child("SignUp_Database");
        Log.d("Employee))))))",email_employer);
        Firebase userPosted = m.child(email_employer);
        Firebase userJob = m.child("Users_Applied");
        Firebase reference = userJob.child(job1[1]);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Log.d("data09090",child.getValue().toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/


    }
}
