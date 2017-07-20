package kriate.production.com.gymnegotiators.fit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dima on 14.07.2017.
 */

public interface FitService {
    @GET("/files/getAllContent.php")
    Call<List<Content>> getAllContent();

    @GET("/files/getContent.php")
    Call<List<Phrases>> getContent(@Query("id") String id);

}
