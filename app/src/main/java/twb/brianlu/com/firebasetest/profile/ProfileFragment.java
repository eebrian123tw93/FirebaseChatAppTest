package twb.brianlu.com.firebasetest.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.hhl.library.FlowTagLayout;

import twb.brianlu.com.firebasetest.R;

public class ProfileFragment extends Fragment implements ProfileView, View.OnClickListener {

    private ProfilePresenter presenter;
    private Button logoutButton;
    private Button deleteButton;
    private FlowTagLayout flowTagLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutButton = view.findViewById(R.id.logout_button);
        deleteButton = view.findViewById(R.id.delete_user_button);
        flowTagLayout = view.findViewById(R.id.flowTagLayout);
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
                presenter.deleteUser();

                break;
            case R.id.logout_button:
                presenter.logout();
                break;

        }
    }

    @Override
    public void onSetMessage(String message, int type) {

    }
}
