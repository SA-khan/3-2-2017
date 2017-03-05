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
import android.util.Log;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;


public class _signinActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private Button btngoAccount;
    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess;
    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;


    Toolbar mytoolbar;
    LinearLayout _signinActivityMainTool;
    ImageView _signinActivityUserImage;
    TextInputEditText _signinActivityEmailEditText;
    TextInputEditText _signinActivityPasswordEditText;
    ImageView _signinActivityLoginButton;
    TextView _signinActivityForgetPassword;
    LinearLayout _signinActivitySignUpByGoogle;
    CheckBox _signinActivityRememberMeCheckBox;
    ImageView _signinActivityBusyView;
    ProgressDialog progressDialog;

    Firebase ref;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__signin);

        //GoogleApiClient myclient = new GoogleApiClient();

        btngoAccount = (Button)findViewById(R.id.btn_Account);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        btnSignIn.setOnClickListener(this);
        btngoAccount.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnRevokeAccess.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());


        firebaseAuth = FirebaseAuth.getInstance();
        mytoolbar = (Toolbar)findViewById(R.id._signinActivityToolbar);
        _signinActivityMainTool = (LinearLayout)findViewById(R.id._signinActivityMainTool);
        _signinActivityUserImage = (ImageView) findViewById(R.id._signinActivityUserImage);
        _signinActivityEmailEditText = (TextInputEditText) findViewById(R.id._signinActivityEmailEditText);
        _signinActivityPasswordEditText = (TextInputEditText) findViewById(R.id._signinActivityPasswordEditText);
        _signinActivityRememberMeCheckBox = (CheckBox) findViewById(R.id._signinActivityRememberMeCheckBox);
        _signinActivityLoginButton = (ImageView) findViewById(R.id._signinActivityLoginButton);
        _signinActivityForgetPassword = (TextView) findViewById(R.id._signinActivityForgetPassword);
        _signinActivitySignUpByGoogle = (LinearLayout) findViewById(R.id._signinActivityLoginWithGoogle);
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
                //_signinActivityMainTool.setVisibility(View.GONE);
               // _signinActivitySignUpByGoogle.setVisibility(View.GONE);
               // _signinActivityBusyView.setVisibility(View.VISIBLE);

                //Glide.with(getApplicationContext()).load(R.drawable.sign).into(new GlideDrawableImageViewTarget(_signinActivityBusyView));
                final String emailSignin = _signinActivityEmailEditText.toString().trim();
                final String passwordSignin = _signinActivityPasswordEditText.toString().trim();
                if(!(_signinActivityEmailEditText.getText().toString().equals("") || _signinActivityPasswordEditText.getText().toString().equals("")))
                {
                    try {
                        progressDialog.setMessage("Loading");
                        progressDialog.show();
                        firebaseAuth.signInWithEmailAndPassword(_signinActivityEmailEditText.getText().toString(), _signinActivityPasswordEditText.getText().toString()).addOnCompleteListener(_signinActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressDialog2.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                                    Firebase myChild = ref.child(_signinActivityEmailEditText.getText().toString().replace(".", "/"));
                                    myChild.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Map<String, String> map = dataSnapshot.getValue(Map.class);
                                            String IsEmployer = map.get("Is_Employer");
                                            if (IsEmployer.equalsIgnoreCase("true")) {
                                                Intent myintent = new Intent(_signinActivity.this, user_profile_activity.class);
                                                myintent.putExtra("email", _signinActivityEmailEditText.getText().toString());
                                                myintent.putExtra("password", _signinActivityEmailEditText.getText().toString());
                                                startActivity(myintent);
                                            } else {
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


                                } else {
                                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                    _signinActivityMainTool.setVisibility(View.VISIBLE);
                                    _signinActivitySignUpByGoogle.setVisibility(View.VISIBLE);
                                    //_signinActivityBusyView.setVisibility(View.GONE);
                                    progressDialog.hide();
                                }
                            }
                        });
                    }
                    catch (Exception ex){

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter All Fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _signinActivityForgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Try to remember", Toast.LENGTH_SHORT).show();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = _signinActivityEmailEditText.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Email", "Email sent.");
                                    View parentLayout = findViewById(R.id._signin_scroll_view);
                                    Snackbar.make( parentLayout, "Email is sent.", Snackbar.LENGTH_LONG ).setAction("Check Email?", new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String email_data = _signinActivityEmailEditText.getText().toString();
                                            int detectAT = email_data.indexOf("@");
                                            String data = email_data.substring(detectAT+1);
                                            if(data.startsWith("gmail")) {
                                                Uri webpage = Uri.parse("http://www.gmail.com/");
                                                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                                                if (intent.resolveActivity(getPackageManager()) != null) {
                                                    startActivity(intent);
                                                }
                                            }
                                            else if(data.startsWith("hotmail") || data.startsWith("live")) {
                                                Uri webpage = Uri.parse("http://www.hotmail.com/");
                                                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                                                if (intent.resolveActivity(getPackageManager()) != null) {
                                                    startActivity(intent);
                                                }
                                            }
                                            else if(data.startsWith("yahoo")) {
                                                Uri webpage = Uri.parse("http://www.yahoo.com/");
                                                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                                                if (intent.resolveActivity(getPackageManager()) != null) {
                                                    startActivity(intent);
                                                }
                                            }
                                            else {
                                                Uri webpage = Uri.parse("http://www.google.com/");
                                                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                                                if (intent.resolveActivity(getPackageManager()) != null) {
                                                    startActivity(intent);
                                                }
                                            }


                                        }
                                    }).show();
                                }
                            }
                        });
            }
        });

        _signinActivitySignUpByGoogle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Didn't add this functionality yet!!!", Toast.LENGTH_SHORT).show();

            }
        });

        _signinActivityPasswordEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email_address = _signinActivityEmailEditText.getText().toString();
                    if (!(email_address.equals(""))) {
                        String email_data = email_address.replace(".", "/");
                        Firebase signup_firebase = ref.child(email_data);
                        Firebase image_firebase = signup_firebase.child("Image_URL");
                        image_firebase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String value = dataSnapshot.getValue(String.class);
                                Log.d("image_url", " " + value);
                                _signinActivityUserImage.invalidate();
                                Picasso.with(_signinActivity.this).load(value).into(_signinActivityUserImage);
                                //_signinActivityUserImage.setImageURI(Uri.parse(value));
                                _signinActivityUserImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                /*_signinActivityUserImage.setMaxHeight(100);
                                _signinActivityUserImage.setMaxWidth(100);*/
                                _signinActivityUserImage.invalidate();
                                _signinActivityUserImage.postInvalidate();
                               /* _signinActivityUserImage.jumpDrawablesToCurrentState();
                                if(!(_signinActivityUserImage.isShown())){
                                    _signinActivityUserImage.setImageResource(R.drawable.user_anonymous);
                                }*/
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                Toast.makeText(getApplicationContext(), "Firebase Erroe: "+firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                catch (Exception ex){
                    Log.d("Exception in image", " "+ex.getMessage());
                }
            }
        });


    }



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void goAccount(){
        Intent myintent = new Intent(_signinActivity.this, user_profile_activity.class);
        myintent.putExtra("email", _signinActivityEmailEditText.getText().toString());
        myintent.putExtra("password", _signinActivityEmailEditText.getText().toString());
        startActivity(myintent);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            txtName.setText(personName);
            txtEmail.setText(email);
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);

            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;

            case R.id.btn_Account:
                goAccount();
                break;

            case R.id.btn_sign_out:
                signOut();
                break;

            case R.id.btn_revoke_access:
                revokeAccess();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btngoAccount.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnRevokeAccess.setVisibility(View.VISIBLE);
            llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btngoAccount.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.GONE);
            btnRevokeAccess.setVisibility(View.GONE);
            llProfileLayout.setVisibility(View.GONE);
        }
    }

}
