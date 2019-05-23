package twb.brianlu.com.firebasetest.api;

import android.support.annotation.NonNull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PairApiService {
    private PairApi pairApi;
    private Retrofit retrofitArticleExcerptApi;

    private PairApiService() {
        URLRetrofitBuilder urlRetrofitBuilder = new URLRetrofitBuilder();
        retrofitArticleExcerptApi = urlRetrofitBuilder.buildretrofit("https://us-central1-firenbasetest.cloudfunctions.net/", true);
        pairApi = retrofitArticleExcerptApi.create(PairApi.class);
    }

    // 獲取實例
    public static PairApiService getInstance() {
        return PairApiService.SingletonHolder.INSTANCE;
    }

    public static void main(String[] args) {

        PairApiService.getInstance().pair(new Observer<Response<ResponseBody>>() {
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
                , true);
    }

    public void pair(@NonNull Observer observer, @NonNull String uid, boolean isObserveOnIO) {

        pairApi.pair(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    // 創建實例
    private static class SingletonHolder {
        private static final PairApiService INSTANCE = new PairApiService();
    }
}

