package application.arkthepro.com.retrofitexample.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import application.arkthepro.com.retrofitexample.R;

public class UserDetails extends AppCompatActivity {
TextView userName,reputatuions,userType,imageUrl,profileUrl,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        userId=(TextView)findViewById(R.id.tv_id);
        userName=(TextView)findViewById(R.id.tv_username);
        reputatuions=(TextView)findViewById(R.id.tv_reputations);
        userType=(TextView)findViewById(R.id.tv_usertype);
        imageUrl=(TextView)findViewById(R.id.tv_imageUrl);
        profileUrl=(TextView)findViewById(R.id.tv_profileUrl);
        Intent receiever=getIntent();
        if(receiever.getStringExtra("username")!=null){
            Log.i("RESULT","----------------------------Intents Receieced");
            userName.setText(receiever.getStringExtra("username"));
            profileUrl.setText(receiever.getStringExtra("profile_url"));
            imageUrl.setText(receiever.getStringExtra("img_url"));
            userType.setText(receiever.getStringExtra("user_type"));
            reputatuions.setText(""+receiever.getIntExtra("reputations",0));
            userId.setText(""+receiever.getIntExtra("id",0));
            Log.i("RESULT","----------------------------Intents Receieced"+userId.getText().toString());
            Log.i("RESULT","----------------------------Intents Receieced"+userName.getText().toString());
            Log.i("RESULT","----------------------------Intents Receieced"+userType.getText().toString());
            Log.i("RESULT","----------------------------Intents Receieced"+imageUrl.getText().toString());
            Log.i("RESULT","----------------------------Intents Receieced"+profileUrl.getText().toString());
            Log.i("RESULT","----------------------------Intents Receieced"+reputatuions.getText().toString());




        }else {
            Log.i("ERROR","----------------------------NO intent Receieced-----------------------");
            Log.i("ERROR","----------------------------Redirecting to Home-----------------------");
            startActivity(new Intent(UserDetails.this,Home.class));
        }


    }
}
