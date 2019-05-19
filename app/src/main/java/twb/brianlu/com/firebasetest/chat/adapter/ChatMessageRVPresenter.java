package twb.brianlu.com.firebasetest.chat.adapter;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.model.ChatMessage;

public class ChatMessageRVPresenter extends BasePresenter {
    private List<ChatMessage> chatMessages;

    public ChatMessageRVPresenter() {
        chatMessages = new ArrayList<>();
    }

    public void bindData(ChatMessageRVAdapter.ViewHolder viewHolder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        viewHolder.onSetUsername(chatMessage.getMessageUser());
        viewHolder.onSetMessage(chatMessage.getMessageText());
        viewHolder.onSetMessageTime(String.valueOf(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", chatMessage.getMessageTime())));

    }

    public int getItemCount() {
        return chatMessages.size();
    }

    public void addMessages(List<ChatMessage> messages) {
        this.chatMessages.addAll(messages);

    }

    public boolean isSelf(int position){
        ChatMessage chatMessage=this.chatMessages.get(position);
        return chatMessage.getUserUid().equals(user.getUid());
    }

    public void clear() {
        this.chatMessages.clear();

    }
}
