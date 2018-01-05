package thinkers.com.marvin.Screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thinkers.com.marvin.R;
import thinkers.com.marvin.Screens.Adapters.MoviesAdapter;
import thinkers.com.marvin.Screens.Modal.Entertainment.MovieResponse;
import thinkers.com.marvin.Screens.Modal.Entertainment.Result;
import thinkers.com.marvin.Screens.Modal.IssueResponse;
import thinkers.com.marvin.Screens.Rest.RestApiClient;
import thinkers.com.marvin.Screens.Rest.RestApiInterface;
import thinkers.com.marvin.Screens.Util.ItemClickSupport;
import thinkers.com.marvin.Screens.Util.MoviesDetails;

public class Entertainement_Menu extends AppCompatActivity {

    MoviesAdapter listAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout activity_issue_list;
    //  private IssueAdapter mAdapter;
    List<Result> result;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainement__menu);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_issues);
        Log.d("RESULT", "----------------------------------Reached Entertainment: ");
        activity_issue_list=(LinearLayout) findViewById(R.id.activity_issue_list);
        getMovies();
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovies();
            }
        });

            }
    public void getMovies(){
        final ProgressDialog progressDialog = ProgressDialog.show(Entertainement_Menu.this, "Loading...", "Please Wait", false, false);
        RestApiInterface apiInterface = RestApiClient.getClient()
                .create(RestApiInterface.class);

        Call<MovieResponse> movie = apiInterface.getMovies(20);
        movie.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    result = response.body().getResults();
                    Log.d("RESULT", "----------------------------------onResponse: " + result.size());
                    final String[] issues_items = new String[result.size()];
                    for (int i = 0; i < result.size(); i++) {
                        //Storing names to string array
                        issues_items[i] = result.get(i).getDisplayTitle();
                        //Log.d("RESULT", "----------------------------------PARSING: " + issues.get(i).getTitle());

                    }
                    //Creating an array adapter for list view
              /*  ArrayAdapter adapter = new ArrayAdapter<String>(IssueList.this,R.layout.list_item_issue,items);

                //Setting adapter to listview
                issue_list.setAdapter(adapter);*/
                    //   listAdapter=new ArrayAdapter<String>(IssueList.this,android.R.layout.simple_list_item_1,issues_items);
                    listAdapter = new MoviesAdapter(result, Entertainement_Menu.this, new MoviesAdapter.PostItemListener() {
                        @Override
                        public void onPostClick(long id) {

                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Entertainement_Menu.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(listAdapter);
                    mRecyclerView.setHasFixedSize(true);
                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(Entertainement_Menu.this, DividerItemDecoration.VERTICAL);
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

                    setClikLiseners(mRecyclerView, response);


                   /* issue_list.setAdapter(listAdapter);
                    issue_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent=new Intent(IssueList.this,Issue_Details.class);
                            intent.putExtra("issue_id",issues.get(i).getId());
                            intent.putExtra("issue_title",issues.get(i).getTitle());
                            intent.putExtra("issue_description",issues.get(i).getDescription());
                            intent.putExtra("issue_type",issues.get(i).getType());
                            intent.putExtra("issue_level",issues.get(i).getLevel());
                            intent.putExtra("issue_createdDate",issues.get(i).getCreadtedDate());
                            intent.putExtra("issue_updated_date",issues.get(i).getUpdatedDate());
                            startActivity(intent);
                        }
                    });*/
                    if (progressDialog != null) {
                        Toast.makeText(getApplicationContext(), "Successfully Loaded", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }


                } else {
                    if (progressDialog != null) {
                        Toast.makeText(getApplicationContext(), "Failed to  Load Try Later", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }

                    int intstatusCode = response.code();
                    // handle request errors depending on status code
                    Log.d("MainActivity", "posts Not loaded from API_______________________" + intstatusCode);

                }
                // Stop refresh animation
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                if (progressDialog != null) {
                   // Toast.makeText(getApplicationContext(), "Successfully Loaded", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }
                Log.d("RESULT", "----------------------------------onResponse: FAiled " +t);

            }
        });
    }

    //Set Lisener
    public void setClikLiseners(final RecyclerView mRecyclerView, final Response<MovieResponse> response){
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                // do it
                Intent intent=new Intent(Entertainement_Menu.this,MoviesDetails.class);
                intent.putExtra("issue_id",result.get(i).getDisplayTitle());
                intent.putExtra("issue_title",result.get(i).getMpaaRating());
                intent.putExtra("issue_description",result.get(i).getByline());
                intent.putExtra("issue_type",result.get(i).getCriticsPick());
                intent.putExtra("issue_level",result.get(i).getPublicationDate());
                intent.putExtra("issue_createdDate",result.get(i).getSummaryShort());
                intent.putExtra("issue_updated_date",result.get(i).getHeadline());
                startActivity(intent);
            }
        });



    }
}
