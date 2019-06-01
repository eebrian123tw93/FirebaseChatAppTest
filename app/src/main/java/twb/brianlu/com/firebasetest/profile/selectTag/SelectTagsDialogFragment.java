package twb.brianlu.com.firebasetest.profile.selectTag;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hhl.library.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.core.BaseApplication;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.fbDataService.FirebaseDataService;

public class SelectTagsDialogFragment extends DialogFragment {
  private SelectTagsAdapter adapter;
  private FlowTagLayout tagLayout;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    // 设置主题的构造方法
    // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.fragment_select_tags, null);

    tagLayout = view.findViewById(R.id.tags_flow_layout);
    tagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);

    adapter = new SelectTagsAdapter(getContext());
    //        loadTags();
    tagLayout.setAdapter(adapter);

    adapter.addTags(BaseApplication.tags);
    loadTags();

    builder.setView(view);
    builder.setPositiveButton(
        "Confirm",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {}
        });
    builder.setNegativeButton(
        "Cancel",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dismiss();
          }
        });

    return builder.create();
  }

  @Override
  public void onResume() {
    super.onResume();
    final AlertDialog d = (AlertDialog) getDialog();
    if (d != null) {
      Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
      positiveButton.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              List<Integer> list = tagLayout.getSelectedList();

              final List<String> selectedTags = new ArrayList<>();
              for (int index : list) selectedTags.add(adapter.getTagWithPosition(index));
              if (selectedTags.size() < 5) {
                Toast.makeText(getContext(), "需要選擇 5 個以上", Toast.LENGTH_SHORT).show();
              } else {
                FirebaseDataService.addTag(
                        FirebaseAuth.getInstance().getCurrentUser().getUid(), selectedTags)
                    .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                              BasePresenter.saveUserTags(selectedTags);
                            } else {

                            }
                          }
                        });
                dismiss();
              }
            }
          });
    }
  }

  public void loadTags() {
    List<String> userTags = BasePresenter.readUserTags();
    for (String tag : userTags) {
      adapter.setTagSelect(tag, true);
    }
    adapter.notifyDataSetChanged();
    //        adapter.notifyDataSetInvalidated();

  }
}
