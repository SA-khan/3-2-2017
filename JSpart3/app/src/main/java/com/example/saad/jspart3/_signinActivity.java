package com.example.saad.jspart3;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;


public class _signinActivity extends AppCompatActivity {

    Toolbar mytoolbar;
    LinearLayout _signinActivityMainTool;
    ImageView _signinActivityUserImage;
    TextInputEditText _signinActivityEmailEditText;
    TextInputEditText _signinActivityPasswordEditText;
    ImageView _signinActivityLoginButton;
    TextView _signinActivityForgetPassword;
    TextView _signinActivitySignUpByGoogle;
    CheckBox _signinActivityRememberMeCheckBox;
    ImageView _signinActivityBusyView;
    ProgressDialog progressDialog;

    Firebase ref;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__signin);
        firebaseAuth = FirebaseAuth.getInstance();
        mytoolbar = (Toolbar)findViewById(R.id._signinActivityToolbar);
        _signinActivityMainTool = (LinearLayout)findViewById(R.id._signinActivityMainTool);
        _signinActivityUserImage = (ImageView) findViewById(R.id._signinActivityUserImage);
        _signinActivityEmailEditText = (TextInputEditText) findViewById(R.id._signinActivityEmailEditText);
        _signinActivityPasswordEditText = (TextInputEditText) findViewById(R.id._signinActivityPasswordEditText);
        _signinActivityRememberMeCheckBox = (CheckBox) findViewById(R.id._signinActivityRememberMeCheckBox);
        _signinActivityLoginButton = (ImageView) findViewById(R.id._signinActivityLoginButton);
        _signinActivityForgetPassword = (TextView) findViewById(R.id._signinActivityForgetPassword);
        _signinActivitySignUpByGoogle = (TextView) findViewById(R.id._signinActivityLoginWithGoogle);
        _signinActivityBusyView = (ImageView) findViewById(R.id._signinActivityBusyView);
        setSupportActionBar(mytoolbar);
        progressDialog = new ProgressDialog(this);

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database");
        //firebaseAuth = FirebaseAuth.getInstance();

        _signinActivityRememberMeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    SharedPreferences sharedPreferences = _signinActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (!(TextUtils.isEmpty(_signinActivityEmailEditText.getText().toString()) && TextUtils.isEmpty(_signinActivityPasswordEditText.getText().toString()))) {
                        editor.putString("SignIn_Email", _signinActivityEmailEditText.getText().toString());
                        editor.putString("SignIn_Password", _signinActivityPasswordEditText.getText().toString());
                        editor.commit();
                    }
                }
                else {
                    SharedPreferences sharedPreferences = _signinActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(!(TextUtils.isEmpty(_signinActivityEmailEditText.getText().toString()) && TextUtils.isEmpty(_signinActivityPasswordEditText.getText().toString())) ) {
                        editor.putString("SignIn_Email", null);
                        editor.putString("SignIn_Password", null);
                        editor.commit();
                    }
                }
            }
        });

        SharedPreferences sharedPreferences = _signinActivity.this.getPreferences(Context.MODE_PRIVATE);
        String shared_email = sharedPreferences.getString("SignIn_Email", null);
        String shared_password = sharedPreferences.getString("SignIn_Password", null);

        if(shared_email != null && shared_password != null){
            try {
                _signinActivityEmailEditText.setText(shared_email);
                _signinActivityPasswordEditText.setText(shared_password);
                _signinActivityRememberMeCheckBox.setChecked(true);
            }
            catch (Exception ex){

            }
        }
        //firebaseAuth = FirebaseAuth.getInstance();
        _signinActivityLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                _signinActivityMainTool.setVisibility(View.GONE);
                _signinActivitySignUpByGoogle.setVisibility(View.GONE);
               // _signinActivityBusyView.setVisibility(View.VISIBLE);
                progressDialog.setMessage("Loading");
                progressDialog.show();
                //Glide.with(getApplicationContext()).load(R.drawable.sign).into(new GlideDrawableImageViewTarget(_signinActivityBusyView));
                final String emailSignin = _signinActivityEmailEditText.toString().trim();
                final String passwordSignin = _signinActivityPasswordEditText.toString().trim();
                firebaseAuth.signInWithEmailAndPassword(_signinActivityEmailEditText.getText().toString(),_signinActivityPasswordEditText.getText().toString()).addOnCompleteListener(_signinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressDialog2.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_SHORT).show();
                            Firebase myChild = ref.child(_signinActivityEmailEditText.getText().toString().replace(".","/"));
                            myChild.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Map<String,String> map = dataSnapshot.getValue(Map.class);
                                    String IsEmployer = map.get("Is_Employer");
                                    if(IsEmployer.equalsIgnoreCase("true")){
                                        Intent myintent = new Intent(_signinActivity.this, user_profile_activity.class);
                                        myintent.putExtra("email", _signinActivityEmailEditText.getText().toString());
                                        myintent.putExtra("password", _signinActivityEmailEditText.getText().toString());
                                        startActivity(myintent);
                                    }
                                    else{
                                        Intent myintent = new Intent(_signinActivity.this, user_profile_activity.class);
                                        myintent.putExtra("email", _signinActivityEmailEditText.getText().toString());
                                        myintent.putExtra("password", _signinActivityEmailEditText.getText().toString());
                                        startActivity(myintent);
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_SHORT).show();
                            _signinActivityMainTool.setVisibility(View.VISIBLE);
                            _signinActivitySignUpByGoogle.setVisibility(View.VISIBLE);
                            //_signinActivityBusyView.setVisibility(View.GONE);
                            progressDialog.hide();
                        }
                    }
                });
            }
        });

        _signinActivityForgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Try to remember", Toast.LENGTH_SHORT).show();
            }
        });

        _signinActivitySignUpByGoogle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Didn't add this functionality yet!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
