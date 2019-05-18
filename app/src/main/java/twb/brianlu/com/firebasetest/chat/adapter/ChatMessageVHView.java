package twb.brianlu.com.firebasetest.chat.adapter;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface ChatMessageVHView  {
    void onSetMessage(String message);
    void onSetUsername(String username);
    void onSetMessageTime(String messageTime);
}
