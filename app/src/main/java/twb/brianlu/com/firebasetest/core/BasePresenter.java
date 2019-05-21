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
import java.util.List;

import twb.brianlu.com.firebasetest.model.User;

public class BasePresenter {
    protected static User user;
    protected static UserListener userListener;
    protected Context context;


    public BasePresenter() {
        this.context = BaseApplication.getContext();
    }

    public static void saveUser(User user) {
        if (user != null) {
            FirebaseDatabase.getInstance()
                    .getReference("users").child(user.getUid()).setValue(user);
        }
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

                    }
                    user.setRooms(new ArrayList<String>());
                    user.getRooms().add("P8hxIBZunNfIfef6zGaqW8A8BLY2_YKd2m0doQShEGzIOFf96YauRmFu1");
                    user.setToken(readToken());
                    List<String> tags = new ArrayList<>();
                    tags.add("旅遊");
                    tags.add("健行");
                    tags.add("露營");

                    tags.add("游泳");
                    tags.add("衝浪");
                    tags.add("浮潛");
                    tags.add("潛水");
                    tags.add("極限運動");
                    tags.add("生存遊戲");
                    tags.add("射擊");
                    tags.add("红色");
                    tags.add("黑色");
                    tags.add("花边色");
                    tags.add("深蓝色");
                    tags.add("白色");
                    tags.add("玫瑰红色");
                    tags.add("紫黑紫兰色");
                    tags.add("葡萄红色");
                    tags.add("绿色");
                    tags.add("彩虹色");
                    tags.add("牡丹色");
                    tags.add("周杰伦");
                    tags.add("刘德华");
                    tags.add("林俊杰");
                    tags.add("邓紫棋");
                    tags.add("华晨宇");
                    tags.add("王力宏");
                    tags.add("Linkin Park");
                    tags.add("legend never die");
                    tags.add("红莲之弓矢");
                    tags.add("Let It Go");
                    tags.add("青鸟");
                    tags.add("in the end");
                    tags.add("Hey Jude");
                    tags.add("告白气球");
                    tags.add("see you again");
                    tags.add("faded");
                    tags.add("篮球");
                    tags.add("网球");
                    tags.add("棒球");
                    tags.add("乒乓球");
                    tags.add("足球");
                    tags.add("跑步");
                    tags.add("游泳");
                    tags.add("冰球");
                    tags.add("高尔夫球");
                    tags.add("橄榄球");
                    tags.add("羽毛球");
                    tags.add("桌球");
                    user.setTags(tags);
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

    public interface UserListener {
        void onLogin();

        void onLogout();

        void toLoginPage();
    }
}
