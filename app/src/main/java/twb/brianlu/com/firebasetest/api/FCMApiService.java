package twb.brianlu.com.firebasetest.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import twb.brianlu.com.firebasetest.model.fcm.Data;
import twb.brianlu.com.firebasetest.model.fcm.FCM;
import twb.brianlu.com.firebasetest.model.fcm.Notification;
import twb.brianlu.com.firebasetest.model.fcm.WebrtcCall;

public class FCMApiService {
  private FCMApi FCMApi;
  private Retrofit retrofit;

  private FCMApiService() {
    URLRetrofitBuilder urlRetrofitBuilder = new URLRetrofitBuilder();
    retrofit = urlRetrofitBuilder.buildretrofit("https://fcm.googleapis.com/", true);
    FCMApi = retrofit.create(FCMApi.class);
  }

  // 獲取實例
  public static FCMApiService getInstance() {
    return FCMApiService.SingletonHolder.INSTANCE;
  }

  //    public static void main(String[] args) {
  //        Notification notification = new Notification();
  //        notification.setBody("djfhals");
  //        notification.setTitle("dfshalf");
  //    FCMApiService.getInstance()
  //        .pushNotification(
  //            new Observer<Response<ResponseBody>>() {
  //              @Override
  //              public void onSubscribe(Disposable d) {}
  //
  //              @Override
  //              public void onNext(Response<ResponseBody> responseBodyResponse) {
  //                //                System.out.println(o.toString());
  //                System.out.println(responseBodyResponse.isSuccessful());
  //              }
  //
  //              @Override
  //              public void onError(Throwable e) {
  //                e.printStackTrace();
  //              }
  //
  //              @Override
  //              public void onComplete() {}
  //            },
  //
  // "esfZFKC5BSY:APA91bEpGobPbKtScD5vqTyXuPYA09y-vhzVvlWfCp4vl_qYE8u8hEZ3bnXasna-hk8nL0HRffAsYx8jCjDF6FjoJk_Yk6wNj5uUOFAXTvTViQ6Gwor2fn07okkzI7liIk1y-iAbECUf",
  //            notification,
  //            true);
  //    }

  public void pushNotification(
      @NonNull Observer observer,
      @NonNull String to,
      @NonNull Notification notification,
      boolean isObserveOnIO) {
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

  public void phoneCall(
          @NonNull Observer observer,
          @NonNull String to,
          @NonNull WebrtcCall webrtcCall,
          boolean isObserveOnIO) {
    Data data = new Data();
    data.setWebrtcCall(webrtcCall);
    FCM fcm = new FCM();
    fcm.setTo(to);
    fcm.setData(data);
    String json = new Gson().toJson(fcm);
    System.out.println(json);
    FCMApi.phoneCall(json)
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
