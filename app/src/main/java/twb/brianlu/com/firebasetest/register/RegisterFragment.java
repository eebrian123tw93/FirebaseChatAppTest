package twb.brianlu.com.firebasetest.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import twb.brianlu.com.firebasetest.R;

public class RegisterFragment extends Fragment implements RegisterView,View.OnClickListener{
    private RegisterPresenter presenter;

//    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private ProgressBar progressBar;

    private Button registerButton;
    private Button clearButton;

    private TextView messageTextView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);


//        if (getActivity().getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }




//        usernameEditText = view.findViewById(R.id.username_editText);
        passwordEditText = view.findViewById(R.id.password_editText);
        emailEditText = view.findViewById(R.id.email_editText);
        progressBar = view.findViewById(R.id.progressBar);
        registerButton = view.findViewById(R.id.register_button);
        clearButton = view.findViewById(R.id.clear_button);
        messageTextView = view.findViewById(R.id.message_textView);

        registerButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);


        presenter = new RegisterPresenter(this);
        presenter.setProgressBarVisibility(View.GONE);
        return view;
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }

    @Override
    public void onClearText() {
//        usernameEditText.setText("");
        passwordEditText.setText("");
        emailEditText.setText("");
        messageTextView.setText("");
    }

    @Override
    public void onRegisterResult(boolean result) {
        presenter.setProgressBarVisibility(View.GONE);
        registerButton.setEnabled(true);
        clearButton.setEnabled(true);
        if (result) {
           getActivity().finish();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                presenter.setProgressBarVisibility(View.VISIBLE);
                registerButton.setEnabled(false);
                clearButton.setEnabled(false);
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                presenter.register(emailEditText.getText().toString(), passwordEditText.getText().toString());
                break;
            case R.id.clear_button:
                presenter.clear();
                break;
        }
    }
}
