package twb.brianlu.com.firebasetest.core;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import twb.brianlu.com.firebasetest.model.User;

public class BasePresenter {
    protected static User user;
    protected static UserListener userListener;
    protected Context context;
    private Object lock;

    public BasePresenter() {
        this.context = BaseApplication.getContext();
    }

    protected void saveUser(User user) {

    }

    protected void readUser() {
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
                        FirebaseDatabase.getInstance()
                                .getReference("users").child(user.getUid()).setValue(user);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }


    public boolean isLogin() {

        return FirebaseAuth.getInstance().getCurrentUser() != null;
//        return true;
    }

    public interface UserListener {
        void onLogin();

        void onLogout();

        void toLoginPage();
    }
}
