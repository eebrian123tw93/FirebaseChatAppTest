package twb.brianlu.com.firebasetest.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.model.ChatMessage;

public class ChatActivity extends AppCompatActivity implements ChatView, View.OnClickListener {

    private static int SIGN_IN_REQUEST_CODE = 1000;
    private FirebaseListAdapter<ChatMessage> adapter;
    private ImageView sendImageView;
    private ChatPresenter chatPresenter;
    private RecyclerView messageRecyclerView;
    private RecyclerView tagsRecyclerView;
    private TwinklingRefreshLayout refreshLayout;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        sendImageView = findViewById(R.id.send_imaeView);
        sendImageView.setOnClickListener(this);
        input = findViewById(R.id.message_editText);
        messageRecyclerView = findViewById(R.id.messages_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messageRecyclerView.setLayoutManager(layoutManager);


        tagsRecyclerView = findViewById(R.id.tags_recyclerView);
        LinearLayoutManager tagsLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        tagsLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tagsRecyclerView.setLayoutManager(tagsLinearLayoutManager);


        chatPresenter = new ChatPresenter(this, getIntent().getStringExtra("roomId"));


        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableRefresh(false);
//        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);

            }
        });


//        ListView listOfMessages = findViewById(R.id.list_of_messages);

//        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
//                R.layout.item_self_message, FirebaseDatabase.getInstance().getReference("rooms/" + "roomId")) {
//            @Override
//            protected void populateView(View v, ChatMessage model, int position) {
//                // Get references to the views of item_messagessage.xml
//
//                TextView messageText = (TextView) v.findViewById(R.id.message_text);
//                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
//                TextView messageTime = (TextView) v.findViewById(R.id.message_time);
//
//                // Set their text
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getMessageTime()));
//            }
//        };

//        listOfMessages.setAdapter(adapter);
//        messageRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onSendMessageSuccess() {
        input.setText("");
    }

    @Override
    public void onSetMessagesAdapter(RecyclerView.Adapter adapter) {
        messageRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSetTagsAdapter(RecyclerView.Adapter adapter) {
        tagsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onScrollMessagesToPosition(int position) {
        messageRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onScrollTagsToPosition(int position) {
        tagsRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onSetMessage(String message, int type) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_imaeView:
                chatPresenter.sendMessage(input.getText().toString());
                break;
        }

    }
}
