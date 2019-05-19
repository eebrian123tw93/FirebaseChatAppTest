package twb.brianlu.com.firebasetest.register;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

import twb.brianlu.com.firebasetest.core.BasePresenter;

public class RegisterPresenter extends BasePresenter {
    private RegisterView view;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
    }

    public void register(String email, String password) {
        if (email.isEmpty()) {
            view.onSetMessage("Email can not be empty", FancyToast.ERROR);
            view.onRegisterResult(false);
            return;
        }
        if (password.isEmpty()) {
            view.onSetMessage("Password can not be empty", FancyToast.ERROR);
            view.onRegisterResult(false);
            return;
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

            }
        }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
//                    // Sign in success
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                            .setDisplayName(displayName).build();
//
//                    user.updateProfile(profiregisterView.onRegisterResult(true);
//                        registerView.onSetMessage("Register Success", FancyToast.SUCCESS);leUpdates);
                    view.onRegisterResult(true);
                    view.onSetMessage("Register Success", FancyToast.SUCCESS);

                } else {
                    view.onRegisterResult(false);
                    view.onSetMessage("Register Error(Email existed)", FancyToast.ERROR);

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

}
