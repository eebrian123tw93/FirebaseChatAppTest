package twb.brianlu.com.firebasetest.chat.adapter;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.model.ChatMessage;

public class ChatMessageRVPresenter {
    private List<ChatMessage>chatMessages;
    public ChatMessageRVPresenter() {
        chatMessages = new ArrayList<>();
    }
    public void bindData(ChatMessageRVAdapter.ViewHolder viewHolder, int position) {
        ChatMessage chatMessage=chatMessages.get(position);
        viewHolder.onSetUsername(chatMessage.getMessageUser());
        viewHolder.onSetMessage(chatMessage.getMessageText());
        viewHolder.onSetMessageTime(String.valueOf(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",chatMessage.getMessageTime())));

    }
    public int getItemCount(){
        return chatMessages.size();
    }

    public void addMessages(List<ChatMessage> messages) {
        this.chatMessages.addAll(messages);

    }

    public void clear() {
        this.chatMessages.clear();

    }
}
