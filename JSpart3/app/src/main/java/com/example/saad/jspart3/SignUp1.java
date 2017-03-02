package com.example.saad.jspart3;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.content.UriMatcher;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URLEncoder;


public class SignUp1 extends AppCompatActivity {

    Button signup;
    Button camera;
    Button gallery;
    ImageView dpSignup;
    Uri ImageUri;
    private static final int PICK_IMAGE = 100;
    private static final int SELECTED_PICTURE = 1;
    static final int CAMERA_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        dpSignup = (ImageView)findViewById(R.id.dpSignup);
        signup = (Button)findViewById(R.id.btnSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp1.this,wait.class));
                finish();
            }
        });

        camera = (Button)findViewById(R.id.cameraSignup);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpSignup.setImageResource(R.drawable.user_anonymous);
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getfile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
                startActivityForResult(camera_intent,CAMERA_REQUEST);
            }
        });

        gallery = (Button)findViewById(R.id.gallerySignup);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });

    }

    private File getfile(){

        File folder = new File("sdcard/camera_app");
        if(!folder.exists()){
            folder.mkdir();
        }
        File image = new File(folder,"cam_image.jpg");
        return image;
    }

    public void btnclick(View view) {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){

            ImageUri = data.getData();
            dpSignup.setImageURI(ImageUri);
        }

        String path = "sdcard/camera_app/cam_image.jpg";
        //Picasso.with(context).load("file:" + path).into(view);
        String rightAngleImage = getRightAngleImage(path);
        String s = rotateImage(90, rightAngleImage);
        dpSignup.setImageDrawable(Drawable.createFromPath(s));
    }
    private String getRightAngleImage(String photoPath) {

        try {
            ExifInterface ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degree = 0;

            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    degree = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                case ExifInterface.ORIENTATION_UNDEFINED:
                    degree = 0;
                    break;
                default:
                    degree = 90;
            }

            return rotateImage(degree,photoPath);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoPath;
    }

    private String rotateImage(int degree, String imagePath){

        if(degree<=0){
            return imagePath;
        }
        try{
            Bitmap b= BitmapFactory.decodeFile(imagePath);

            Matrix matrix = new Matrix();
            if(b.getWidth()>b.getHeight()){
                matrix.setRotate(degree);
                b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(),
                        matrix, true);
            }

            FileOutputStream fOut = new FileOutputStream(imagePath);
            String imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            String imageType = imageName.substring(imageName.lastIndexOf(".") + 1);

            FileOutputStream out = new FileOutputStream(imagePath);
            if (imageType.equalsIgnoreCase("png")) {
                b.compress(Bitmap.CompressFormat.PNG, 100, out);
            }else if (imageType.equalsIgnoreCase("jpeg")|| imageType.equalsIgnoreCase("jpg")) {
                b.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }
            fOut.flush();
            fOut.close();

            b.recycle();
        }catch (Exception e){
            e.printStackTrace();
        }
        return imagePath;
    }

    public void openGallery(){

        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);


    }




}
