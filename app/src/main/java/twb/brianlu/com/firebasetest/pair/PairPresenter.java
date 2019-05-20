package twb.brianlu.com.firebasetest.pair;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.core.BasePresenter;

public class PairPresenter extends BasePresenter {
    private PairView view;
    private Thread progressThread;
    private Thread changeThread;
    private boolean isRunning;

    public PairPresenter(final PairView view) {
        this.view = view;


    }

    public void pair() {
        if (!isRunning) {
            isRunning = true;
            startThread();
            setProgressBarProgress(true);
            view.onRippleStart();
        } else {
            unPair();
            view.onRippleStop();
        }


//        changeThread.interrupt();

    }

    public void startThread() {
        progressThread = new Thread() {

            @Override
            public void run() {
                super.run();
                for (int i = 0; isRunning; i++) {
                    if (i % 102 == 0) {
                        i = 0;
                    }
                    view.onSetProgressBarProgress(i);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        progressThread.start();

        changeThread = new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; isRunning; i++) {
                    if (i % 3 == 1) view.onSetProgressBarImage(R.drawable.make_friends);
                    else if (i % 3 == 2) view.onSetProgressBarImage(R.drawable.make_friends_half);
                    else view.onSetProgressBarImage(R.drawable.make_friends_color);
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        };
        changeThread.start();
    }

    public void unPair() {
        isRunning = false;
        view.onSetProgressBarProgress(0);
        view.onSetProgressBarImage(R.drawable.make_friends_color);
        setProgressBarProgress(false);
    }


    public void setProgressBarProgress(boolean visible) {
        view.onSetProgressBarVisibility(visible);
    }

    public void setProgressBarImage(int image) {
        if (!isRunning) {
            view.onSetProgressBarImage(image);
        }
    }

}
