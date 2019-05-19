package twb.brianlu.com.firebasetest.FCMApi;

import android.support.annotation.NonNull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class FCMApiService {
    private FCMApi FCMApi;
    private Retrofit retrofitArticleExcerptApi;

    private FCMApiService() {
        URLRetrofitBuilder urlRetrofitBuilder = new URLRetrofitBuilder();
        retrofitArticleExcerptApi = urlRetrofitBuilder.buildretrofit("https://fcm.googleapis.com/", true);
        FCMApi = retrofitArticleExcerptApi.create(FCMApi.class);
    }

    // 獲取實例
    public static FCMApiService getInstance() {
        return FCMApiService.SingletonHolder.INSTANCE;
    }

    public void pushNotification(@NonNull Observer observer, @NonNull String to, @NonNull String data, boolean isObserveOnIO) {
        String json = "";
        FCMApi.pushFCM(json)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    // 創建實例
    private static class SingletonHolder {
        private static final FCMApiService INSTANCE = new FCMApiService();
    }
}

