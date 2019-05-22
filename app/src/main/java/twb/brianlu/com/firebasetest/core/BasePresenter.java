package twb.brianlu.com.firebasetest.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import twb.brianlu.com.firebasetest.model.User;

public class BasePresenter {
    protected static User user;
    protected static UserListener userListener;
    protected static PublishSubject<List<String>> tagsObservable = PublishSubject.create();
    protected Context context;


    public BasePresenter() {
        this.context = BaseApplication.getContext();
    }

    public static void saveUser(User user) {
        if (user != null) {
            FirebaseDatabase.getInstance()
                    .getReference("users").child(user.getUid()).setValue(user);
            if (user.getTags() != null && user.getTags().size() != 0) {
                saveUserTags(user.getTags());
            }

        }
    }

    public static void saveUserTags(List<String> tags) {
        tagsObservable.onNext(tags);
        SharedPreferences preferences = BaseApplication.getContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("tags", new HashSet<>(tags));
        editor.apply();
    }

    public static List<String> readUserTags() {
        SharedPreferences prfs = BaseApplication.getContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        return new ArrayList<>(prfs.getStringSet("tags", new HashSet<String>()));
    }

    public static void readUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            user = null;
        } else {
            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    if (user == null) {
                        user = new User();
                        user.setDisplayName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        user.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        user.setDisplayName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                    }
                    user.setRooms(new ArrayList<String>());
                    user.getRooms().add("P8hxIBZunNfIfef6zGaqW8A8BLY2_YKd2m0doQShEGzIOFf96YauRmFu1");
                    user.setToken(readToken());
                    saveUser(user);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    public static boolean isLogin() {

        return FirebaseAuth.getInstance().getCurrentUser() != null;
//        return true;
    }

    public static void saveToken(String token) {
        SharedPreferences preferences = BaseApplication.getContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Authentication_token", token);
        editor.apply();
    }

    public static String readToken() {
        SharedPreferences prfs = BaseApplication.getContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        return prfs.getString("Authentication_token", "");
    }

    public void setTagsObserver(Observer observer) {
        tagsObservable.subscribe(observer);
    }

    public interface UserListener {
        void onLogin();

        void onLogout();

        void onDeleteUser();

        void toLoginPage();
    }
}
