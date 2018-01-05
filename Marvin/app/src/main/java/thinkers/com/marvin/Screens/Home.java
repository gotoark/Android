package thinkers.com.marvin.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import thinkers.com.marvin.R;


public class Home extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    String mUsername,mPhotoUrl;
    CardView btn_health, btn_entertainemnt, btn_news, btn_travel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_health =(CardView) findViewById(R.id.btn_health);
        btn_entertainemnt =(CardView)findViewById(R.id.btn_entertainment);
        btn_news =(CardView)findViewById(R.id.btn_news);
        btn_travel =(CardView)findViewById(R.id.btn_travel);
       /* // Initialize Firebase Auth
        // Firebase instance variables
         FirebaseAuth mFirebaseAuth;
         FirebaseUser mFirebaseUser;
         mFirebaseAuth = FirebaseAuth.getInstance();
         mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, Authentication.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
*/

        btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(Home.this,Health_Menu.class));
            }
        });
        btn_entertainemnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Entertainement_Menu.class));

            }
        });
        btn_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,News_Menu.class));
            }
        });
        btn_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Travel_Menu.class));

            }
        });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}
