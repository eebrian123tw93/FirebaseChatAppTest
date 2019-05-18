package twb.brianlu.com.firebasetest.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import twb.brianlu.com.firebasetest.chat.adapter.ChatMessageRVAdapter;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.model.ChatMessage;
import twb.brianlu.com.firebasetest.model.Room;

public class ChatPresenter extends BasePresenter {

    private ChatView view;

    private Room room;

    private ChatMessageRVAdapter chatMessageRVAdapter;

    public ChatPresenter(ChatView view){
        this.view=view;
        chatMessageRVAdapter=new ChatMessageRVAdapter(context);
        //debug
        room=new Room();
        room.setRoomId("roomId");
//        readUser();
        ///////////

        view.onSetAdapter(chatMessageRVAdapter);
        loadMessages();
    }
    public void loadMessages(){
        FirebaseDatabase.getInstance().getReference("rooms").child(room.getRoomId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ChatMessage>chatMessages=new ArrayList<>();
//                ChatMessage[] chatMessages=dataSnapshot.getValue(ChatMessage[].class);
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage=shot.getValue(ChatMessage.class);
                    chatMessages.add(chatMessage);
                }
                chatMessageRVAdapter.addMessages(chatMessages);
                view.onScrollToPosition(chatMessageRVAdapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("rooms").child(room.getRoomId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ChatMessage>chatMessages=new ArrayList<>();
//                ChatMessage[] chatMessages=dataSnapshot.getValue(ChatMessage[].class);
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage=shot.getValue(ChatMessage.class);
                    chatMessages.add(chatMessage);
                }
                chatMessageRVAdapter.addMessages(chatMessages);
                view.onScrollToPosition(chatMessageRVAdapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void sendMessage(String message){
        if(isLogin()) {
            ChatMessage chatMessage = new ChatMessage(message, user.getDisplayName());
            FirebaseDatabase.getInstance()
                    .getReference("rooms" )
                    .child(room.getRoomId())
                    .push()
                    .setValue(chatMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        view.onSendMessageSuccess();
                    }
                }
            });
        }
    }
}
