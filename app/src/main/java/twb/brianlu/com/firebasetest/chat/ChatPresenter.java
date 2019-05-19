package twb.brianlu.com.firebasetest.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.chat.adapter.ChatMessageRVAdapter;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.model.ChatMessage;
import twb.brianlu.com.firebasetest.model.Room;

public class ChatPresenter extends BasePresenter {

    private ChatView view;

    private Room room;

    private ChatMessageRVAdapter chatMessageRVAdapter;

    public ChatPresenter(ChatView view) {
        this.view = view;
        chatMessageRVAdapter = new ChatMessageRVAdapter(context);
        //debug
        room = new Room();
        room.setRoomId("roomId");
//        readUser();
        ///////////

        view.onSetAdapter(chatMessageRVAdapter);
        loadMessages();
    }

    public void loadMessages() {
//        FirebaseDatabase.getInstance().getReference("rooms").child(room.getRoomId()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<ChatMessage> chatMessages = new ArrayList<>();
////                ChatMessage[] chatMessages=dataSnapshot.getValue(ChatMessage[].class);
//                for (DataSnapshot shot : dataSnapshot.getChildren()) {
//                    ChatMessage chatMessage = shot.getValue(ChatMessage.class);
//                    chatMessages.add(chatMessage);
//                }
//                chatMessageRVAdapter.addMessages(chatMessages);
//                view.onScrollToPosition(chatMessageRVAdapter.getItemCount() - 1);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        FirebaseDatabase.getInstance().getReference("rooms").child(room.getRoomId()).addChildEventListener(new ChildEventListener() {
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

//        FirebaseDatabase.getInstance().getReference("rooms").child(room.getRoomId()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<ChatMessage> chatMessages = new ArrayList<>();
////                ChatMessage[] chatMessages=dataSnapshot.getValue(ChatMessage[].class);
//                for (DataSnapshot shot : dataSnapshot.getChildren()) {
//                    ChatMessage chatMessage = shot.getValue(ChatMessage.class);
//                    chatMessages.add(chatMessage);
//                }
//                chatMessageRVAdapter.addMessages(chatMessages);
//                view.onScrollToPosition(chatMessageRVAdapter.getItemCount() - 1);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }


    public void sendMessage(String message) {
        if (isLogin() && !message.isEmpty()) {
            ChatMessage chatMessage = new ChatMessage(message, user.getDisplayName(),user.getUid());
            FirebaseDatabase.getInstance()
                    .getReference("rooms")
                    .child(room.getRoomId())
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
