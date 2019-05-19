package twb.brianlu.com.firebasetest.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

import twb.brianlu.com.firebasetest.core.BasePresenter;

public class LoginPresenter extends BasePresenter {
    private LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void login(String email, String password) {
        if(email.isEmpty()){
            view.onSetMessage("Email cant not be empty", FancyToast.ERROR);
            view.onLoginResult(false);
            return;
        }
        if(password.isEmpty()){
            view.onSetMessage("Password cant not be empty", FancyToast.ERROR);
            view.onLoginResult(false);
            return;
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

            }
        }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    saveUser(user);
                    view.onLoginResult(true);
                    view.onSetMessage("Login Success", FancyToast.SUCCESS);
                    LoginPresenter.this.readUser();
                }else {
                    view.onLoginResult(false);
                    view.onSetMessage("Login Failed", FancyToast.ERROR);
                }
            }
        });


    }

    public void clear() {
        view.onClearText();
    }

    public void setProgressBarVisibility(int visibility) {
        view.onSetProgressBarVisibility(visibility);
    }

    public void forgetPassword() {
        view.onForgetPassword();
    }

    public void register() {
        view.onRegister();
    }
}
