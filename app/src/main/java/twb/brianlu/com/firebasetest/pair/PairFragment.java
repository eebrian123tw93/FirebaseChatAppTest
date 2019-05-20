package twb.brianlu.com.firebasetest.pair;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.halcyon.squareprogressbar.SquareProgressBar;
import twb.brianlu.com.firebasetest.R;


public class PairFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pair, container, false);
        final SquareProgressBar squareProgressBar = view.findViewById(R.id.sprogressbar);
        squareProgressBar.setImage(R.drawable.make_friends_color);

//        squareProgressBar.setProgress(50);
        squareProgressBar.animate().start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; true; i++) {
                    if(i%102==0){
                        i=0;
                    }
                    squareProgressBar.setProgress(i);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        squareProgressBar.setRoundedCorners(true);
        return view;
    }
}
