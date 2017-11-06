package arkthepro.androidwidgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

public class ConfigureActivity extends AppCompatActivity {
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private EditText etUrl;
    private Button btAdd;
    private AppWidgetManager widgetManager;
    private RemoteViews views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_main);
        etUrl = (EditText) findViewById(R.id.etUrl);
        btAdd=(Button)findViewById(R.id.btAdd);
        // These steps are seen in the previous examples
        widgetManager = AppWidgetManager.getInstance(this);
        views = new RemoteViews(this.getPackageName(), R.layout.configurable_widget);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gets user input
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(etUrl.getText().toString()));
                PendingIntent pending = PendingIntent.getActivity(ConfigureActivity.this, 0, intent, 0);
                views.setOnClickPendingIntent(R.id.tvWidget3, pending);
                widgetManager.updateAppWidget(mAppWidgetId, views);
                Intent resultValue = new Intent();
                // Set the results as expected from a 'configure activity'.
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }


}
