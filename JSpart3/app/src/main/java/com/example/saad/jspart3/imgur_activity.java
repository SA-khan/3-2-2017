package com.example.saad.jspart3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saad.jspart3.helpers.DocumentHelper;
import com.example.saad.jspart3.helpers.IntentHelper;
import com.example.saad.jspart3.imgurmodel.ImageResponse;
import com.example.saad.jspart3.imgurmodel.Upload;
import com.example.saad.jspart3.services.UploadService;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class imgur_activity extends AppCompatActivity {

    @Bind(R.id.imageview)
    ImageView uploadImage;
    @Bind(R.id.editText_upload_title)
    EditText uploadTitle;
    @Bind(R.id.editText_upload_desc)
    EditText uploadDesc;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Upload upload; // Upload object containging image and meta data
    private File chosenFile; //chosen file from intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgur_activity);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri returnUri;

        if (requestCode != IntentHelper.FILE_PICK) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        returnUri = data.getData();
        String filePath = DocumentHelper.getPath(this, returnUri);
        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) return;
        chosenFile = new File(filePath);
        //Toast.makeText(getApplicationContext(),filePath,Toast.LENGTH_SHORT).show();

                /*
                    Picasso is a wonderful image loading tool from square inc.
                    https://github.com/square/picasso
                 */
        Picasso.with(getBaseContext())
                .load(chosenFile)
                .placeholder(R.drawable.ic_photo_library_black)
                .fit()
                .into(uploadImage);

    }
    @OnClick(R.id.imageview)
    public void onChooseImage() {
        uploadDesc.clearFocus();
        uploadTitle.clearFocus();
        IntentHelper.chooseFileIntent(this);
    }
    private void clearInput() {
        uploadTitle.setText("");
        uploadDesc.clearFocus();
        uploadDesc.setText("");
        uploadTitle.clearFocus();
        uploadImage.setImageResource(R.drawable.ic_photo_library_black);
    }
    @OnClick(R.id.fab)
    public void uploadImage() {
    /*
      Create the @Upload object
     */
        if (chosenFile == null) return;
        createUpload(chosenFile);

    /*
      Start upload
     */
        new UploadService(imgur_activity.this).Execute(upload, new UiCallback());
    }

    private void createUpload(File image) {
        upload = new Upload();

        upload.image = image;
        upload.title = uploadTitle.getText().toString();
        upload.description = uploadDesc.getText().toString();
    }
    private class UiCallback implements Callback<ImageResponse>, retrofit.Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {
            //System.out.println("______________________________________________________________________"+imageResponse.toString());
            //Toast.makeText(imgur_activity.this,imageResponse.toString()+"\n"+response.toString(),Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(R.id.rootView), "pass", Snackbar.LENGTH_SHORT).show();
            String url = imageResponse.data.link;
            finish();
            clearInput();
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
           // Toast.makeText(imgur_activity.this,"error"+error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(R.id.rootView), "fail", Snackbar.LENGTH_SHORT).show();
            if (error == null) {
               // Toast.makeText(imgur_activity.this,"error",Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.rootView), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

}
