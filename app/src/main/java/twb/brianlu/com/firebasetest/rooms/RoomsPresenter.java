package twb.brianlu.com.firebasetest.rooms;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import twb.brianlu.com.firebasetest.core.BaseApplication;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.model.Room;
import twb.brianlu.com.firebasetest.rooms.adapter.RoomsRVAdapter;

public class RoomsPresenter extends BasePresenter {

    private RoomsView view;
    private RoomsRVAdapter roomsRVAdapter;

    public RoomsPresenter(RoomsView view) {
        this.view = view;
        roomsRVAdapter = new RoomsRVAdapter(BaseApplication.getContext());
        loadRooms();
    }

    public void setRoomsRVAdapter() {
        view.onSetRoomsAdapter(roomsRVAdapter);
    }

    public void loadRooms() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("rooms").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println(dataSnapshot.getValue());
                System.out.println(s);

                Room room = new Room();
                room.setRoomKey(dataSnapshot.getKey());
                room.setRoomId(dataSnapshot.getValue().toString());

                String[] ids = room.getRoomId().split("_");
                for (String uid : ids) {
                    if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        room.setSelfUId(uid);
                    } else {
                        room.setOppositeUid(uid);
                    }
                }

                roomsRVAdapter.addRoom(room);
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

}
