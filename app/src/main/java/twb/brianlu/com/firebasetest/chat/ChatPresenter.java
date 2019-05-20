package twb.brianlu.com.firebasetest.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import twb.brianlu.com.firebasetest.chat.adapter.ChatMessageRVAdapter;
import twb.brianlu.com.firebasetest.chat.adapter.TagsRVAdapter;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.model.ChatMessage;
import twb.brianlu.com.firebasetest.model.Room;

public class ChatPresenter extends BasePresenter {

    private ChatView view;

    private Room room;

    private ChatMessageRVAdapter chatMessageRVAdapter;

    private TagsRVAdapter tagsRVAdapter;

    public ChatPresenter(ChatView view) {
        this.view = view;
        chatMessageRVAdapter = new ChatMessageRVAdapter(context);
        tagsRVAdapter=new TagsRVAdapter(context);
        //debug
        room = new Room();
        room.setRoomId("roomId");
        room.setSelfUId(user.getUid());
        room.setOppositeUid("oppositeUId");
        room.setOppositeTags(new ArrayList<String>());
       room.getOppositeTags().add("旅遊");
        room.getOppositeTags().add("健行");
        room.getOppositeTags().add("露營");
        room.getOppositeTags().add("極限運動");
        room.getOppositeTags().add("游泳");
        room.getOppositeTags().add("衝浪");
        room.getOppositeTags().add("浮潛");
        room.getOppositeTags().add("潛水");
        room.getOppositeTags().add("生存遊戲");
        room.getOppositeTags().add("射擊");

        addTags();
//        readUser();
        ///////////

        view.onSetMessagesAdapter(chatMessageRVAdapter);
        view.onSetTagsAdapter(tagsRVAdapter);
        loadMessages();
        loadTags();
    }
    public void addTags(){
        FirebaseDatabase.getInstance().getReference("rooms").child(room.getRoomId()).child("tags").child(room.getOppositeUid())
                .setValue(room.getOppositeTags());

//        FirebaseDatabase.getInstance().getReference("rooms").child(room.getRoomId()).child("tags").child(room.getOppositeUid()).push().setValue(room.getOppositeTags().get(0));
    }
    public void loadTags(){
        FirebaseDatabase.getInstance().getReference("rooms").child(room.getRoomId()).child("tags").child(room.getOppositeUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println(s);
                String value=dataSnapshot.getValue().toString();
                System.out.println(dataSnapshot.getValue());
                System.out.println(s);
                tagsRVAdapter.addTag(value);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void loadMessages() {
        FirebaseDatabase.getInstance().getReference("rooms").child(room.getRoomId()).child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println(s);
                ChatMessage chatMessage=dataSnapshot.getValue(ChatMessage.class);
                chatMessageRVAdapter.addMessage(chatMessage);
                view.onScrollToPosition(chatMessageRVAdapter.getItemCount() - 1);
                System.out.println(chatMessage.getUserUid());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void sendMessage(String message) {
        if (isLogin() && !message.isEmpty()) {
            ChatMessage chatMessage = new ChatMessage(message, user.getDisplayName(),user.getUid());
            FirebaseDatabase.getInstance()
                    .getReference("rooms")
                    .child(room.getRoomId())
                    .child("messages")
                    .push()
                    .setValue(chatMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        view.onSendMessageSuccess();
                    }
                }
            });
        }
    }
}
