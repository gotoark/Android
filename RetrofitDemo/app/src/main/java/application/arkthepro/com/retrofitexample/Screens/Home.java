package application.arkthepro.com.retrofitexample.Screens;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

import application.arkthepro.com.retrofitexample.Adapters.AnswerAdapter;
import application.arkthepro.com.retrofitexample.R;
import application.arkthepro.com.retrofitexample.data.Utils.ApiUtils;
import application.arkthepro.com.retrofitexample.data.Utils.ItemClickSupport;
import application.arkthepro.com.retrofitexample.data.model.Items;
import application.arkthepro.com.retrofitexample.data.model.SOAnswersResponse;
import application.arkthepro.com.retrofitexample.data.remote.SOService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity{
    private AnswerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;
    public static String TAG="RESULT";
    public static int MY_PERMISSIONS_REQUEST=101;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "posts loaded from API---------------------------------");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Check All Permissions
        CheckAllPermissions();

        mService = ApiUtils.getSoService();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_answers);
        mAdapter = new AnswerAdapter(
                new ArrayList<Items>(0), this,
                new AnswerAdapter.PostItemListener() {
                    @Override
                    public void onPostClick(long id) {
                        Toast.makeText(Home.this, "Post id is" + id, Toast.LENGTH_SHORT).show();

                    }
                });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        loadAnswers(mRecyclerView);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAnswers(mRecyclerView);
            }
        });

}
    public void loadAnswers(final RecyclerView mRecyclerView) {
        mService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {

ProgressDialog progressDialog=ProgressDialog.show(Home.this,"Loading...","Please Wait",false,false);
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {

                if(response.isSuccessful()) {
                    mAdapter.updateAnswer(response.body().getItems());
                    Log.d(TAG, "Set Click Liseners--------------------------------");
                    setClikLiseners(mRecyclerView,response);
                    //Print All Items
                    int i;
                    for (i=0;i<response.body().getItems().size();i++){
                        Log.d(TAG, "Name---------------------------------------"+response.body().getItems().get(i).getOwner().getDisplayName());

                    }
                    if(progressDialog!=null){
                        Toast.makeText(getApplicationContext(),"Successfully Loaded",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }


                }else {
                    if(progressDialog!=null){
                        Toast.makeText(getApplicationContext(),"Failed to  Load Try Later",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }

                    int intstatusCode  = response.code();
                    // handle request errors depending on status code
                    Log.d("MainActivity", "posts Not loaded from API_______________________"+intstatusCode);

                }
                // Stop refresh animation
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {

                Log.d(TAG, "error loading from API"+t);
                // Stop refresh animation
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    //Set Lisener
    public void setClikLiseners(final RecyclerView mRecyclerView, final Response<SOAnswersResponse> response){
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                Toast.makeText(getApplicationContext(),""+response.body().getItems().get(position).getOwner().getDisplayName(),Toast.LENGTH_SHORT).show();
                Intent sender=new Intent(Home.this,UserDetails.class);
                sender.putExtra("username",response.body().getItems().get(position).getOwner().getDisplayName());
                sender.putExtra("reputations",response.body().getItems().get(position).getOwner().getReputation());
                sender.putExtra("id",response.body().getItems().get(position).getOwner().getUserId());
                sender.putExtra("img_url",response.body().getItems().get(position).getOwner().getProfileImage());
                sender.putExtra("profile_url",response.body().getItems().get(position).getOwner().getLink());
                sender.putExtra("user_type",response.body().getItems().get(position).getOwner().getUserType());
                startActivity(sender);
            }
        });

    }


    public void CheckAllPermissions(){
        boolean isLocationPermissionGranted=checkPermissions(Manifest.permission.INTERNET);
        if(isLocationPermissionGranted){
            //All Permissions Granted Do What You want
            Log.i(TAG, "-------------------------User Granted All Permissions ");
        }else {
            //Some Permission were Denied Request Permissions from User
            Log.e(TAG, "-------------------------Asking for Permissions ");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, MY_PERMISSIONS_REQUEST);
        }


    }
    public boolean checkPermissions(String per){
        boolean result= true;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int check=checkSelfPermission(per);
            result = check == PackageManager.PERMISSION_GRANTED;
            Log.i(TAG, "----------------------Checking RunTime Permissions Since the Device VERSION_CODE >= M :" + result);
            return result;
        }else {
            Log.i(TAG, "----------------------No Need of RunTime Permissions for Devices with VERSION_CODE < M"+result);
            return result;
        }
    }



}


/*
Reference URL:
https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
 */