package arkthepro.com.detectscreenshots;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;

import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.FileObserver.CREATE;

public class MainActivity extends AppCompatActivity {

    //Find ScreenShots Path in DCIM
    File DIR_DCIM = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM);
    final File DCIM_ScreenShots = new File(DIR_DCIM, "Screenshots");

    //Find ScreenShots Path in PICTURES
    File DIR_PICTURES = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
    final File PICTURES_ScreenShots = new File(DIR_PICTURES, "Screenshots");

    //Create Obj For DetectScreenShots by passing path and Event
    DetectScreenShots d = new DetectScreenShots(DCIM_ScreenShots.toString(), CREATE);
    DetectScreenShots p = new DetectScreenShots(PICTURES_ScreenShots.toString(), CREATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dips path of Dcim
        TextView dcim = (TextView) findViewById(R.id.dcim);
        dcim.setText(DCIM_ScreenShots.toString());

        //display path of Pictures
        TextView pictures = (TextView) findViewById(R.id.pictures);
        pictures.setText(PICTURES_ScreenShots.toString());

//Start the event to Observe the Folder Changes
        Log.d("Event Triggered", "********************* Observing the Folder is Started***************************");
        d.startWatching();
        p.startWatching();


    }

    @Override
    protected void onDestroy() {
        Log.d("Event Triggered", "********************* Observing the Folder was Stopped ***************************");
        d.stopWatching();
        p.stopWatching();
        super.onDestroy();
    }
}
