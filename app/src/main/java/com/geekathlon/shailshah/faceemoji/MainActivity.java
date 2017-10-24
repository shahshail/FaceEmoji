package com.geekathlon.shailshah.faceemoji;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE =1 ;
    private static final int REQUEST_STORAGE_PERMISSION = 1;

    private static final String FILE_PROVIDER_AUTHORITY = "com.geekathlon.shailshah.fileprovider";

    @BindView(R.id.image_view) ImageView mImageView;

    @BindView(R.id.emojify_button) Button mEmojifyButton;
    @BindView(R.id.share_button) FloatingActionButton mShareFeb;
    @BindView(R.id.save_button)FloatingActionButton mSaveFeb;
    @BindView(R.id.clear_button) FloatingActionButton mClearFeb;

    @BindView(R.id.title_text_view) TextView mTitleTextView;

    private String mTempPhotoPath;

    private Bitmap mResultBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind the view
        ButterKnife.bind(this);

        //Setup timber
        Timber.plant(new Timber.DebugTree());
    }

    @OnClick(R.id.emojify_button)
    public void emojifyMe() {
        // Check for the external storage permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            // Launch the camera if the permission exists
            launchCamera();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // called when app request read and write permission to the user
        switch (requestCode)
        {
            case REQUEST_STORAGE_PERMISSION:
            {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    launchCamera();
                }else
                {
                    Toast.makeText(this, getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }


    private void launchCamera()
    {
        //Create  the capture image Intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Ensure that there is a camera activity to handle an intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;
            try{
                //create temporary file thet the photo should go...
                photoFile = BitmapUtils.createTempImageFile(this);
            }catch (IOException e)
            {
                e.printStackTrace();
            }

            //continue only is file was created
            if (photoFile != null)
            {
                //Get the path of temporary file
                mTempPhotoPath = photoFile.getAbsolutePath();

                //Get the content URI for image file
                Uri photoUri = FileProvider.getUriForFile(this,FILE_PROVIDER_AUTHORITY,photoFile);

                // Add url so that the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                //lunch the camera activity
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and user successfuly capture an image
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            processAndSetImage();
        }else
                {
                   BitmapUtils.deleteImageFile(this,mTempPhotoPath);
                }
        }

    private void processAndSetImage()
    {
        // Toggle Visibility of the views
        mEmojifyButton.setVisibility(View.GONE);
        mTitleTextView.setVisibility(View.GONE);
        mSaveFeb.setVisibility(View.VISIBLE);
        mShareFeb.setVisibility(View.VISIBLE);
        mClearFeb.setVisibility(View.VISIBLE);

        //Resemble the saved image to fit the ImageView
        mResultBitmap = BitmapUtils.resamplePic(this,mTempPhotoPath);

        mImageView.setImageBitmap(mResultBitmap);

    }
    @OnClick(R.id.save_button)
    public void saveMe() {
        // Delete the temporary image file
        BitmapUtils.deleteImageFile(this, mTempPhotoPath);

        // Save the image
        BitmapUtils.saveImage(this, mResultBitmap);
    }

    /**
     * OnClick method for the share button, saves and shares the new bitmap.
     */
    @OnClick(R.id.share_button)
    public void shareMe() {
        // Delete the temporary image file
        BitmapUtils.deleteImageFile(this, mTempPhotoPath);

        // Save the image
        BitmapUtils.saveImage(this, mResultBitmap);

        // Share the image
        BitmapUtils.shareImage(this, mTempPhotoPath);
    }

    /**
     * OnClick for the clear button, resets the app to original state.
     */
    @OnClick(R.id.clear_button)
    public void clearImage() {
        // Clear the image and toggle the view visibility
        mImageView.setImageResource(0);
        mEmojifyButton.setVisibility(View.VISIBLE);
        mTitleTextView.setVisibility(View.VISIBLE);
        mShareFeb.setVisibility(View.GONE);
        mSaveFeb.setVisibility(View.GONE);
        mClearFeb.setVisibility(View.GONE);

        // Delete the temporary image file
        BitmapUtils.deleteImageFile(this, mTempPhotoPath);
    }
}
