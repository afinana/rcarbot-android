package com.middleand.mobile.rccar.framents;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.middleand.mobile.rccar.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraFragment extends Fragment {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    // Debugging
    private static final String TAG = "CameraFragment";
    // private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 1;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static TextView output;
    @SuppressWarnings("unused")
    private Uri fileUri;

    /**
     * Create a file Uri for saving an image or video
     */
    @SuppressWarnings("unused")
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_camera, container,
                false);
        Button buttonRecording = (Button) rootView.findViewById(R.id.recording);
        output = (TextView) rootView.findViewById(R.id.output);
        buttonRecording.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // create new Intent with with Standard Intent action that can
                // be
                // sent to have the camera application capture an video and
                // return it.
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                // // create a file to save the video
                // fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                //
                // if (fileUri!=null){
                // // set the image file name
                // intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                // set the video image quality to high
                // intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

                // set the video image quality to low
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 200);

                // start the Video Capture Intent
                startActivityForResult(intent,
                        CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

                // }
            }
        });
        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // After camera screen this code will executed

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                // create a file to save the video
                File tmpFile = getOutputMediaFile(MEDIA_TYPE_VIDEO);

                if (tmpFile != null) {


                    try {
                        AssetFileDescriptor videoAsset = getActivity()
                                .getContentResolver().openAssetFileDescriptor(
                                        data.getData(), "r");
                        FileInputStream fis = videoAsset.createInputStream();
                        FileOutputStream fos = new FileOutputStream(tmpFile);

                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = fis.read(buf)) > 0) {
                            fos.write(buf, 0, len);
                        }
                        fis.close();
                        fos.close();

                        // Video captured and saved to fileUri specified in the
                        //Intent
                        String absolutePath = tmpFile.getAbsolutePath();

                        output.setText("Video File : " + absolutePath);
                        Log.d(TAG, "Video saved in: " + absolutePath);

                        Toast.makeText(getActivity(), "Video saved to:" +
                                absolutePath, Toast.LENGTH_LONG).show();

                    } catch (IOException io_e) {
                        Log.e(TAG, io_e.toString());
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

                output.setText("User cancelled the video capture.");
                Log.d(TAG, "User cancelled the video capture.");

                // User cancelled the video capture
                Toast.makeText(getActivity(),
                        "User cancelled the video capture.", Toast.LENGTH_LONG)
                        .show();

            } else {

                output.setText("Video capture failed.");
                Log.d(TAG, "Video capture failed.");

                // Video capture failed, advise user
                Toast.makeText(getActivity(), "Video capture failed.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}
