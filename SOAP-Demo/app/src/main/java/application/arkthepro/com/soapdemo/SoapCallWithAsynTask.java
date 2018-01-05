package application.arkthepro.com.soapdemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;


public class SoapCallWithAsynTask extends ActionBarActivity {

   private final String NAMESPACE = "http://www.holidaywebservice.com/HolidayService_v2/";
    private final String URL = "http://www.holidaywebservice.com/HolidayService_v2/HolidayService2.asmx";
    private  String SOAP_ACTION = "http://www.holidaywebservice.com/HolidayService_v2/GetCountriesAvailable";
    private  String METHOD_NAME = "GetCountriesAvailable";
    private String TAG = "RESULT";
    Button get;
    TextView tv_result;
    EditText et;
    EditText parameter1,parameter2,parameter3;
    RadioGroup radioGroup;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap);
        tv_result = (TextView)findViewById(R.id.tv_result);
        get = (Button)findViewById(R.id.button1);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup_SoapType);
        parameter1=(EditText)findViewById(R.id.et_param1);
        parameter2=(EditText)findViewById(R.id.et_param2);
        parameter3=(EditText)findViewById(R.id.et_param3);
        get.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int selected_type=radioGroup.getCheckedRadioButtonId();
                switch(selected_type){
                    //for No parameter
                    case R.id.rb_GetCountriesAvailable:
                        parameter1.setText("Null");
                        parameter2.setText("Null");
                        parameter3.setText("Null");
                       // getSoap(null,null,null,"GetCountriesAvailable");
                        break;

                    //for 1 parameter (CountryCode)
                    case R.id.rb_GetHolidaysAvailable:
                        parameter1.setText("Canada");
                        parameter2.setText("Null");
                        parameter3.setText("Null");

                      //  getSoap("Canada",null,null,"GetHolidaysAvailable");
                        break;

                    //for 2 parameter (CountryCode,Year)
                    case R.id.rb_GetHolidaysForYear :
                        parameter1.setText("Canada");
                        parameter2.setText("2016");
                        parameter3.setText("Null");
                     //   getSoap("Canada","2016",null,"GetHolidaysForYear");
                        break;

                    //for 3 parameter (CountryCode,HodidayCode,Year)
                    case R.id.rb_GetHolidayDate:
                        parameter1.setText(null);
                        parameter2.setText(null);
                        parameter3.setText(null);
                        parameter1.setText("Canada");
                        parameter2.setText("NEW-YEARS-DAY-ACTUAL");
                        parameter3.setText("2016");
                   //     getSoap("Canada","NEW-YEARS-DAY-ACTUAL","2016","GetHolidayDate");
                        break;

                }

                //Run it on AsyncTask
                AsyncCallWs task = new AsyncCallWs();
                task.execute(selected_type);

            }
        });
    }

    private class AsyncCallWs extends AsyncTask<Integer, Void , Void >{
        @Override
        protected Void doInBackground(Integer... params) {
            Log.i(TAG, "doInBackground-------------------------------------"+params[0].toString());
            switch(params[0]){
                //for No parameter
                case R.id.rb_GetCountriesAvailable:

                    SOAP_ACTION = "http://www.holidaywebservice.com/HolidayService_v2/GetCountriesAvailable";
                    METHOD_NAME = "GetCountriesAvailable";

                    getSoap(null,null,null,"GetCountriesAvailable");
                    break;

                //for 1 parameter (CountryCode)
                case R.id.rb_GetHolidaysAvailable:

                    SOAP_ACTION = "http://www.holidaywebservice.com/HolidayService_v2/GetHolidaysAvailable";
                    METHOD_NAME = "GetHolidaysAvailable";

                    getSoap("Canada",null,null,"GetHolidaysAvailable");
                    break;

                //for 2 parameter (CountryCode,Year)
                case R.id.rb_GetHolidaysForYear :
                    SOAP_ACTION = "http://www.holidaywebservice.com/HolidayService_v2/GetHolidaysForYear";
                    METHOD_NAME = "GetHolidaysForYear";

                    getSoap("Canada","2016",null,"GetHolidaysForYear");
                    break;

                //for 3 parameter (CountryCode,HodidayCode,Year)
                case R.id.rb_GetHolidayDate:

                    SOAP_ACTION = "http://www.holidaywebservice.com/HolidayService_v2/GetHolidayDate";
                    METHOD_NAME = "GetHolidayDate";

                    getSoap("Canada","NEW-YEARS-DAY-ACTUAL","2016","GetHolidayDate");
                    break;

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if(progressDialog!=null){
                progressDialog.cancel();
            }
            tv_result.setText("Check Logs for Results...");
        }

        @Override
        protected void onPreExecute() {
            progressDialog=ProgressDialog.show(SoapCallWithAsynTask.this,"Loading...","Please Wait....",false,false);
            Log.i(TAG, "onPreExecute");
            tv_result.setText("Calculating...");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }
    }

    public void getSoap(String param1,String param2,String param3,String soapType){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("Permission Result", "onClick: ---------------------------"+checkSelfPermission(Manifest.permission.INTERNET));
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET,
                    }, 101);
        }
        //Initialize SOAP OBject
        Log.d("Result", "getSoap: -------------------------soapType:"+soapType);
        SoapObject request_Object=new SoapObject(NAMESPACE,METHOD_NAME);

        //Add parameters as property

        switch (soapType){
            case "GetHolidaysAvailable":
            {
                Log.d(TAG, "getSoap: ------------------------------------------Add Only One Parameter");
                PropertyInfo CountryCode=new PropertyInfo();
                CountryCode.setName("CountryCode");
                CountryCode.setValue(param1);

                request_Object.addProperty(CountryCode);
                break;
            } case "GetHolidaysForYear":
            {
                Log.d(TAG, "getSoap: ------------------------------------------Adding Two Parameters");
                PropertyInfo CountryCode=new PropertyInfo();
                CountryCode.setName("CountryCode");
                CountryCode.setValue(param1);

                PropertyInfo year=new PropertyInfo();
                year.setName("year");
                year.setValue(param2);

                request_Object.addProperty(CountryCode);
                request_Object.addProperty(year);
                break;
            } case "GetHolidayDate":
            {
                Log.d(TAG, "getSoap: ------------------------------------------Adding Three Parameters");
                PropertyInfo CountryCode=new PropertyInfo();
                CountryCode.setName("CountryCode");
                CountryCode.setValue(param1);

                PropertyInfo holidayCode=new PropertyInfo();
                holidayCode.setName("holidayCode");
                holidayCode.setValue(param2);

                PropertyInfo year=new PropertyInfo();
                year.setName("year");
                year.setValue(param3);

                request_Object.addProperty(CountryCode);
                request_Object.addProperty(holidayCode);
                request_Object.addProperty(year);
                break;
            }


        }


        //Define SOAP Versions
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request_Object);

        //Needed to make Internet Calls
       //  HttpTransportSE androidhttpsTransportSE=new HttpTransportSE(URL,600000);

        HttpTransportSE androidhttpsTransportSE=new HttpTransportSE(URL);
        try{
            //Call webservice
            androidhttpsTransportSE.call(SOAP_ACTION,envelope);
        }catch (Exception e)
        {

            e.printStackTrace();
        }

        //Get SOAP RESPONSE
        SoapObject response_Object=(SoapObject)envelope.bodyIn;

        if(response_Object!=null){
            Log.d(TAG,"SOAP response:\n" + response_Object.getProperty(0).toString());
             XmlParser(response_Object);

        }else {
            Log.d(TAG,"SOAP response Failed:---------------------------\n");
            tv_result.setText("Network Connectivity Issue Try later...");
        }


    }

    private void XmlParser(SoapObject response){


        // TODO: 21-03-2017
        //Test URL - http://www.holidaywebservice.com/HolidayService_v2/HolidayService2.asmx/GetHolidaysAvailable?countryCode=Canada


        Log.d(TAG, "**************** XmlParser ****************");
        Log.d(TAG, "------------------------- getName()          : "+response.getName());
        Log.d(TAG, "------------------------- getPropertyCount() : "+response.getPropertyCount());
        //get Root Object
        SoapObject root = (SoapObject) response.getProperty(0);
        Log.d(TAG, "-------------------------root                : "+root);
        Log.d(TAG, "-------------------------root.getPropertyCount() : "+root.getPropertyCount());

        int i,j;
        for(i=0;i<root.getPropertyCount();i++){
            SoapObject object = (SoapObject) root.getProperty(i);
                Log.d(TAG, "------------------------- Child :"+i+": "+object);
            //Print all Values
            for(j=0;j<object.getPropertyCount();j++){
                Log.d(TAG, "------------------------- Value :"+j+": "+object.getProperty(j));
            }


        }





    }

}
