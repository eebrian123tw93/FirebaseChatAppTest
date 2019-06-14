package twb.brianlu.com.firebasetest.pair;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shashank.sony.fancytoastlib.FancyToast;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import mx.com.pegasus.RippleCircleButton;
import twb.brianlu.com.firebasetest.R;

public class PairFragment2 extends Fragment implements PairView2, View.OnClickListener {
  private RippleCircleButton rippleCircleButton;
  private PairPresenter2 presenter;

  @RequiresApi(api = Build.VERSION_CODES.M)
  @SuppressLint("ClickableViewAccessibility")
  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_pair2, container, false);
    rippleCircleButton = view.findViewById(R.id.rippleCircleButton);
    rippleCircleButton.setMainCircleSize(100f); // Value is in DPs
    rippleCircleButton.setMainCircleColor(Color.parseColor("#000000FF"));
    rippleCircleButton.setMainCircleBackgroundImageSize(100f); // Value is in DPs
    rippleCircleButton.setSecondaryCirclesNumber(6);
    rippleCircleButton.setSecondaryCirclesColor(Color.parseColor("#06BCF8"));
    rippleCircleButton.stopAnimation();

    rippleCircleButton.setOnMainCircleClickListener(
        new Function0<Unit>() {
          @Override
          public Unit invoke() {
            presenter.pair();
            return Unit.INSTANCE;
          }
        });

    presenter = new PairPresenter2(this);

    return view;
  }

  @Override
  public void onRippleStart() {
    if (getActivity() != null)
      getActivity()
          .runOnUiThread(
              new Runnable() {
                @Override
                public void run() {
                  rippleCircleButton.startAnimation();
                }
              });
  }

  @Override
  public void onRippleStop() {
    if (getActivity() != null)
      getActivity()
          .runOnUiThread(
              new Runnable() {
                @Override
                public void run() {
                  rippleCircleButton.stopAnimation();
                }
              });
  }

  @Override
  public void onSetRippleViewBarImage(final int image) {
    if (getActivity() != null) {
      getActivity()
          .runOnUiThread(
                  () -> {
                    if (getActivity() != null) {
                      rippleCircleButton.setMainCircleBackgroundImage(
                          getActivity().getDrawable(image));
                    }
                  });
    }
  }

  @Override
  public void onSetRippleViewAnimationDuration(int duration) {
      if (getActivity() != null) {
          getActivity()
                  .runOnUiThread(
                          () -> {
                              if (getActivity() != null) {
                                  rippleCircleButton.setAnimationDuration(duration);
                              }
                          });
      }

  }

  @Override
  public void onSetRippleViewCirclesNumber(int number) {
      if(getActivity()!=null) {
          getActivity().runOnUiThread(() -> rippleCircleButton.setSecondaryCirclesNumber(number));
      }

  }

  @Override
  public void onSetRippleViewAnimationType(int animation) {
      if(getActivity()!=null) {
          getActivity().runOnUiThread(() -> rippleCircleButton.setSecondaryCirclesAnimation(animation));
      }

  }

  @Override
  public void onSetMessage(String message, int type) {
      if(getActivity()!=null) {
          getActivity().runOnUiThread(() -> FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show());
      }

  }

  @Override
  public void onClick(View v) {}
}
