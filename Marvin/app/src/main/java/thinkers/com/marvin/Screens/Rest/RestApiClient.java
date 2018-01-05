package thinkers.com.marvin.Screens.Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rajesharumugam on 19-03-2017.
 */

public class RestApiClient {

   public static final String BASE_URL="http://52.36.211.72:5555/gateway/reviews/";
    //public static final String BASE_URL="https://api.practo.com/";
    private  static Retrofit retrofit=null;


    public  static Retrofit getClient(){
        if(retrofit==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }
}
