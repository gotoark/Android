package application.arkthepro.com.retrofitexample.data.Utils;

import application.arkthepro.com.retrofitexample.data.remote.RestApiClient;
import application.arkthepro.com.retrofitexample.data.remote.SOService;

/**
 * Created by i00589 on 21-03-2017.
 */

public class ApiUtils {

    public static final String BASE_URL="https://api.stackexchange.com/2.2/";
    public static SOService getSoService(){
        return RestApiClient.getClient(BASE_URL).create(SOService.class);
    }

}
