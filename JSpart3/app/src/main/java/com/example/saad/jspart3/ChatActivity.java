package com.example.saad.jspart3;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    Firebase ref;
    Intent myintent;
    String sender_data;
    String key_data;
    String reciever_data;

    ListView MessageView;
    EditText chatappWriteMessage;
    Button chatappSendButton;

    Firebase userFrom;

    ArrayList<MessageWord> list = new ArrayList<MessageWord>();

    String Sender_Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/");


        myintent = getIntent();
        sender_data = myintent.getStringExtra("Sender");
        key_data = myintent.getStringExtra("Key");
        reciever_data = myintent.getStringExtra("Reciever");
        final String user1 = myintent.getStringExtra("user1");
        Log.d("reciever-->>", " "+reciever_data);
        Log.d("sender-->>", " "+sender_data);
        Log.d("user1-->>", " "+user1);
        MessageView = (ListView)findViewById(R.id.chatappMessagesView);
        chatappWriteMessage = (EditText)findViewById(R.id.chatappMessage);
        chatappSendButton = (Button)findViewById(R.id.chatappSend);
        //chatappSendButton.setEnabled(false);
        final MessageAdaptor adaptor = new MessageAdaptor(this, list);
        try {
            Firebase userTo = ref.child(reciever_data);

            Firebase message = userTo.child("Messages");

            final Firebase key = message.child(key_data);

            userFrom = ref.child(user1.replace(".","/"));
            Firebase message2 = userFrom.child("Messages");
            final Firebase key2 = message2.child(key_data);

            key.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, String> map = dataSnapshot.getValue(Map.class);
                    final String Msg = map.get("Message");
                    String Destination = map.get("Destination");
                    String Source = map.get("Source");
                    Sender_Data = map.get("MessageKey");
                    Log.d("Source-->>", " " + Source);
                    Log.d("Destination-->>", " " + Destination);
                    Log.d("MessageKey-->>", " " + Sender_Data);
                    Log.d("Message-->>", " " + Msg);
                    Firebase date = key.child("Posted_Date");
                    date.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, String> map = dataSnapshot.getValue(Map.class);
                            String date = String.valueOf(map.get("monthDay"));
                            String month = String.valueOf(map.get("month"));
                            String year = String.valueOf(map.get("year"));
                            Log.d("date-->>", " " + date);
                            Log.d("month-->>", " " + month);
                            Log.d("yeare-->>", " " + year);
                            list.add(new MessageWord(Msg, "" + date + "-" + month + "-" + year));
                            adaptor.notifyDataSetChanged();
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

            key.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        final Firebase thread = key.child("Thread");
                        thread.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    final String clubkey = childSnapshot.getKey();
                                    Firebase messagekey = thread.child(clubkey);
                                    messagekey.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String value = dataSnapshot.getValue(String.class);
                                            list.add(new MessageWord(value, ""));
                                            adaptor.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(FirebaseError firebaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });


                    } catch (Exception ex) {

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            chatappSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Message = chatappWriteMessage.getText().toString();
                    /*if (Message != null) {
                        Firebase newMessage = key.child("Thread");
                        newMessage.push().setValue(Message);
                        Firebase newMessage2 = key2.child("Thread");
                        newMessage2.push().setValue(Message);
                    }*/
                    //String messageWrite = mtv.getText().toString();
                    //Firebase messageKey = senderRef.child("Messages");
                    String generatedKey = key.push().getKey();
                    Firebase RandomKey = key.child(generatedKey);

                   // Firebase messageKey2 = recieverRef.child("Messages");
                    String generatedKey2 = key2.push().getKey();
                    Firebase RandomKey2 = key2.child(generatedKey2);
                    Firebase msgKey2 = RandomKey2.child("MessageKey");

                    Firebase msgKey = RandomKey.child("MessageKey");
                    msgKey.setValue(generatedKey2);
                    Firebase source = RandomKey.child("Source");
                    source.setValue(user1);
                    Firebase destination = RandomKey.child("Destination");
                    destination.setValue(sender_data);
                    Firebase message = RandomKey.child("Message");
                    message.setValue(Message);
                    Time now = new Time();
                    now.setToNow();
                    Firebase date = RandomKey.child("Posted_Date");
                    date.setValue(now);



                    msgKey2.setValue(generatedKey);
                    Firebase source2 = RandomKey2.child("Source");
                    source2.setValue(user1);
                    Firebase destination2 = RandomKey2.child("Destination");
                    destination2.setValue(sender_data);
                    Firebase message2 = RandomKey2.child("Message");
                    message2.setValue(Message);
                    Time now2 = new Time();
                    now2.setToNow();
                    Firebase date2 = RandomKey2.child("Posted_Date");
                    date2.setValue(now2);

                    Toast.makeText(getApplicationContext(), "Message Send", Toast.LENGTH_SHORT).show();
                }
            });

            MessageView.setAdapter(adaptor);

        }
        catch (Exception ex){
            Log.d("Exception: ",""+ex.getMessage());
        }
    }
}
