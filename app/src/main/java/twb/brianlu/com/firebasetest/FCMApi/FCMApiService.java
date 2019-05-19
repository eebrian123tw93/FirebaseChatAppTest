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

//    public void register(@NonNull Observer observer,
//                         @NonNull User user, boolean isObserveOnIO) {
//
//        Gson gson = new Gson();
//        String json = gson.toJson(user);
//        FCMApi
//                .register(json)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//    public void viewed(@NonNull Observer observer,
//                       @NonNull Article article, boolean isObserveOnIO) {
//
//        String articleId = article.getArticleId();
//        FCMApi
//                .viewed(articleId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//    public void login(@NonNull Observer observer, @NonNull User user, boolean isObserveOnIO) {
//        String authKey = user.authKey();
//        FCMApi
//                .login(authKey)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//
//    }
//
//    public void deleteUser(@NonNull Observer observer, @NonNull User user, boolean isObserveOnIO) {
//        String authKey = user.authKey();
//        FCMApi
//                .deleteUser(authKey)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//
//    }
//
//    public void forgotPassword(@NonNull Observer observer, @NonNull String email, boolean isObserveOnIO) {
//        FCMApi.forgotPassword(email)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//    @Deprecated
//    public void getArticlesPrivate(@NonNull Observer observer, @NonNull User user, @NonNull LocalDateTime endDateTime, @NonNull LocalDateTime startDateTime, @NonNull String orderBy, @NonNull int offset, @NonNull int limit, boolean isObserveOnIO) {
//        FCMApi.getArticlesPrivate(user.authKey(), endDateTime, startDateTime, orderBy, offset, limit)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//    public void getArticlesPublic(@NonNull Observer observer, @NonNull LocalDateTime endDateTime, @NonNull LocalDateTime startDateTime, @NonNull String orderBy, @NonNull int offset, @NonNull int limit, boolean isObserveOnIO) {
//        FCMApi.getArticles("",endDateTime, startDateTime, orderBy, offset, limit)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//    public void getArticles(@NonNull Observer observer,  User user, @NonNull LocalDateTime endDateTime, @NonNull LocalDateTime startDateTime, @NonNull String orderBy, @NonNull int offset, @NonNull int limit, boolean isObserveOnIO) {
//        FCMApi.getArticlesPrivate(user.authKey(), endDateTime, startDateTime, orderBy, offset, limit)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//
//    public void searchArticle(@NonNull Observer observer, @NonNull String query, @NonNull int limit, @NonNull int offset, boolean isObserveOnIO) {
//        FCMApi.searchArticle(query, limit, offset)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//    public void getUserPostHistoryPublic(@NonNull Observer observer, @NonNull User author, boolean isObserveOnIO) {
//        String authorId = author.getUserId();
//        FCMApi.getUserPostHistory("",authorId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//    public void getUserPostHistoryPrivate(@NonNull Observer observer, @NonNull User user,@NonNull User author, boolean isObserveOnIO) {
//        String authorId = author.getUserId();
//        FCMApi.getUserPostHistory(user.authKey(),authorId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//
//    public void postArticle(@NonNull Observer observer, @NonNull User user, @NonNull Article article, boolean isObserveOnIO) {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
//        String json = gson.toJson(article);
//        System.out.println(json);
//        FCMApi.postArticle(user.authKey(), json)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//    public void like(@NonNull Observer observer, @NonNull User user, @NonNull Like like, boolean isObserveOnIO) {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
//        String json = gson.toJson(like);
//        System.out.println(json);
//        FCMApi.like(user.authKey(), json)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//
//    }
//
//    public void comment(@NonNull Observer observer, @NonNull User user, @NonNull Comment comment, boolean isObserveOnIO) {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
//        String json = gson.toJson(comment);
//        System.out.println(json);
//        FCMApi.comment(user.authKey(), json)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//
//    }
//
//    @Deprecated
//    public void getComments(@NonNull Observer observer, @NonNull Article article, boolean isObserveOnIO) {
//        String articleId = article.getArticleId();
//        FCMApi.getComments(articleId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//    public void getArticleDataPublic(@NonNull Observer observer, @NonNull Article article, boolean isObserveOnIO) {
//        String articleId = article.getArticleId();
//        FCMApi.getArticleDataPublic(articleId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }
//
//    public void getArticleDataPrivate(@NonNull Observer observer, @NonNull User user, @NonNull Article article, boolean isObserveOnIO) {
//        String articleId = article.getArticleId();
//        FCMApi.getArticleDataPrivate(user.authKey(), articleId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(observer);
//    }

    // 創建實例
    private static class SingletonHolder {
        private static final FCMApiService INSTANCE = new FCMApiService();
    }
}

