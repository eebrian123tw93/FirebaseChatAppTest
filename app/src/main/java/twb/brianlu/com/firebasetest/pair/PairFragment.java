package twb.brianlu.com.firebasetest.pair;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.skyfishjy.library.RippleBackground;

import ch.halcyon.squareprogressbar.SquareProgressBar;
import mx.com.pegasus.RippleCircleButton;
import twb.brianlu.com.firebasetest.R;


public class PairFragment extends Fragment implements PairView, View.OnTouchListener, View.OnLongClickListener {
    SquareProgressBar squareProgressBar;
    private PairPresenter presenter;
    private RippleBackground rippleBackground;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pair, container, false);
        squareProgressBar = view.findViewById(R.id.sprogressbar);
        squareProgressBar.setOnTouchListener(this);
        squareProgressBar.setImage(R.drawable.make_friends);
        squareProgressBar.setWidth(5);

        presenter = new PairPresenter(this);
        presenter.setProgressBarProgress(false);

        rippleBackground = view.findViewById(R.id.content);

//        rippleBackground.setVisibility(View.INVISIBLE);



        return view;
    }


    @Override
    public void onSetProgressBarImage(int image) {
        squareProgressBar.setImage(image);
    }

    @Override
    public void onSetProgressBarProgress(double value) {
        squareProgressBar.setProgress(value);
    }

    @Override
    public void onSetProgressBarVisibility(boolean visible) {
        squareProgressBar.setRoundedCorners(visible);
    }

    @Override
    public void onRippleStart() {
        rippleBackground.startRippleAnimation();

    }

    @Override
    public void onRippleStop() {
        rippleBackground.stopRippleAnimation();
        rippleBackground.clearAnimation();

    }

    @Override
    public void onSetMessage(String message, int type) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.sprogressbar:
                switch (event.getAction()) {//当前状态
                    case MotionEvent.ACTION_DOWN:
                        presenter.setProgressBarImage(R.drawable.make_friends_color);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        presenter.setProgressBarImage(R.drawable.make_friends);
                        presenter.pair();


                        break;
                    default:
                        break;
                }
                return true;
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
//        presenter.unPair();
        return true;
    }
}
