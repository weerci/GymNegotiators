package kriate.production.com.gymnegotiators.fit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dima on 14.07.2017.
 */

public interface FitService {
    @GET("/files/getAllContent.php")
    Call<List<Content>> getAllContent();
}
