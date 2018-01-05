package application.arkthepro.com.retrofitexample.data.remote;

import application.arkthepro.com.retrofitexample.data.model.SOAnswersResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by i00589 on 21-03-2017.
 */

public interface SOService {

    //Get All Answers
    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers();

    //Get Answer by Tag

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers(@Query("tagged")String tags);
}
