package twb.brianlu.com.firebasetest.rooms;

import android.support.v7.widget.RecyclerView;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface RoomsView extends BaseView {
    void onSetRoomsAdapter(RecyclerView.Adapter adapter);
}
