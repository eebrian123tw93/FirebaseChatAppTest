package twb.brianlu.com.firebasetest.rooms.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.chat.ChatActivity;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.model.Room;

public class RoomsRVPresenter extends BasePresenter {
    List<Room>rooms;
    DataChanged dataChanged;

    public RoomsRVPresenter(DataChanged dataChanged){
        rooms=new ArrayList<>();
        this.dataChanged=dataChanged;
    }

    public void onBindViewHolder(@NonNull RoomsRVAdapter.ViewHolder viewHolder, int position){
        final Room room=rooms.get(position);

        viewHolder.onSetName(room.getRoomId());
        viewHolder.onSetClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("roomId",room.getRoomId());
                context.startActivity(intent);
            }
        });

        viewHolder.onSetLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteRoom(room);
                removeRoom(room);
                dataChanged.onDataChanged();
                return false;
            }
        });

    }

    public void deleteRoom(Room room){
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("rooms").child(room.getRoomKey()).removeValue();
    }

    public  int getItemCount(){
        return rooms.size();
    }
    public void addRoom(Room room){
        rooms.add(room);
    }
    public void removeRoom(Room room){
        rooms.remove(room);
    }
    public void clearAll(){
        rooms.clear();
    }
}
