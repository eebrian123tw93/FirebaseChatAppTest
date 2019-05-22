package twb.brianlu.com.firebasetest.pair;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface PairView extends BaseView {
    void onSetProgressBarVisibility(boolean visible);

    void onSetProgressBarImage(int image);

    void onSetProgressBarProgress(double value);

    void onRippleStart();

    void onRippleStop();

}
