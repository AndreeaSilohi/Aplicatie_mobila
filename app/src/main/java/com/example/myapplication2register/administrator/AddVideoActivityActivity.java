package com.example.myapplication2register.administrator;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.myapplication2register.R;
import com.example.myapplication2register.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddVideoActivityActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private EditText title;
    private VideoView videoView;
    private Button upload;
    //private FloatingActionButton pick;
    ActivityResultLauncher<String> getContent;


    private String t;

    private static final int VIDEO_PICK_GALLERY_CODE = 100;
    private static final int VIDEO_PICK_CAMERA_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private String[] cameraPermissions;
    private Uri videoUri;
    private ProgressDialog progresDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video_activity);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Add new video");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        title = findViewById(R.id.title);
        videoView = findViewById(R.id.videoView);
        upload = findViewById(R.id.upload);
       // pick = findViewById(R.id.pick);

        progresDialog = new ProgressDialog(this);
        progresDialog.setTitle("Please wait");
        progresDialog.setMessage("Uploading video");
        progresDialog.setCanceledOnTouchOutside(false);
        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                videoView.setVideoURI(result);

            }
        });

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = title.getText().toString().trim();
                if (TextUtils.isEmpty(t)) {
                    Toast.makeText(AddVideoActivityActivity.this, "Required", Toast.LENGTH_SHORT).show();
                }
                // Code for showing progressDialog while uploading
                progresDialog = new ProgressDialog(AddVideoActivityActivity.this);
                choosevideo();


            }
        });
    }
    private void choosevideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }

    Uri videouri;
    //    private void setVideoToVideoView(){
//        MediaController mediaController=new MediaController(this);
//        mediaController.setAnchorView(videoView);
//
//        //set media
//        videoView.setMediaController(mediaController);
//
//        videoView.setVideoURI(videoUri);
//        videoView.requestFocus();
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                videoView.pause();
//            }
//        });
//
//    }
    // startActivityForResult is used to receive the result, which is the selected video.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videouri = data.getData();

            progresDialog.setTitle("Uploading...");
            progresDialog.show();
            uploadvideo();

        }
    }

    private String getfiletype(Uri videouri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }

    private void uploadvideo() {

        String timestamp=""+System.currentTimeMillis();

        String filePathAndName="Videos/"+"video_"+timestamp;
        if (videouri != null) {
            // save the selected video in Firebase storage
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Files/" + System.currentTimeMillis() + "." + getfiletype(videouri));
            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("videoclipuri");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("videolink", ""+downloadUri);
                    map.put("title",""+title.getText().toString());
                    map.put("id",""+timestamp);
                    map.put("timestamp",""+timestamp);

                    reference1.child(timestamp).setValue(map);
                    // Video uploaded successfully
                    // Dismiss dialog
                    progresDialog.dismiss();
                    Toast.makeText(AddVideoActivityActivity.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progresDialog.dismiss();
                    Toast.makeText(AddVideoActivityActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progresDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }
}
