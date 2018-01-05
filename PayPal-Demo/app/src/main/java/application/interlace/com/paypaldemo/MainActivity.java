package application.interlace.com.paypaldemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    // TODO: 15-03-2017
    //Replace Text with Your Client ID at line 33
    String TAG="paymentExample";
    //Create a PayPalConfiguration object
    // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
    // or live (ENVIRONMENT_PRODUCTION)
    private static PayPalConfiguration payPalConfiguration=new PayPalConfiguration()
            .clientId("Your Client ID from PayPal Dev Account")
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_pay=(Button)findViewById(R.id.btn_pay);

        //Start PayPalService
        Intent intent=new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration);
        startService(intent);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.VIBRATE,
                                Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.INTERNET
                        }, 101);
                onBuyPressed(v);
            }
        });

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    public void onBuyPressed(View pressed){
        //PayPalPayment(price,"USD",ItemName)
        // PAYMENT_INTENT_SALE will cause the payment to complete immediately.
        // Change PAYMENT_INTENT_SALE to
        //   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
        //   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
        //     later via calls from your server.
        PayPalPayment payPalPayment=new PayPalPayment(new BigDecimal(1.75),"USD","sample_item",PayPalPayment.PAYMENT_INTENT_SALE );
        Intent intent =new Intent(this, PaymentActivity.class);
        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("paymentExample","onActivityResult--------------------------------------------resultCode"+resultCode);
        Log.i("paymentExample","onActivityResult--------------------------------------------requestCode"+requestCode);
                if(resultCode== Activity.RESULT_OK){
                    PaymentConfirmation paymentConfirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    Log.i("paymentExample","onActivityResult--------------------------------------------paymentConfirmation"+paymentConfirmation);
                    if(paymentConfirmation!=null){
                        try{
                            Log.i("paymentExample", paymentConfirmation.toJSONObject().toString(4));
                            Log.i("paymentExample", paymentConfirmation.toJSONObject().toString());

                            JSONObject jsonObject=new JSONObject(paymentConfirmation.toJSONObject().toString(4));
                            String result=jsonObject.getJSONObject("response").getString("state");
                            Log.d(TAG, "onActivityResult:--------------------------client        "+jsonObject.get("client").toString());
                            Log.d(TAG, "onActivityResult:--------------------------response      "+jsonObject.get("response").toString());
                            Log.d(TAG, "onActivityResult:--------------------------response_type "+jsonObject.get("response_type").toString());
                            Log.d(TAG, "onActivityResult:--------------------------response_State "+result);

                            if(result.equals("approved")){
                                Toast.makeText(getApplicationContext(),"Payment Successfully" +result,Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"Payment Not Successfull "+result,Toast.LENGTH_SHORT).show();
                            }

                            // TODO: send 'confirm' to your server for verification.
                            // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                            // for more details.


                        }catch (JSONException e){
                            Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);

                        }
                    }

                }else if(resultCode==Activity.RESULT_CANCELED){
                    Log.i(TAG, "-----------------------"+"The user canceled.");

                }else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID){
                    Log.i(TAG, "-----------------------"+"An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
