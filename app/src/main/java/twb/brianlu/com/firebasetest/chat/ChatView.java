package twb.brianlu.com.firebasetest.chat;

import android.support.v7.widget.RecyclerView;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface ChatView extends BaseView {
    void onSendMessageSuccess();
    void onSetAdapter(RecyclerView.Adapter adapter);
    void onScrollToPosition(int position);
}
