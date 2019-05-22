package twb.brianlu.com.firebasetest.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ScrollView;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.hhl.library.FlowTagLayout;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.profile.selectTag.SelectTagsDialogFragment;

public class ProfileFragment extends Fragment implements ProfileView, View.OnClickListener {

    private ProfilePresenter presenter;
    private Button logoutButton;
    private Button deleteButton;
    private FlowTagLayout flowTagLayout;
    private ScrollView tagsScrollView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutButton = view.findViewById(R.id.logout_button);
        deleteButton = view.findViewById(R.id.delete_user_button);
        flowTagLayout = view.findViewById(R.id.flowTagLayout);
        tagsScrollView = view.findViewById(R.id.tags_scrollView);
//        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);

        logoutButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);


        presenter = new ProfilePresenter(this);
        return view;
    }

    @Override
    public void onSetTagsAdapter(BaseAdapter adapter) {
        flowTagLayout.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_user_button:
                new BottomDialog.Builder(getContext())
                        .setTitle("Warning")
                        .setContent("This action will delete user")
                        .setPositiveText("Confirm")
                        .setPositiveBackgroundColorResource(R.color.colorPrimary)
                        //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                        .setPositiveTextColorResource(android.R.color.white)
                        //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(BottomDialog dialog) {
                                presenter.deleteUser();
                            }
                        })
                        .setNegativeText("Cancel")
                        .show();

                break;
            case R.id.logout_button:
                new BottomDialog.Builder(getContext())
                        .setTitle("Warning")
                        .setContent("This action will logout user,and restart application")
                        .setPositiveText("Confirm")
                        .setPositiveBackgroundColorResource(R.color.colorPrimary)
                        //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                        .setPositiveTextColorResource(android.R.color.white)
                        //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(BottomDialog dialog) {
                                presenter.logout();
                            }
                        })
                        .setNegativeText("Cancel")
                        .show();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                // Create and show the dialog.
                DialogFragment newFragment = new SelectTagsDialogFragment();
                newFragment.show(ft, "dialog");

                break;

        }
    }

    @Override
    public void onSetMessage(String message, int type) {

    }
}
