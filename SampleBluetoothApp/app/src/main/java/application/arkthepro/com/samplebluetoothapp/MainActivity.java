package application.arkthepro.com.samplebluetoothapp;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST = 100;
    private static final int REQUEST_OPEN_SETTINGS = 102;
    private static final int REQUEST_ENABLE_BT = 103;
    String TAG = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckAllPermissions();
    }

    public void CheckAllPermissions() {
        boolean isBluetoothPermissionGranted = checkPermissions(Manifest.permission.BLUETOOTH)&&checkPermissions(Manifest.permission.BLUETOOTH_ADMIN);
        if (isBluetoothPermissionGranted) {
            //All Permissions Granted Do What You want
            Log.i(TAG, "-------------------------User Granted All Permissions ");
            CheckBluetoothCompatibility();
        } else {
            //Some Permission were Denied Request Permissions from User
            Log.e(TAG, "-------------------------Asking for Permissions ");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN
                    }, MY_PERMISSIONS_REQUEST);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "--------------------------------onRequestPermissionsResult" + permissions[0] + " =" + grantResults[0]);
        Log.d(TAG, "--------------------------------onRequestPermissionsResult" + permissions[1] + " =" + grantResults[1]);
        for (int i : grantResults) {
            if (i != 0) {
                ShowAlert("With out Required Permissions we can't Proceed.Please Enable Req Permissions in App Setting ");
                break;
            }
        }
/*
//Customized Message for Each Permission
        if(grantResults[0]!=0){
            ShowAlert("To Access Contact Details You need to enable the Contact Related Permission in Settings ");
        }else if(grantResults[1]!=0) {
           ShowAlert("To Access Location Details You need to enable the Location Permission in Settings ");
        }else {
            Log.i(TAG, "-------------------------User Granted All Permissions ");
        }
*/
    }

    public boolean checkPermissions(String per) {
        boolean result = true;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int check = checkSelfPermission(per);
            result = check == PackageManager.PERMISSION_GRANTED;
            Log.i(TAG, "----------------------Checking RunTime Permissions Since the Device VERSION_CODE >= M :" + result);
            return result;
        } else {
            Log.i(TAG, "----------------------No Need of RunTime Permissions for Devices with VERSION_CODE < M" + result);
            return result;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101: {
              //
                break;
            }case 102: {
                CheckAllPermissions();
                break;
            }case 103: {
                CheckBluetoothCompatibility();
                break;
            }
        }
    }


    public void ShowAlert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                MainActivity.this).create();
        // Setting Dialog Title
        alertDialog.setTitle("Please Enable Location Permissions");
        // Setting Dialog Message
        alertDialog.setMessage(msg);
        // Setting Icon to Dialog
        // alertDialog.setIcon(R.draw);
        // Setting OK Button
        alertDialog.setButton("Open Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)), REQUEST_OPEN_SETTINGS);
            }
        });
        // Showing Alert Message
        alertDialog.show();

    }

    public void CheckBluetoothCompatibility(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Your Device Does Not Support Bluetooth ",Toast.LENGTH_SHORT).show();
        }else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }else {
                Set<BluetoothDevice> pairedDevices=mBluetoothAdapter.getBondedDevices();

                if(pairedDevices.size()>0){
                    for(BluetoothDevice devices:pairedDevices){
                        //Get Details Of Paired Devices
                        String deviceName=devices.getName();
                        String deviceMacAddress=devices.getAddress();
                        Log.i("TAG","BlueTooth Name:"+deviceName);
                        Log.i("TAG","BlueTooth MAC Address:"+deviceMacAddress);


                    }
                }
            }
        }

    }
}


