package twb.brianlu.com.firebasetest.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import twb.brianlu.com.firebasetest.model.fcm.Data;
import twb.brianlu.com.firebasetest.model.fcm.FCM;
import twb.brianlu.com.firebasetest.model.fcm.Notification;


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

    public static void main(String[] args) {
        Notification notification = new Notification();
        notification.setBody("djfhals");
        notification.setTitle("dfshalf");
        FCMApiService.getInstance().pushNotification(new Observer<Response<ResponseBody>>() {
                                                         @Override
                                                         public void onSubscribe(Disposable d) {

                                                         }

                                                         @Override
                                                         public void onNext(Response<ResponseBody> responseBodyResponse) {
//                System.out.println(o.toString());
                                                             System.out.println(responseBodyResponse.isSuccessful());
                                                         }

                                                         @Override
                                                         public void onError(Throwable e) {
                                                             e.printStackTrace();

                                                         }

                                                         @Override
                                                         public void onComplete() {

                                                         }
                                                     }, "esfZFKC5BSY:APA91bEpGobPbKtScD5vqTyXuPYA09y-vhzVvlWfCp4vl_qYE8u8hEZ3bnXasna-hk8nL0HRffAsYx8jCjDF6FjoJk_Yk6wNj5uUOFAXTvTViQ6Gwor2fn07okkzI7liIk1y-iAbECUf"
                , notification, true);
    }

    public void pushNotification(@NonNull Observer observer, @NonNull String to, @NonNull Notification notification, boolean isObserveOnIO) {
        Data data = new Data();
        data.setNotification(notification);
        FCM fcm = new FCM();
        fcm.setTo(to);
        fcm.setData(data);
        String json = new Gson().toJson(fcm);
        System.out.println(json);
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

