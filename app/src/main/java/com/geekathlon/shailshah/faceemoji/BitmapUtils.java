package com.geekathlon.shailshah.faceemoji;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shailshah on 10/23/17.
 */

public class BitmapUtils {

    private static final String FILE_PROVIDER_AUTHORITY = "com.geekathlon.shailshah.fileprovider";

    /**
     * resamplePic will simply use to better memory usage
     * @param context The Application Context
     * @param imagePath Path of the photo to be resampled.
     * @return
     */
    static Bitmap resamplePic(Context context, String imagePath)
    {
        //Gwt Device Screen Size Information...
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        int targerH = metrics.heightPixels;
        int targetW = metrics.widthPixels;

        //Get the dimension of the original image
        BitmapFactory.Options bmOprions = new BitmapFactory.Options();
        bmOprions.inJustDecodeBounds =true;
        BitmapFactory.decodeFile(imagePath,bmOprions);
        int photoW = bmOprions.outWidth;
        int photoH = bmOprions.outHeight;

        //Determine How much Scale down the image..
        int sacleFactor = Math.min(photoW / targetW , photoH /targerH);

        //Decode the image file into a Bitmap sized fill the view..

        bmOprions.inJustDecodeBounds = false;
        bmOprions.inSampleSize  = sacleFactor;

        return BitmapFactory.decodeFile(imagePath);
    }

    /**
     * createTempImageFile will help us to store temparory image file into cache memory.. if user marked file as save the it will got ot actual directory..
     * @param context
     * @return The temporary image file
     * @throws IOException If the error occured creating the file
     */

    static File createTempImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp+ "_";
        File storageDir = context.getExternalCacheDir();

        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir

        );
    }

    /**
     * Deletes image file for a given path
     * @param context
     * @param imagePath
     * @return
     */

    static boolean deleteImageFile(Context context , String imagePath)
    {
        //Get the file
        File imageFile  = new File(imagePath);

        //Delete The image
        boolean deleted = imageFile.delete();

        //Toast will shown if there is an error to delete the image
        if (!deleted)
        {
            Toast.makeText(context, "Error finding image.. Please try again later..", Toast.LENGTH_SHORT).show();
        }

        return deleted;

    }

    private static void galarrayAddPic(Context context,String ImagePath)
    {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(ImagePath);
        Uri contenturi = Uri.fromFile(f);
        mediaScanIntent.setData(contenturi);
        context.sendBroadcast(mediaScanIntent);
    }

    static String saveImage(Context context, Bitmap image)
    {
        String savedImagePath = null;

        //Create the new file in the external storage
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
        String imageFilename = "JPEG_"+timeStamp+".jpg";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Emojify"
        );

        boolean success = true;
        if (!storageDir.exists())
        {
            success = storageDir.mkdirs();
        }

        //Save the new Bitmap
        if (success)
        {
            File imageFile = new File(storageDir,imageFilename);
            savedImagePath = imageFile.getAbsolutePath();

            try
            {
                OutputStream fOut = new FileOutputStream(imageFilename);
                image.compress(Bitmap.CompressFormat.JPEG,100,fOut);
                fOut.close();

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            //Add the image to the system gallery
            galarrayAddPic(context, savedImagePath);

            //show a toast with the saved location
            Toast.makeText(context, "Image Saved at " + savedImagePath , Toast.LENGTH_SHORT).show();
        }

        return savedImagePath;
    }

    static void shareImage(Context context , String imagePath)
    {
        //create the share intent and start the share Activity
        File imageFile = new File(imagePath);
        Intent shareintent = new Intent(Intent.ACTION_SEND);
        shareintent.setType("image/*");
        Uri photoURI = FileProvider.getUriForFile(context,FILE_PROVIDER_AUTHORITY,imageFile);
        shareintent.putExtra(Intent.EXTRA_STREAM,photoURI);
        context.startActivity(shareintent);

    }

}
