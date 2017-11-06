package arkthepro.androidwidgets.Service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.util.Random;

import arkthepro.androidwidgets.R;
import arkthepro.androidwidgets.Widgets.UpdatingWidget;

/**
 * Created by Rajesh Kumar on 06-11-2017.
 */

public class UpdatingService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        // generates random number
        // generates random number
        Random random = new Random();
        int randomInt = random.nextInt(100);
        String lastUpdate = "Count: "+randomInt;
        // Reaches the view on widget and displays the number
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.updating_widget);
        view.setTextViewText(R.id.text4, lastUpdate);
        ComponentName theWidget = new ComponentName(this, UpdatingWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, view);
        return super.onStartCommand(intent, flags, startId);
    }
}
