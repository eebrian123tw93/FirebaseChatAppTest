package twb.brianlu.com.firebasetest.pair;

import android.os.Handler;
import android.os.HandlerThread;

import mx.com.pegasus.RippleCircleButton;
import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.core.BasePresenter;

public class PairPresenter2 extends BasePresenter {
    private PairView2 view;
    private Handler handler;
    private HandlerThread handlerThread;
    private boolean isPairing;

    public PairPresenter2(final PairView2 view) {
        this.view = view;
        handlerThread = new HandlerThread("PairPresenter2");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                view.onRippleStop();
//            }
//        }, 100);
        setRippleViewUnPairMode();
    }

    public void pair() {
        if (!isPairing) {
            setRippleViewPairMode();
        } else {
            setRippleViewUnPairMode();
        }
    }

    private void setRippleViewPairMode() {
        isPairing = true;
        view.onSetRippleViewCirclesNumber(10);
        view.onSetRippleViewAnimationDuration(2000);
        view.onSetRippleViewAnimationType(RippleCircleButton.EXPAND_AND_DISAPPEAR_ANIMATION);
        startThread();
        view.onRippleStart();
    }

    private void setRippleViewUnPairMode() {
        isPairing = false;
        view.onRippleStop();
        view.onSetRippleViewCirclesNumber(6);
        view.onSetRippleViewAnimationDuration(3000);
        view.onSetRippleViewAnimationType(RippleCircleButton.PROGRESSIVE_ANIMATION);
        view.onSetRippleViewBarImage(R.drawable.make_friends_color);
        view.onRippleStart();
    }

    private void startThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; isPairing; i++) {
                    if (i % 3 == 1) view.onSetRippleViewBarImage(R.drawable.make_friends);
                    else if (i % 3 == 2) view.onSetRippleViewBarImage(R.drawable.make_friends_half);
                    else view.onSetRippleViewBarImage(R.drawable.make_friends_color);
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
