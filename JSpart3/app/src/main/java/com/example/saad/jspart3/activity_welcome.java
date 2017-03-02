package com.example.saad.jspart3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.saad.jspart3.helpers.DocumentHelper;
import com.example.saad.jspart3.helpers.IntentHelper;
import com.example.saad.jspart3.helpers.NotificationHelper;
import com.example.saad.jspart3.imgurmodel.ImageResponse;
import com.example.saad.jspart3.imgurmodel.Upload;
import com.example.saad.jspart3.services.UploadService;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Downloader;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.File;

public class activity_welcome extends AppCompatActivity {

    public static final int check = 1;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;

    private Firebase Database;
    private Firebase SignUpDatabase;
    private Firebase key;
    private Firebase FireFirstName;
    private Firebase FireLastName;
    private Firebase FireEmailAddress;
    private Firebase FirePassword;
    private Firebase FireGenderMale;
    private Firebase FireGenderFemale;
    private Firebase FireEmployer;
    private Firebase FireEmployee;
    private Firebase FireCountry;
    private Firebase FireCity;
    private Firebase FireDateOfBirth;
    private Firebase FireCV;
    private Firebase FireImage;
    private Firebase FireAgreementChecked;
    EditText sFirstName;
    EditText sLastName;
    EditText sEmailAddress;
    EditText sPassword;
    RadioGroup sGender;
    RadioButton sGenderMale;
    RadioButton sGenderFemale;
    RadioGroup sSignUpAs;
    RadioButton sSignUpAsEmployer;
    RadioButton getsSignUpAsEmployee;
    EditText sCountry;
    EditText sCity;
    EditText sDateOfBirth;
    EditText sCvEdittext;
    EditText sImageEdittext;
    Button sCvButton;
    Button sImageButton;
    EditText sAgreement;
    CheckBox sTermsAgreementCheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        Database = new Firebase("https://js-part-3.firebaseio.com/");



        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
       /* if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        } */

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_activity_welcome);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched



                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    //launchHomeScreen();

                    SharedPreferences sharedPref = activity_welcome.this.getPreferences(Context.MODE_PRIVATE);
                    String FirstName = sharedPref.getString("First_Name",null);
                    String LastName = sharedPref.getString("Last_Name",null);
                    final String EmailAddress = sharedPref.getString("Email_Address",null);
                    String Password = sharedPref.getString("Password",null);
                    String GenderMale = sharedPref.getString("Gender_Male",null);
                    String GenderFemale = sharedPref.getString("Gender_Female",null);
                    final String IsEmployer = sharedPref.getString("Is_Employer",null);
                    String IsEmployee = sharedPref.getString("Is_Employee",null);
                    String DateOfBirth = sharedPref.getString("Date_of_Birth",null);
                    String Country = sharedPref.getString("Country",null);
                    String City = sharedPref.getString("City",null);
                    String CVURL = sharedPref.getString("CV_URL",null);
                    String ImageURL = sharedPref.getString("Image_URL",null);
                    String AgreementChecked = sharedPref.getString("AgreementChecked",null);

                    firebaseAuth = FirebaseAuth.getInstance();
                    SignUpDatabase = Database.child("SignUp_Database");
                    //final String a = SignUpDatabase.push().getKey();
                    String abc = EmailAddress;
                    String ab = abc.replace(".","/");
                    key = SignUpDatabase.child(ab);
                    //FireFirstName = key.child("First_Name");
                    //FireFirstName.setValue(FirstName);
                    //FireLastName = key.child("Last_Name");
                    //FireLastName.setValue(LastName);


                    FireFirstName = key.child("First_Name");
                    FireFirstName.setValue(FirstName);
                    FireLastName = key.child("Last_Name");
                    FireLastName.setValue(LastName);
                    FireEmailAddress = key.child("Email_Address");
                    FireEmailAddress.setValue(EmailAddress);
                    FirePassword = key.child("Password");
                    FirePassword.setValue(Password);
                    FireGenderMale = key.child("Is_Male");
                    FireGenderMale.setValue(GenderMale);
                    FireGenderFemale = key.child("Is_Female");
                    FireGenderFemale.setValue(GenderFemale);
                    FireEmployer = key.child("Is_Employer");
                    FireEmployer.setValue(IsEmployer);
                    FireEmployee = key.child("Is_Employee");
                    FireEmployee.setValue(IsEmployee);
                    FireCountry = key.child("Country");
                    FireCountry.setValue(Country);
                    FireCity = key.child("City");
                    FireCity.setValue(City);
                    FireDateOfBirth = key.child("Date_of_Birth");
                    FireDateOfBirth.setValue(DateOfBirth);
                    FireCV = key.child("CV_URL");
                    FireCV.setValue(CVURL);
                    FireImage = key.child("Image_URL");
                    FireImage.setValue(ImageURL);
                    FireAgreementChecked = key.child("Agreement_Checked");
                    FireAgreementChecked.setValue(AgreementChecked);
                    firebaseAuth.createUserWithEmailAndPassword(EmailAddress,Password).addOnCompleteListener(activity_welcome.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(activity_welcome.this,"Data Successfully saved",Toast.LENGTH_SHORT).show();

                        }
                    });

                    finish();
                    if(Boolean.parseBoolean(IsEmployer)){
                        Firebase Post_Job = key.child("Post_Job");
                        Intent myintent = new Intent(activity_welcome.this,PostJob1.class);
                        myintent.putExtra("email", EmailAddress);
                        startActivity(myintent);
                    }
                    else{
                        Intent myintent = new Intent(activity_welcome.this,SearchJob1.class);
                        myintent.putExtra("email", EmailAddress);
                        startActivity(myintent);
                    }
                }
                try {
                    if (current == 1) {

                        sFirstName = (EditText)findViewById(R.id.sFirstName);
                        sLastName = (EditText)findViewById(R.id.sLastName);
                        sEmailAddress = (EditText)findViewById(R.id.sEmailAddress);
                        sPassword = (EditText)findViewById(R.id.sPassword);

                        String FirstName = sFirstName.getText().toString();
                        String LastName = sLastName.getText().toString();
                        String EmailAddress = sEmailAddress.getText().toString();
                        String Password = sPassword.getText().toString();

                        if(!TextUtils.isEmpty(FirstName) || !TextUtils.isEmpty(LastName) || !TextUtils.isEmpty(EmailAddress) || !TextUtils.isEmpty(Password)) {
                            btnNext.setEnabled(true);

                            SharedPreferences sharedPref = activity_welcome.this.getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("First_Name", sFirstName.getText().toString());
                            editor.putString("Last_Name", sLastName.getText().toString());
                            editor.putString("Email_Address", sEmailAddress.getText().toString());
                            editor.putString("Password", sPassword.getText().toString());

                            editor.commit();
                        }
                        else {
                            btnNext.setEnabled(false);
                        }




                    }
                    if(current == 2){

                        sGender = (RadioGroup)findViewById(R.id.sGenderRadioGroup);
                        sGenderMale = (RadioButton)findViewById(R.id.sGenderRadioButtonMale);
                        sGenderFemale = (RadioButton)findViewById(R.id.sGenderRadioButtonFemale);
                        sSignUpAs = (RadioGroup)findViewById(R.id.sSignUpAsRadioGroup);
                        sSignUpAsEmployer= (RadioButton)findViewById(R.id.sSignUpAsRadioButtonEmployer);
                        getsSignUpAsEmployee= (RadioButton)findViewById(R.id.sSignUpAsRadioButtonEmployee);
                        sCountry = (EditText)findViewById(R.id.sCountry);
                        sCity = (EditText)findViewById(R.id.sCity);
                        sDateOfBirth = (EditText)findViewById(R.id.sDateOfBirth);

                            SharedPreferences sharedPref = activity_welcome.this.getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("Gender_Male", String.valueOf(sGenderMale.isChecked()));
                            editor.putString("Gender_Female", String.valueOf(sGenderFemale.isChecked()));
                            editor.putString("Is_Employer", String.valueOf(sSignUpAsEmployer.isChecked()));
                            editor.putString("Is_Employee", String.valueOf(getsSignUpAsEmployee.isChecked()));
                            editor.putString("Date_of_Birth", sDateOfBirth.getText().toString());
                            editor.putString("Country", sCountry.getText().toString());
                            editor.putString("City", sCity.getText().toString());
                            editor.commit();

                    }
                    if(current == 3){
                        //sCvEdittext = (EditText)findViewById(R.id.sCVTextview);
                        //sCvButton = (Button)findViewById(R.id.sCVButton);
                        sImageEdittext = (EditText)findViewById(R.id.sImageTextview);
                        sImageButton = (Button)findViewById(R.id.sImageButton);

                        ////////////////////////////////////////////////////////////////
                        sCvButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(activity_welcome.this,"Clicked",Toast.LENGTH_SHORT).show();
                            }
                        });

                        sImageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(activity_welcome.this,imgur_activity.class));
                            }
                        });

                        //////////////////////////////////////////////////////////////////


                        // Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref = activity_welcome.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("CV_URL", sCvEdittext.getText().toString());
                        editor.putString("Image_URL", sImageEdittext.getText().toString());
                        editor.commit();



                    }
                    if(current == 4){
                        sAgreement = (EditText) findViewById(R.id.sAgreement);
                        sTermsAgreementCheckbox = (CheckBox)findViewById(R.id.sAgreementClicked);

                        SharedPreferences sharedPref = activity_welcome.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("AgreementChecked", String.valueOf(sTermsAgreementCheckbox.isChecked()));
                        editor.commit();

                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),""+ex.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Exception ",""+ex.getMessage());
                }



            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(activity_welcome.this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
    public void notWorking(View view){
        //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(activity_welcome.this,imgur_activity.class));
        //sImageEdittext = (EditText)findViewById(R.id.sImageTextview);
        //sImageEdittext.setText("");
        storageReference = FirebaseStorage.getInstance().getReference();

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("*/*");
        startActivityForResult(intent, 4);

/*        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://js-part-3.appspot.com/CV/");

        Uri file = Uri.fromFile(new File("data/data/file-path/file-name"));
        Log.d("file", file.getPath());


        StorageReference riversRef = storageRef.child("firebase-storage");

        UploadTask uploadTask = riversRef.putFile(file);
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d("uploadFail", "" + exception);

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //sendNotification("upload backup", 1);

                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                Log.d("downloadUrl", "" + downloadUrl);
            }
        });*/
    }


    public void ImageUpload(View view){
        storageReference = FirebaseStorage.getInstance().getReference();
        //sCvEdittext = (EditText)findViewById(R.id.sCVTextview);
        Intent newIntent = new Intent(Intent.ACTION_PICK);
        newIntent.setType("image/*");
        //newIntent.setData("asd");
        startActivityForResult(newIntent,3);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 3 && resultCode == RESULT_OK){
            Uri uri = data.getData();

            sImageEdittext = (EditText)findViewById(R.id.sImageTextview);
            StorageReference filepath = storageReference.child("Images").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    sImageEdittext.setText(String.valueOf(downloadUri));
                    Toast.makeText(getApplicationContext(),"Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(requestCode == 4 && resultCode == RESULT_OK){
            Uri uuri = data.getData();
            //sCvEdittext = (EditText)findViewById(R.id.sCVTextview);
            final StorageReference Ref = storageReference.child("CVs").child(uuri.getLastPathSegment());
            Ref.putFile(uuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sCvEdittext.setText(String.valueOf(taskSnapshot.getDownloadUrl()));
                    Toast.makeText(getApplicationContext(),"File Uploaded", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
