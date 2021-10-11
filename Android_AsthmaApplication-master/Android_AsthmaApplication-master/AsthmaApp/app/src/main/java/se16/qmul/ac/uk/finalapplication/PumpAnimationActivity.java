package se16.qmul.ac.uk.finalapplication;
//Imports used throughout class
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

//Inspiration from source https://www.youtube.com/watch?v=8Lq3HyBCuAA

public class PumpAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pump_animation);
        VideoView videoView = findViewById(R.id.video_view_pump );
        String video_path = "android.resource://"+getPackageName()+"/"+R.raw.finished_animation;
        Uri uri = Uri.parse(video_path);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

    }
}
