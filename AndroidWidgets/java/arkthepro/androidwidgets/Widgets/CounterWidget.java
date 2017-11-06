package arkthepro.androidwidgets.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import arkthepro.androidwidgets.R;

/**
 * Implementation of App Widget functionality.
 */
public class CounterWidget extends AppWidgetProvider {
    private static final String ACTION_SIMPLEAPPWIDGET = "ACTION_BROADCASTWIDGETSAMPLE";
    private static int mCounter = 0;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.counter_widget);
        views.setTextViewText(R.id.tvWidget2, widgetText);

        // Construct an Intent which is pointing this class.
        Intent intent = new Intent(context, CounterWidget.class);
        intent.setAction(ACTION_SIMPLEAPPWIDGET);
        // And this time we are sending a broadcast with getBroadcast
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.tvWidget2, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);



    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_SIMPLEAPPWIDGET.equals(intent.getAction())) {
            mCounter++;
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.counter_widget);
            views.setTextViewText(R.id.tvWidget2, Integer.toString(mCounter));
            // This time we dont have widgetId. Reaching our widget with that way.
            ComponentName appWidget = new ComponentName(context, CounterWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidget, views);
        }
    }
}

