package twb.brianlu.com.firebasetest.pair;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ch.halcyon.squareprogressbar.SquareProgressBar;
import twb.brianlu.com.firebasetest.R;


public class PairFragment extends Fragment implements PairView {
     SquareProgressBar squareProgressBar;
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pair, container, false);
        squareProgressBar = view.findViewById(R.id.sprogressbar);
        squareProgressBar.setImage(R.drawable.make_friends);
        squareProgressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {//当前状态
                    case MotionEvent.ACTION_DOWN:
                        squareProgressBar.setImage(R.drawable.make_friends_color);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        squareProgressBar.setImage(R.drawable.make_friends);
                        break;
                    default:
                        break;
                }
                return true;

            }
        });


//        squareProgressBar.setProgress(50);
        squareProgressBar.animate().start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; true; i++) {
                    if (i % 102 == 0) {
                        i = 0;
                    }
                    squareProgressBar.setProgress(i);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; true; i++) {
                    if (i % 3 == 1) squareProgressBar.setImage(R.drawable.make_friends);
                    else if (i % 3 == 2) squareProgressBar.setImage(R.drawable.make_friends_half);
                    else squareProgressBar.setImage(R.drawable.make_friends_color);
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        squareProgressBar.setRoundedCorners(true);
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
        
    }

    @Override
    public void onSetMessage(String message, int type) {

    }

}
