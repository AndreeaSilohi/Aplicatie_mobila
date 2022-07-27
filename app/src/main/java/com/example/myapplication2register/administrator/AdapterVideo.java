package com.example.myapplication2register.administrator;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2register.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.HolderVideo> {

    //context
    private Context context;
    private ArrayList<ModelVideo>videoArrayList;

    public AdapterVideo(Context context, ArrayList<ModelVideo> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;


    }

    @NonNull
    @Override
    public HolderVideo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_video,parent,false);
        return new HolderVideo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderVideo holder, int position) {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("videoclipuri");
        ModelVideo modelVideo=videoArrayList.get(position);

        String videolink=modelVideo.getVideolink();
        String title=modelVideo.getTitle();
        String id=modelVideo.getId();
        String timestamp=modelVideo.getTimestamp();



       holder.titlu.setText(title);

        setVideoUrl(modelVideo,holder);

        //handle click, download video
        holder.downloadFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadVideo(modelVideo);
            }


        });



        //handle click, delete video
        holder.deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show alert dialog confirm to delete

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete video: "+title)
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //confirmed to delete
                                deleteVideo(modelVideo);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //canceled deleting , dismiss dialof
                                dialog.dismiss();
                            }
                        })

                        .show();
            }
        });



    }

    private void setVideoUrl(ModelVideo modelVideo, HolderVideo holder) {
        //show progres bar
        // holder.progressBar.setVisibility(View.VISIBLE);
        String videoUrl=modelVideo.getVideolink();

        MediaController mediaController=new MediaController(context);
        mediaController.setAnchorView(holder.videoView);

        Uri videoUri=Uri.parse(videoUrl);

       holder.titlu.setText(modelVideo.getTitle());
        holder.videoView.setMediaController(mediaController);
        holder.videoView.setVideoURI(videoUri);
        holder.videoView.requestFocus();
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //video is ready to play
                mediaPlayer.start();
            }
        });
        holder.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
                //to check if buffering , rendering

                switch (what) {
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: {
                        //rendering started
                      //  holder.progressBar.setVisibility(View.VISIBLE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START: {
                        //buffering started
                       // holder.progressBar.setVisibility(View.VISIBLE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:{
                       // buffering ended
                        //holder.progressBar.setVisibility(View.GONE);
                        return true;
                    }

                }
                return false;
            }
        });

        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                mediaPlayer.start();
            }
        });

    }

    private void deleteVideo(ModelVideo modelVideo) {
        String videoId=modelVideo.getId();
        String videoUrl=modelVideo.getVideolink();
        String videoTitle=modelVideo.getTitle();

        //DElete from firebase storage;

        StorageReference reference= FirebaseStorage.getInstance().getReferenceFromUrl(videoUrl);
        reference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                       //1. Delete from firebase storage

                        //2.Delete from firebase database
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("videoclipuri");
                        databaseReference.child(videoId)//aici iau video id
                               .removeValue()
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       //deleted from firebase database

                                       Toast.makeText(context,"Video deleted succesfully...",Toast.LENGTH_SHORT).show();
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       //failed deleting
                                       Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                   }
                               });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed delete
                        Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void downloadVideo(ModelVideo modelVideo) {
        String videoUrl=modelVideo.getVideolink();//will be used to download video

        //get video reference
        StorageReference storageReference=FirebaseStorage.getInstance().getReferenceFromUrl(videoUrl);
        storageReference.getMetadata()
                .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        //get video basic info e.g. title, type

                        String fileName=storageMetadata.getName();//file name in firebase storage
                        String fileType=storageMetadata.getContentType();//file type in in firebase storage e.g mp4
                        String fileDirectory= Environment.DIRECTORY_DOWNLOADS;//video will be saved in the folder...

                         ///init  download manager
                        DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
                        //get uri file to be downloaded
                        Uri uri=Uri.parse(videoUrl);

                        //create download request, new request for each download
                        DownloadManager.Request request=new DownloadManager.Request(uri);

                        //notification visibility
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        // set destionation path
                        request.setDestinationInExternalPublicDir(""+fileDirectory,""+fileName+".mp4");

                        //add request to enque-can be multiple requests
                        downloadManager.enqueue(request);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed getting
                        Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }






    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }
        //view holder class, holds,inits de ui views

    class HolderVideo extends RecyclerView.ViewHolder{
        VideoView videoView;
        TextView titlu;

        ProgressBar progressBar;
        FloatingActionButton deleteFab;
        FloatingActionButton downloadFab;
        public HolderVideo(@NonNull View itemView) {
            super(itemView);

            videoView=itemView.findViewById(R.id.videoView);
            titlu=itemView.findViewById(R.id.titleTv);
           // progressBar=itemView.findViewById(R.id.progressBar);
            deleteFab=itemView.findViewById(R.id.deleteFab);
            downloadFab=itemView.findViewById(R.id.downloadFab);

        }
    }
}
