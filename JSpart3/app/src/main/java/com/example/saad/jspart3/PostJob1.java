package com.example.saad.jspart3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.util.TimeUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.util.Calendar.MONDAY;

public class PostJob1 extends AppCompatActivity{

    Firebase ref;
    Firebase uRef;
    TextView ppname;
    ImageView ppdp;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;

    EditText xJobTitle;
    EditText xJobDescription;
    EditText xJobCategory;
    EditText xJobCompany;
    EditText xJobDomain;
    EditText xJobLocation;
    EditText xJobCountry;
    EditText xJobCity;
    EditText xJobLongitude;
    EditText xJobLatitude;
    Spinner xEmployeeCareerLevel;
    Spinner xEmployeeEducation;
    Spinner xEmployeeInNumbers;
    Spinner xEmployeeSalary;
    EditText xEmployeeSkillSet;
    EditText xJobExpiry;
    EditText xMinimumExperience;
    EditText xMaximumExperience;
    EditText xDepartment;
    EditText xComment;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        try {
            Intent myintent = getIntent();
            key = myintent.getStringExtra("email").replace(".", "/");

        }
        catch (Exception ex){

            /*if (TextUtils.isEmpty(key.toString())) {*/
                RelativeLayout postjob = (RelativeLayout) findViewById(R.id.postJob_layout);
                Snackbar.make(getWindow().getDecorView().getRootView(), "First You have to SIGN IN.", Snackbar.LENGTH_INDEFINITE).setAction("Click to Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myintent = new Intent(PostJob1.this, _signinActivity.class);
                        startActivity(myintent);
                    }
                }).show();
           /* }*/
        }
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://js-part-3.firebaseio.com/Jobs/");
        uRef = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/"+key+"/");


        try {
            Firebase data = uRef.child("First_Name");
            data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ppname = (TextView)findViewById(R.id.ppptv1);
                    String name = dataSnapshot.getValue(String.class);
                    ppname.setText(name);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            Firebase data2 = uRef.child("Image_URL");
            data2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ppdp = (ImageView)findViewById(R.id.pppdp1);
                    String url = dataSnapshot.getValue(String.class);
                    try {
                        Picasso.with(PostJob1.this).setLoggingEnabled(true);
                        Picasso.with(PostJob1.this).load(url).into(ppdp);

                    }
                    catch (Exception ex){
                        Toast.makeText(PostJob1.this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }
        catch (Exception ex){

        }


        //setContentView(R.layout.activity_post_job1);
        prefManager = new PrefManager(this);

// Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_post_job1);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.xelcome_signup1,
                R.layout.xelcome_signup2,
                R.layout.xelcome_signup3,
                R.layout.xelcome_signup4};

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
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if(current == 1){
                    xJobTitle = (EditText)findViewById(R.id.xJobTitle);
                    xJobDescription = (EditText)findViewById(R.id.xJobDescription);
                    xJobCategory = (EditText)findViewById(R.id.xJobCategory);
                    xJobCompany = (EditText)findViewById(R.id.xJobCompany);
                    //xJobDomain = (EditText)findViewById(R.id.xJobDomain);
                    String jobTitle = xJobTitle.getText().toString();
                    String jobTDescription = xJobDescription.getText().toString();
                    String jobCategory = xJobCategory.getText().toString();
                    String jobCompany = xJobCompany.getText().toString();
                    //String jobDomain = xJobDomain.getText().toString();
                    SharedPreferences sharedPreferences = PostJob1.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor Editor = sharedPreferences.edit();
                    Editor.putString("JOB_TITLE", jobTitle);
                    Editor.putString("JOB_DESC", jobTDescription);
                    Editor.putString("JOB_CATEGORY", jobCategory);
                    Editor.putString("JOB_COMPANY", jobCompany);
                    //Editor.putString("JOB_DOMAIN", jobDomain);
                    Editor.commit();
                }
                if(current == 2){
                    xJobLocation = (EditText)findViewById(R.id.xLocation);
                    xJobCountry = (EditText)findViewById(R.id.xJobCountry);
                    xJobCity = (EditText)findViewById(R.id.xJobCity);
                    //xJobLongitude = (EditText)findViewById(R.id.xJobLongitude);
                    //xJobLatitude = (EditText)findViewById(R.id.xJobLatitude);
                    String jobLocation = xJobLocation.getText().toString();
                    String jobCountry = xJobCountry.getText().toString();
                    String jobCity = xJobCity.getText().toString();
                    //String jobLongitude = xJobLongitude.getText().toString();
                    //String jobLatitude = xJobLatitude.getText().toString();
                    SharedPreferences sharedPreferences = PostJob1.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor Editor = sharedPreferences.edit();
                    Editor.putString("JOB_LOCATION", jobLocation);
                    Editor.putString("JOB_COUNTRY", jobCountry);
                    Editor.putString("JOB_CITY", jobCity);
                    //Editor.putString("JOB_LONGITUDE", jobLongitude);
                    //Editor.putString("JOB_LATITUDE", jobLatitude);
                    Editor.commit();
                }
                if(current == 3){
                    xEmployeeCareerLevel = (Spinner)findViewById(R.id.xEmployeeCareerLevel);
                    xEmployeeEducation = (Spinner)findViewById(R.id.xEmployeeEducation);
                    xEmployeeInNumbers = (Spinner)findViewById(R.id.xEmployeeNumbers);
                    xEmployeeSalary = (Spinner)findViewById(R.id.xEmployeeSalary);
                    xEmployeeSkillSet = (EditText)findViewById(R.id.xEmployeeSkillSet);
                   // String[] careerLevel = new String[] {"Student/Intern", "Entry Level", "Experienced", "Professional/Experienced", "Department Head" , "G.M / C.E.O/ Country Head / President"};
                    //ArrayAdapter<String> careerAdapter = new ArrayAdapter<String>(PostJob1.this, android.R.layout.simple_spinner_dropdown_item, careerLevel);
                    //xEmployeeCareerLevel.setAdapter(careerAdapter);
                    xEmployeeCareerLevel.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                    xEmployeeEducation.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                    xEmployeeInNumbers.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                    xEmployeeSalary.setOnItemSelectedListener(new CustomOnItemSelectedListener());

                    String employeeCareerLevel = xEmployeeCareerLevel.getSelectedItem().toString();
                    String employeeEducation = xEmployeeEducation.getSelectedItem().toString();
                    String employeeInNumber = xEmployeeInNumbers.getSelectedItem().toString();
                    String employeeSalary = xEmployeeSalary.getSelectedItem().toString();
                    String employeeSkillSet = xEmployeeSkillSet.getText().toString();
                    SharedPreferences sharedPreferences = PostJob1.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor Editor = sharedPreferences.edit();
                    Editor.putString("EMPLOYEE_CAREER_LEVEL", employeeCareerLevel);
                    Editor.putString("EMPLOYEE_EDUCATION", employeeEducation);
                    Editor.putString("EMPLOYEE_IN_NUMBER", employeeInNumber);
                    Editor.putString("EMPLOYEE_SALARY", employeeSalary);
                    Editor.putString("EMPLOYEE_SKILL_SET", employeeSkillSet);
                    Editor.commit();
                }
                if(current == 4){
                    xJobExpiry = (EditText)findViewById(R.id.xExpiry);
                    //xMinimumExperience = (EditText)findViewById(R.id.xMinimumExperience);
                    //xMaximumExperience = (EditText)findViewById(R.id.xMaximumExperience);
                    //xDepartment = (EditText)findViewById(R.id.xDepartment);
                    //xComment = (EditText)findViewById(R.id.xComment);
                    String JobExpiry = xJobExpiry.getText().toString();
                    String MinimumExperience = xMinimumExperience.getText().toString();
                    String MaximumExperience = xMaximumExperience.getText().toString();;
                    String Department = xDepartment.getText().toString();;
                    String Comment = xComment.getText().toString();
                    SharedPreferences sharedPreferences = PostJob1.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor Editor = sharedPreferences.edit();
                    Editor.putString("Job_Expiry", JobExpiry);
                    Editor.putString("Minimum_Experience", MinimumExperience);
                    Editor.putString("Maximum_Experience", MaximumExperience);
                    Editor.putString("Department", Department);
                    Editor.putString("Comment", Comment);
                    Editor.commit();
                }
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    SharedPreferences sharedPreferences = PostJob1.this.getPreferences(Context.MODE_PRIVATE);
                    String Title = sharedPreferences.getString("JOB_TITLE", null);
                    String Desc = sharedPreferences.getString("JOB_DESC", null);
                    String Category = sharedPreferences.getString("JOB_CATEGORY", null);
                    String Company = sharedPreferences.getString("JOB_COMPANY", null);
                    String Domain = sharedPreferences.getString("JOB_DOMAIN", null);
                    String Location = sharedPreferences.getString("JOB_LOCATION", null);
                    String Country = sharedPreferences.getString("JOB_COUNTRY", null);
                    String City = sharedPreferences.getString("JOB_CITY", null);
                    String Longitude = sharedPreferences.getString("JOB_LONGITUDE", null);
                    String Latitude = sharedPreferences.getString("JOB_LATITUDE", null);
                    String Career_Level = sharedPreferences.getString("EMPLOYEE_CAREER_LEVEL", null);
                    String Education = sharedPreferences.getString("EMPLOYEE_EDUCATION", null);
                    String In_Number = sharedPreferences.getString("EMPLOYEE_IN_NUMBER", null);
                    String Salary = sharedPreferences.getString("EMPLOYEE_SALARY", null);
                    String Skill_Set = sharedPreferences.getString("EMPLOYEE_SKILL_SET", null);
                    String Expiry = sharedPreferences.getString("Job_Expiry", null);
                    String Minimum_Experience = sharedPreferences.getString("Minimum_Experience", null);
                    String Maximum_Experience = sharedPreferences.getString("Maximum_Experience", null);
                    String Department = sharedPreferences.getString("Department", null);
                    String Comment = sharedPreferences.getString("Comment", null);

                    final String referencce = ref.push().getKey();
                    Firebase ref_child = ref.child(referencce);
                    Firebase uRef_child = uRef.child("Posted_Jobs");
                    Firebase uRef_child2 = uRef_child.child("Job IDs");
                    uRef_child2.push().setValue(referencce);
                    Firebase Job_Title = ref_child.child("JobTitle");
                    Job_Title.setValue(Title);
                    Firebase Job_Description = ref_child.child("JobDescription");
                    Job_Description.setValue(Desc);
                    Firebase Job_Category = ref_child.child("JobCategory");
                    Job_Category.setValue(Category);
                    Firebase Job_Company = ref_child.child("JobCompany");
                    Job_Company.setValue(Company);
                    Firebase Job_Domain = ref_child.child("JobDomain");
                    Job_Domain.setValue(Domain);
                    Firebase Job_Location = ref_child.child("JobLocation");
                    Job_Location.setValue(Location);
                    Firebase Job_Country = ref_child.child("JobCountry");
                    Job_Country.setValue(Country);
                    Firebase Job_City = ref_child.child("JobCity");
                    Job_City.setValue(City);
                    Firebase Job_Longitude = ref_child.child("JobLongitude");
                    Job_Longitude.setValue(Longitude);
                    Firebase Job_Latitude = ref_child.child("JobLatitude");
                    Job_Latitude.setValue(Latitude);
                    Firebase Job_Career_Level = ref_child.child("EmployeeCareerLevel");
                    Job_Career_Level.setValue(Career_Level);
                    Firebase Job_Education = ref_child.child("EmployeesEducation");
                    Job_Education.setValue(Education);
                    Firebase Job_In_Number = ref_child.child("EmployeeInNumber");
                    Job_In_Number.setValue(In_Number);
                    Firebase Job_Salary = ref_child.child("EmployeeSalary");
                    Job_Salary.setValue(Salary);
                    Firebase Job_Skill_Set = ref_child.child("EmployeeSkillSet");
                    Job_Skill_Set.setValue(Skill_Set);
                    Firebase Expiry_Date = ref_child.child("Expiry");
                    Expiry_Date.setValue(Expiry);
                    Firebase MinimumExperience = ref_child.child("Minimum_Experience");
                    MinimumExperience.setValue(Minimum_Experience);
                    Firebase MaximumExperience = ref_child.child("Maximum_Experience");
                    MaximumExperience.setValue(Maximum_Experience);
                    Firebase Com_Department = ref_child.child("Department");
                    Com_Department.setValue(Department);
                    Firebase Extra_Comment = ref_child.child("comment");
                    Extra_Comment.setValue(Comment);
                    //String formattedDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    /*DateFormat df = DateFormat.getTimeInstance();
                    df.setTimeZone(TimeZone.getTimeZone("gmt"));
                    String gmtTime = df.format(new Date());*/

                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    String weekDay;
                    SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.US);
                    weekDay = dayFormat.format(cal.getTime());
                    Date currentLocalTime = cal.getTime();
                    DateFormat date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
                    date.setTimeZone(TimeZone.getTimeZone("GMT"));
                    String localTime = date.format(currentLocalTime);
                    System.out.println(localTime);
                    Firebase Job_Post_Date = ref_child.child("Post_Date");
                    Job_Post_Date.setValue(weekDay+", "+localTime);
                    Firebase Source = ref_child.child("source");
                    Source.setValue("Job Seekicious");
                    Firebase Apply = ref_child.child("Apply_Results");
                    Apply.setValue("-");
                    Firebase User_Posted = ref_child.child("User_Posted");
                    User_Posted.setValue(key);

                    Toast.makeText(getApplicationContext(), "Data Successfully Saved", Toast.LENGTH_SHORT).show();


                    finish();


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
        startActivity(new Intent(PostJob1.this, MainActivity.class));
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
                btnNext.setText("Post Job");
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

}
