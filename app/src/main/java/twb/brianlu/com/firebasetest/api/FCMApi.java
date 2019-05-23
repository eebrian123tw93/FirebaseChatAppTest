package twb.brianlu.com.firebasetest.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMApi {

    @Headers({"Content-Type:application/json", "Authorization:key=AAAAit6rMy8:APA91bG9nPyj_MWSxRxTKldnsodZ9spA9zIGEcW0V5DGtQaGFgfZrxHabGTyKILSVn5ZInKdTO6h9l8ApbRv3i_nw38NhK121SNJyd5yDj2XqbL2SOI3QC05ozwc5jd6WW9Xg0D-I4cc"})
    @POST("fcm/send")
    Observable<Response<ResponseBody>> pushFCM(@Body String s);
}
