package twb.brianlu.com.firebasetest.rooms.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.chat.ChatActivity;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.fbDataService.FirebaseDataService;
import twb.brianlu.com.firebasetest.model.Room;

public class RoomsRVPresenter extends BasePresenter {
    List<Room> rooms;
    DataChanged dataChanged;

    public RoomsRVPresenter(DataChanged dataChanged) {
        rooms = new ArrayList<>();
        this.dataChanged = dataChanged;
    }

    public void onBindViewHolder(@NonNull RoomsRVAdapter.ViewHolder viewHolder, int position) {
        final Room room = rooms.get(position);

        viewHolder.onSetName(room.getOppositeDisplayName());
        if (room.getLastMessage() != null) {
            viewHolder.onSetMessage(room.getLastMessage().getMessageText());
            viewHolder.onSetTime(String.valueOf(DateFormat.format("MM/dd(HH:mm)", room.getLastMessage().getMessageTime())));
        }else {
            viewHolder.onSetMessage("");
            viewHolder.onSetTime("");
        }
        viewHolder.onSetClickListener(
                v -> {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("roomId", room.getRoomId());
                    context.startActivity(intent);
                });

        viewHolder.onSetDeleteButton(
                v -> {
                    deleteRoom(room);
                    removeRoom(room);
                    dataChanged.onDataChanged();
                });
    }

    public void deleteRoom(Room room) {
        FirebaseDataService.deleteRoom(
                FirebaseAuth.getInstance().getCurrentUser().getUid(), room.getRoomId());
    }

    public int getItemCount() {
        return rooms.size();
    }

    public void addRoom(Room room) {
        if(rooms.stream().filter((room1)->room1.getRoomId().equals(room.getRoomId())).findAny().orElse(null)==null){
            rooms.add(room);
        }

    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    public void clearAll() {
        rooms.clear();
    }
}
