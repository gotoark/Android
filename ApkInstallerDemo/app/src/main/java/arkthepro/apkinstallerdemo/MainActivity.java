package arkthepro.apkinstallerdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    /* Another Reference
    https://gist.github.com/zeuxisoo/997485
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ReadWritePerMissions
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 101);
        Button btn_apk1=(Button)findViewById(R.id.btn_apk1);
        btn_apk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                installRequiredApk();
            }
        });
        if(isAppInstalled(this,"com.gorro.nothing")){  //package name of application in assets
            //Allow the user to Proceed further
            ((TextView)findViewById(R.id.tv_status)).setText("All Required Applications Installed");
            ((TextView)findViewById(R.id.tv_status)).setTextColor(Color.GREEN);
            btn_apk1.setClickable(false);
            btn_apk1.setEnabled(false);
        }else{
            ((TextView)findViewById(R.id.tv_status)).setText("Please Install Required Applications Installed");
            ((TextView)findViewById(R.id.tv_status)).setTextColor(Color.RED);
            btn_apk1.setClickable(true);
            btn_apk1.setEnabled(true);
        }

    }

    private void installRequiredApk() {
        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open("reqApp.apk");
            out = new FileOutputStream("/sdcard/reqApp.apk");
            byte[] buffer = new byte[1024];
            int read;
            while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File("/sdcard/reqApp.apk")), "application/vnd.android.package-archive");
            startActivity(intent);
        }catch(Exception e){
            Log.e("TAG","---------------------------------------------Error"+e);
        }
    }
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
