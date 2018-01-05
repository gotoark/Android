package thinkers.com.marvin.Screens.Rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import thinkers.com.marvin.Screens.Modal.Cricket.MatchResponse;
import thinkers.com.marvin.Screens.Modal.Entertainment.MovieResponse;
import thinkers.com.marvin.Screens.Modal.IssueResponse;

/**
 * Created by rajesharumugam on 19-03-2017.
 */

public interface RestApiInterface {
    @GET("/get_issues.php")
    Call<IssueResponse> getIssues(@Query("issue_type") String issueType);

    @GET("/gateway/Movie%20Reviews%20API/2.0.0/reviews/search.json")
    @Headers("x-Gateway-APIKey: 35c70beb-b111-4111-926d-892e2e9d3221")
    Call<MovieResponse> getMovies(@Query("offset") Integer offset);


    @GET("/gateway/Cricket%20API/1.0/matches")
    @Headers("x-Gateway-APIKey: 35c70beb-b111-4111-926d-892e2e9d3221")
    Call<MatchResponse> getMatches(@Query("apikey") String offset);
}
