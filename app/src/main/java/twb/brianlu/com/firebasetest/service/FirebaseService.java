package twb.brianlu.com.firebasetest.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import twb.brianlu.com.firebasetest.core.BasePresenter;

public class FirebaseService extends FirebaseMessagingService {
    private static final String TAG="FirebaseService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            Log.i(TAG,"title "+remoteMessage.getNotification().getTitle());
            Log.i(TAG,"body "+remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.i("FirebaseService","token "+token);
        BasePresenter.saveToken(token);
        BasePresenter.readUser();
    }
}
