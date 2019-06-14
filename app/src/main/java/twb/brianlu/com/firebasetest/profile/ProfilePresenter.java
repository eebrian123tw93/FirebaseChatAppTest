package twb.brianlu.com.firebasetest.profile;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import twb.brianlu.com.firebasetest.core.BaseApplication;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.fbDataService.FirebaseDataService;
import twb.brianlu.com.firebasetest.profile.adapter.TagsAdapter;

public class ProfilePresenter extends BasePresenter {
    private ProfileView view;
    private TagsAdapter tagsAdapter;

    public ProfilePresenter(ProfileView view) {
        this.view = view;
        tagsAdapter = new TagsAdapter(context);
        view.onSetTagsAdapter(tagsAdapter);

        loadsTags();

        FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        view.onSetUserDisplayName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

    }

    public void changeDisplayName(String name) {
        if (name.isEmpty()) {

            view.onSetMessage("Display Name Can't be empty", FancyToast.ERROR);
            return;
        } else if (name.length() < 6) {
            view.onSetMessage("Display Name can't less than 6", FancyToast.ERROR);
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates =
                new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        user.updateProfile(profileUpdates)
                .addOnSuccessListener(aVoid -> {
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("displayName").setValue(name);
                    view.onSetMessage(name + "change success", FancyToast.SUCCESS);
                    view.onSetUserDisplayName(name);
                })
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        view.onSetMessage(name + "change error", FancyToast.ERROR);
                    }

                });

    }

    public void addNewCustomizeTag(String customTag){
        final List<String> tags = readUserTags();
        tags.add(customTag);
        BaseApplication.addNewCustomizeTags(customTag);
//        tagsAdapter.addTag(customTag);
        FirebaseDataService.addTag(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),tags )
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    BasePresenter.saveUserTags(tags);
//                                    loadsTags();
                                    tagsAdapter.clear();
                                    tagsAdapter.addTags(tags);
                                } else {

                                }
                            }
                        });
    }

    public void loadsTags() {

        final List<String> tags = readUserTags();
        tagsAdapter.addTags(tags);

        Observer<List<String>> listObserver =
                new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<String> tags) {
                        tagsAdapter.clear();
                        tagsAdapter.addTags(tags);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };
        setTagsObserver(listObserver);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        saveUserTags(new ArrayList<String>());
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userListener != null) userListener.onLogout();
    }

    public void deleteUser() {
        saveUserTags(new ArrayList<String>());
        FirebaseAuth.getInstance().getCurrentUser().delete();
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userListener != null) userListener.onDeleteUser();
    }
}
