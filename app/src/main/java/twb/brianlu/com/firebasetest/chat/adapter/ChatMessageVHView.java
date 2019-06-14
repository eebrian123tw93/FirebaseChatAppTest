package twb.brianlu.com.firebasetest.chat.adapter;

public interface ChatMessageVHView {
  void onSetMessage(String message);

  void onSetUsername(String username);

  void onSetMessageTime(String messageTime);

  void onSetImage(String image);

  void onSetImageVisibility(int option);
}
