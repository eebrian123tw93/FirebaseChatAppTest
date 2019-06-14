package twb.brianlu.com.firebasetest.profile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.hhl.library.FlowTagLayout;
import com.hhl.library.OnTagClickListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.profile.selectTag.SelectTagsDialogFragment;

public class ProfileFragment extends Fragment implements ProfileView, View.OnClickListener {

    private ProfilePresenter presenter;
    private Button logoutButton;
    private Button deleteButton;
    private FlowTagLayout flowTagLayout;
    private ScrollView tagsScrollView;
    private TextView userNameTextView;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutButton = view.findViewById(R.id.logout_button);
        deleteButton = view.findViewById(R.id.delete_user_button);
        flowTagLayout = view.findViewById(R.id.flowTagLayout);
        tagsScrollView = view.findViewById(R.id.tags_scrollView);
        //        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
        userNameTextView = view.findViewById(R.id.userId_textView);

        flowTagLayout.setOnTagClickListener(
                new OnTagClickListener() {
                    @Override
                    public void onItemClick(FlowTagLayout parent, View view, int position) {
                        if (parent.getAdapter().getCount() - 1 == position) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                            if (prev != null) {
                                ft.remove(prev);
                            }
                            ft.addToBackStack(null);
                            // Create and show the dialog.
                            DialogFragment newFragment = new SelectTagsDialogFragment();
                            newFragment.show(ft, "dialog");
                        } else if (parent.getAdapter().getCount() - 2 == position) {
                            AlertDialog.Builder editDialog = new AlertDialog.Builder(getContext());
                            editDialog.setTitle("New Customize tag");

                            final EditText editText = new EditText(getContext());
                            editDialog.setView(editText);

                            editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
//                                    presenter.changeDisplayName(editText.getText().toString());
                                    presenter.addNewCustomizeTag(editText.getText().toString().trim());
                                }
                            });
                            editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });
                            editDialog.show();
                        }
                    }
                });

        logoutButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        userNameTextView.setOnClickListener(this);

        presenter = new ProfilePresenter(this);
        return view;
    }

    @Override
    public void onSetTagsAdapter(BaseAdapter adapter) {
        flowTagLayout.setAdapter(adapter);
    }

    @Override
    public void onSetUserDisplayName(String name) {
        userNameTextView.setText("Jyou-" + name);
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
                        // .setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                        .setPositiveTextColorResource(android.R.color.white)
                        // .setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                        .onPositive(
                                new BottomDialog.ButtonCallback() {
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
                        // .setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                        .setPositiveTextColorResource(android.R.color.white)
                        // .setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                        .onPositive(
                                new BottomDialog.ButtonCallback() {
                                    @Override
                                    public void onClick(BottomDialog dialog) {
                                        presenter.logout();
                                    }
                                })
                        .setNegativeText("Cancel")
                        .show();

                break;
            case R.id.userId_textView:
                AlertDialog.Builder editDialog = new AlertDialog.Builder(getContext());
                editDialog.setTitle("Change Display Name");

                final EditText editText = new EditText(getContext());
                editText.setText(userNameTextView.getText().subSequence(5, userNameTextView.getText().length()));
                editDialog.setView(editText);

                editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        presenter.changeDisplayName(editText.getText().toString());
                    }
                });
                editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                editDialog.show();

                break;
        }
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
