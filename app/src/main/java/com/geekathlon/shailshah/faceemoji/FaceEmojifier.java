package com.geekathlon.shailshah.faceemoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by shailshah on 10/23/17.
 */

public class FaceEmojifier {

        public static void detectFaces(Context context, Bitmap bitmap)
        {

                FaceDetector faceDetector = new FaceDetector.Builder(context)
                        .setTrackingEnabled(false)
                        .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                        .build();

                //Build the frame
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();

                //Detect The faces
                SparseArray<Face> fases = faceDetector.detect(frame);

                Log.d(FaceEmojifier.class.getSimpleName(),"detectFaces: Number of Fases  = " + fases.size());

            //If there are no faces detected, show a toast message
            if (fases.size() == 0)
            {
                Toast.makeText(context, "No Faces Detected :( !!!", Toast.LENGTH_SHORT).show();
            }
            faceDetector.release();
        }
        }
