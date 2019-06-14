package twb.brianlu.com.firebasetest.chat;

import android.support.v7.widget.RecyclerView;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface ChatView extends BaseView {
  void onSendMessageSuccess();

  void onSetMessagesAdapter(RecyclerView.Adapter adapter);

  void onSetTagsAdapter(RecyclerView.Adapter adapter);

  void onScrollMessagesToPosition(int position);

  void onScrollTagsToPosition(int position);

  void onCall(String roomId);

  void onSetPhoneImageAlpha(int alpha);
}
