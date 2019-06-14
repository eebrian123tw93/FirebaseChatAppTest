package twb.brianlu.com.firebasetest.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PairApi {

  @POST("/requestpair")
  Observable<Response<ResponseBody>> pair(@Body String s);

  @POST("/cancelpair")
  Observable<Response<ResponseBody>> cancelPair(@Body String s);
}
