package twb.brianlu.com.firebasetest.chat.adapter;

import android.text.format.DateFormat;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.model.ChatMessage;

public class ChatMessageRVPresenter extends BasePresenter {
  private List<ChatMessage> chatMessages;

  public ChatMessageRVPresenter() {
    chatMessages = new ArrayList<>();
  }

  public List<ChatMessage> getChatMessages() {
    return chatMessages;
  }

  public void bindData(ChatMessageRVAdapter.ViewHolder viewHolder, int position) {
    ChatMessage chatMessage = chatMessages.get(position);
    viewHolder.onSetUsername(chatMessage.getMessageUser());
    viewHolder.onSetMessageTime(
        String.valueOf(DateFormat.format("MM/dd(HH:mm)", chatMessage.getMessageTime())));

    if (chatMessage.getFileModel() != null) {
      viewHolder.onSetImage(chatMessage.getFileModel().getFileUrl());
      viewHolder.onSetImageVisibility(View.VISIBLE);
      viewHolder.onSetMessage("");
    } else {
      viewHolder.onSetImageVisibility(View.GONE);
      viewHolder.onSetMessage(chatMessage.getMessageText());
    }
  }

  public int getItemCount() {
    return chatMessages.size();
  }

  public void addMessages(List<ChatMessage> messages) {
    this.chatMessages.addAll(messages);
  }

  public void addMessage(ChatMessage chatMessage) {
    this.chatMessages.add(chatMessage);
  }

  public boolean isSelf(int position) {
    ChatMessage chatMessage = this.chatMessages.get(position);
    return chatMessage.getUserUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid());
  }

  public void clear() {
    this.chatMessages.clear();
  }
}
