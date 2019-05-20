package twb.brianlu.com.firebasetest.rooms.adapter;

import android.view.View;

public interface RoomsVHView {


    void onSetTime(String time);

    void onSetName(String name);

    void onSetMessage(String message);

    void onSetClickListener(View.OnClickListener listener);

    void onSetLongClickListener(View.OnLongClickListener listener);
}
