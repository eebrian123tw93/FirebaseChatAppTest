package twb.brianlu.com.firebasetest.core;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    //7611ce223ea0842
    //2f18ee6998162e046f860e709d0b0fcf080552e2
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
