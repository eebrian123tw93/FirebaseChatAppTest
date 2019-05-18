package twb.brianlu.com.firebasetest.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.model.ChatMessage;

public class ChatMessageRVAdapter extends RecyclerView.Adapter<ChatMessageRVAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder implements ChatMessageVHView {

        private TextView messageTextView;
        private TextView usernameTextView;
        private TextView timeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text);
            usernameTextView = itemView.findViewById(R.id.message_user);
            timeTextView = itemView.findViewById(R.id.message_time);
        }

        @Override
        public void onSetMessage(String message) {
            messageTextView.setText(message);
        }

        @Override
        public void onSetUsername(String username) {
            usernameTextView.setText(username);
        }

        @Override
        public void onSetMessageTime(String messageTime) {
            timeTextView.setText(messageTime);
        }
    }


    private Context context;

    private ChatMessageRVPresenter presenter;

    public ChatMessageRVAdapter(Context context) {
        this.context = context;
        this.presenter = new ChatMessageRVPresenter();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_message, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        presenter.bindData(viewHolder, i);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    public void addMessages(List<ChatMessage> chatMessages) {
        presenter.addMessages(chatMessages);
        notifyDataSetChanged();
    }

    public void clear() {
        presenter.clear();
        notifyDataSetChanged();
    }

}
