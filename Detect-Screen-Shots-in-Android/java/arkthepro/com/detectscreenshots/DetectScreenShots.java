package arkthepro.com.detectscreenshots;

import android.os.FileObserver;
import android.util.Log;

/**
 * Created by rajesharumugam on 29-01-2017.
 */

public class DetectScreenShots extends FileObserver {

//Constructor to initialize the FileObserver
    public DetectScreenShots(String path, int event) {
        super(path, event);
    }
    //onEvent(int event, String path) triggers when the event is happen in the desired path
    @Override
    public void onEvent(int event, String path) {
        Log.d("Event Triggered", "********************* ScreenShot was Taken ***************************");
        //Todo
        //do what you want here
    }
}
