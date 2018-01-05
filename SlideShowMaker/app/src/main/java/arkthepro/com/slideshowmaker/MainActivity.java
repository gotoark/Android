package arkthepro.com.slideshowmaker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.kbeanie.multipicker.api.AudioPicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.AudioPickerCallback;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenAudio;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import arkthepro.com.slideshowmaker.Adapters.MediaResultsAdapter;

public class MainActivity extends AppCompatActivity implements ImagePickerCallback,AudioPickerCallback {
    private final static int EXTERNAL_STORAGE_PERMISSION_REQUEST = 100;
    private ListView lvResults;
    private Button btPickImageMultiple,btn_setaudio,btCreateVideo,btMergeAudio;
    TextView tv_audio;
    List<ChosenImage> imagesList=new ArrayList<ChosenImage>();
    List<ChosenAudio> audioList=new ArrayList<ChosenAudio>();
    private ImagePicker imagePicker;
    private AudioPicker audioPicker;
    FFmpeg ffmpeg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvResults = (ListView) findViewById(R.id.lvResults);
        tv_audio = (TextView) findViewById(R.id.tv_audio);
        btPickImageMultiple = (Button) findViewById(R.id.btGalleryMultipleImages);
        btn_setaudio = (Button) findViewById(R.id.btn_setaudio);
        btCreateVideo = (Button) findViewById(R.id.btCreateVideo);
        btMergeAudio = (Button) findViewById(R.id.btMergeAudio);
        btPickImageMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageMultiple();
            }
        });
        btn_setaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickAudio();
            }
        });

        btCreateVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNoteOnSD(getApplicationContext(),"command.txt");
            }
        });
        btMergeAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MereAudio();
            }
        });

        ffmpeg = FFmpeg.getInstance(getApplicationContext());
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onFailure() {}

                @Override
                public void onSuccess() {}

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
       /*

        String command[]={"-y", "-r","1/5" ,"-i",src.getAbsolutePath(),
                "-c:v","libx264","-vf", "fps=25","-pix_fmt","yuv420p", dest.getAbsolutePath()};
        src.getAbsolutePath() is the absolute path of all your input images.
                String filePrefix = "extract_picture";
        String fileExtn = ".jpg";
        File picDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File dir = new File(picDir, "Images");
        File src = new File(dir, filePrefix + "%03d" + fileExtn);
        file '/storage/emulated/0/DCIM/Camera/P_20170807_143916.jpg'
    duration 2
    file '/storage/emulated/0/DCIM/Pic/P_20170305_142948.jpg'
    duration 5
    file '/storage/emulated/0/DCIM/Camera/P_20170305_142939.jpg'
    duration 6
    file '/storage/emulated/0/DCIM/Pic/P_20170305_142818.jpg'
    duration 2

String command[] = {"-y", "-f", "concat", "-safe", "0", "-i", textFile.getAbsolutePath(), "-vsync", "vfr", "-pix_fmt", "yuv420p", dest.getAbsolutePath()};

                */
        String[] cmd={"",""};

        requestExternalStoragePermission();
    }
    private void requestExternalStoragePermission() {
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS},
                    EXTERNAL_STORAGE_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == Picker.PICK_IMAGE_DEVICE) {
                imagePicker.submit(data);
            }
            if (requestCode == Picker.PICK_AUDIO) {
                audioPicker.submit(data);
            }


    }

    public void pickImageMultiple() {
        imagePicker = new ImagePicker(this);
        imagePicker.setImagePickerCallback(this);
        imagePicker.allowMultiple();
        imagePicker.pickImage();
    }
    public void pickAudio() {
        audioPicker = new AudioPicker(this);
        audioPicker.setAudioPickerCallback(this);
       // audioPicker.allowMultiple();
        audioPicker.pickAudio();
    }

    @Override
    public void onImagesChosen(List<ChosenImage> images) {

        this.imagesList=images;
        MediaResultsAdapter adapter = new MediaResultsAdapter(images, this);
        lvResults.setAdapter(adapter);
    }

    @Override
    public void onError(String s) {
        Toast.makeText(this, "Failed to Load Images", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onAudiosChosen(List<ChosenAudio> audiolist) {
        tv_audio.setText(audiolist.get(0).getDisplayName());
        this.audioList=audiolist;
    }
    public void generateNoteOnSD(Context context, String sFileName) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "SlideShowMaker");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);

            for(ChosenImage chosenImage:imagesList){
                writer.append("file "+"'"+chosenImage.getThumbnailPath()+"'");
                writer.append("\n");
                writer.append("duration 3");
                writer.append("\n");
            }
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            createVideo(gpxfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createVideo(File gpxfile){
        File root = new File(Environment.getExternalStorageDirectory(), "SlideShowMaker");
        if (!root.exists()) {
            root.mkdirs();
        }

        String command[] = {"-y", "-f", "concat", "-safe", "0", "-i", gpxfile.getAbsolutePath(), "-vsync", "vfr", "-pix_fmt", "yuv420p", root.getAbsolutePath()+"/"+"video.mp4"};

        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) {
                    Log.d("Result", "onProgress: -------------------------------------------"+message);

                }

                @Override
                public void onFailure(String message) {
                    Log.d("Result", "onFailure: -------------------------------------------"+message);
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onSuccess(String message) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Toast.makeText(getApplicationContext(), "Eroor", Toast.LENGTH_LONG).show();

        }
    }

    public void MereAudio(){
        File root = new File(Environment.getExternalStorageDirectory(), "SlideShowMaker");
        // with Audio
        String command[] = {"-i",root.getAbsolutePath()+"/"+"video.mp4","-i",audioList.get(0).getOriginalPath(), "-c:v", "copy", "-c:a", "aac","-map", "0:v:0", "-map", "1:a:0","-shortest", root.getAbsolutePath()+"/"+"videoWithAudio.mp4"};
    // Empty Audio
     //   String command[] = {"-i",root.getAbsolutePath()+"/"+"video.mp4","-i",audioList.get(0).getOriginalPath(), "-c:v", "copy", "-c:a", "aac","-shortest", destinationAbsolutePath};
        try {
            // to execute "ffmpeg -version" command you just need to pass "-version"
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onProgress(String message) {
                    Log.d("Result", "onProgress: -------------------------------------------"+message);

                }

                @Override
                public void onFailure(String message) {
                    Log.d("Result", "onFailure: -------------------------------------------"+message);
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onSuccess(String message) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Toast.makeText(getApplicationContext(), "Eroor", Toast.LENGTH_LONG).show();

        }
    }
}
