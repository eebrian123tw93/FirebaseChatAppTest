package twb.brianlu.com.firebasetest.api;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/** Created by max on 2018/1/11. */
public class URLRetrofitBuilder {

  private Retrofit retrofit;

  public URLRetrofitBuilder() {}

  public Retrofit buildretrofit(String apiPath, boolean isSerializeNulls) {

    retrofit =
        new Retrofit.Builder()
            .baseUrl(apiPath) // 最後一定要是 "/" 結尾否則會報錯
            .client(new CommonPairs().getUnsafeOkHttpClient())
            // excludeFieldsWithoutExposeAnnotation，Model 中需個別指定序列化、反序列化欄位，加上 @Expose 標籤
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    isSerializeNulls
                        ? new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .serializeNulls()
                            .setLenient()
                            .create()
                        : new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .setLenient()
                            .create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    return retrofit;
  }
}
