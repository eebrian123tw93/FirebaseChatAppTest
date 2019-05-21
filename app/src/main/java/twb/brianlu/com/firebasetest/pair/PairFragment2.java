package twb.brianlu.com.firebasetest.pair;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.HandlerThread;
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


public class PairFragment2 extends Fragment {
    RippleCircleButton rippleCircleButton;
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pair2, container, false);


         rippleCircleButton=view.findViewById(R.id.rippleCircleButton);
        rippleCircleButton.setMainCircleSize(100f); //Value is in DPs
        rippleCircleButton.setMainCircleColor(Color.parseColor("#000000FF"));

        rippleCircleButton.setMainCircleBackgroundImage(ContextCompat.getDrawable(getContext(), R.drawable.make_friends_color));
        rippleCircleButton.setMainCircleBackgroundImageSize(100f); //Value is in DPs

        rippleCircleButton.setSecondaryCirclesNumber(6);


        rippleCircleButton.setSecondaryCirclesColor(Color.parseColor("#06BCF8"));

        rippleCircleButton.setAnimationDuration(3000); //Value is in Milliseconds


        rippleCircleButton.setSecondaryCirclesAnimation(RippleCircleButton.PROGRESSIVE_ANIMATION);

        rippleCircleButton.startAnimation();

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        rippleCircleButton.stopAnimation();
//                        rippleCircleButton.setSecondaryCirclesNumber(10);
                        rippleCircleButton.setAnimationDuration(2000);
                        rippleCircleButton.setSecondaryCirclesAnimation(RippleCircleButton.EXPAND_AND_DISAPPEAR_ANIMATION);
                        rippleCircleButton.startAnimation();
                    }
                });

            }
        }.start();

        return view;
    }



}
