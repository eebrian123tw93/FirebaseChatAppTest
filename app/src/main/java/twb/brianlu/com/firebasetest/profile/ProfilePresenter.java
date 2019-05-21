package twb.brianlu.com.firebasetest.profile;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.profile.adapter.TagsAdapter;

public class ProfilePresenter extends BasePresenter {
    private ProfileView view;
    private TagsAdapter tagsAdapter;


    public ProfilePresenter(ProfileView view) {
        this.view = view;
        tagsAdapter = new TagsAdapter(context);
        view.onSetTagsAdapter(tagsAdapter);
        loadsTags();
    }

    public void loadsTags() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).child("tags").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> tags = new ArrayList<>();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    tags.add(shot.getValue().toString());
                }
                tagsAdapter.addTags(tags);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void logout() {

        FirebaseAuth.getInstance().signOut();

        if (userListener != null) userListener.onLogout();
    }

    public void deleteUser() {
        FirebaseAuth.getInstance().getCurrentUser().delete();

    }
}
