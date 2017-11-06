package arkthepro.androidwidgets.Widgets;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;

import arkthepro.androidwidgets.R;
import arkthepro.androidwidgets.Service.UpdatingService;

/**
 * Implementation of App Widget functionality.
 */
public class UpdatingWidget extends AppWidgetProvider {

    private PendingIntent service;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent i = new Intent(context, UpdatingService.class);
        if (service == null) {
            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, service);
    }
}

