package com.example.saad.jspart3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class _chatActivity extends AppCompatActivity {

    MultiAutoCompleteTextView mtv;
    Button SendMessage;

    Firebase recieverRef;
    Firebase senderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__chat);
        Firebase.setAndroidContext(this);

        try {

            Intent myintent = getIntent();
            final String employer = myintent.getStringExtra("Sender");
            final String employee = myintent.getStringExtra("Reciever");

            recieverRef = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/" + employee.replace(".", "/") + "/");
            senderRef = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/" + employer.replace(".", "/") + "/");

            mtv = (MultiAutoCompleteTextView) findViewById(R.id.AddMessage);
            SendMessage = (Button) findViewById(R.id.SendMessage);

            SendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String messageWrite = mtv.getText().toString();
                    Firebase messageKey = senderRef.child("Messages");
                    String generatedKey = messageKey.push().getKey();
                    Firebase RandomKey = messageKey.child(generatedKey);

                    Firebase messageKey2 = recieverRef.child("Messages");
                    String generatedKey2 = messageKey2.push().getKey();
                    Firebase RandomKey2 = messageKey2.child(generatedKey2);
                    Firebase msgKey2 = RandomKey2.child("MessageKey");

                    Firebase msgKey = RandomKey.child("MessageKey");
                    msgKey.setValue(generatedKey2);
                    Firebase source = RandomKey.child("Source");
                    source.setValue(employer);
                    Firebase destination = RandomKey.child("Destination");
                    destination.setValue(employee);
                    Firebase message = RandomKey.child("Message");
                    message.setValue(messageWrite);
                    Time now = new Time();
                    now.setToNow();
                    Firebase date = RandomKey.child("Posted_Date");
                    date.setValue(now);



                    msgKey2.setValue(generatedKey);
                    Firebase source2 = RandomKey2.child("Source");
                    source2.setValue(employer);
                    Firebase destination2 = RandomKey2.child("Destination");
                    destination2.setValue(employee);
                    Firebase message2 = RandomKey2.child("Message");
                    message2.setValue(messageWrite);
                    Time now2 = new Time();
                    now2.setToNow();
                    Firebase date2 = RandomKey2.child("Posted_Date");
                    date2.setValue(now2);

                    Toast.makeText(getApplicationContext(), "Message Send", Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception ex){
            Log.d("Exception: "," "+ex.getMessage());
        }

    }
}
