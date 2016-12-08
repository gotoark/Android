package com.teamrs.rajesh.buttonclick;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create a Button for toast and link it by its id Id and invoke the Onclick Listener by button id
        Button button_toast = (Button) findViewById(R.id.toast_btn);
        button_toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This is Toast Message", Toast.LENGTH_LONG).show();

            }
        });
        //Create a Button for toast and link it by its id Id and invoke the Onclick Listener by button id
        Button button_nextpage=(Button)findViewById(R.id.next_page);
        button_nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondPage.class);
                startActivity(i);
            }
            //NOTE: Add the activity in Manifest.xml
        });


    }

}
