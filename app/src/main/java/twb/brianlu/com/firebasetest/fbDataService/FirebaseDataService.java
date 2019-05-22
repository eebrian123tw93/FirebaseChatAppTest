package twb.brianlu.com.firebasetest.fbDataService;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;

public class FirebaseDataService {
    public static void deleteRoom(final String uid, final String roomId) {
        FirebaseDatabase.getInstance().getReference("users").child(uid)
                .child("rooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> rooms = new ArrayList<>();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    rooms.add(shot.getValue().toString());
                }
                rooms.remove(roomId);
                FirebaseDatabase.getInstance().getReference("users").child(uid)
                        .child("rooms").setValue(rooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void addRoom(final String uid, final String roomId) {
        FirebaseDatabase.getInstance().getReference("users").child(uid)
                .child("rooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> rooms = new ArrayList<>();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    rooms.add(shot.getValue().toString());
                }
                if (!rooms.contains(roomId)) rooms.add(roomId);
                FirebaseDatabase.getInstance().getReference("users").child(uid)
                        .child("rooms").setValue(rooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void addTag(final String uid, final String tag) {
        FirebaseDatabase.getInstance().getReference("users").child(uid)
                .child("tags").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> tags = new ArrayList<>();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    tags.add(shot.getValue().toString());
                }
                if (!tags.contains(tag)) tags.add(tag);
                FirebaseDatabase.getInstance().getReference("users").child(uid)
                        .child("tags").setValue(tag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static Task<Void> addTag(final String uid, final List<String> tags) {
        return FirebaseDatabase.getInstance().getReference("users").child(uid)
                .child("tags").setValue(tags);
    }

    public static void deleteTag(final String uid, final String tag) {
        FirebaseDatabase.getInstance().getReference("users").child(uid)
                .child("tags").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> tags = new ArrayList<>();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    tags.add(shot.getValue().toString());
                }
                tags.remove(tag);
                FirebaseDatabase.getInstance().getReference("users").child(uid)
                        .child("tags").setValue(tag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void addTagToRoom(String uid, final String roomId, final String tag) {
        FirebaseDatabase.getInstance().getReference("rooms").child(roomId)
                .child("tags").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> tags = new ArrayList<>();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    tags.add(shot.getValue().toString());
                }
                if (!tags.contains(tags)) tags.add(tag);
                FirebaseDatabase.getInstance().getReference("rooms").child(roomId)
                        .child("tags").setValue(tags);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void getUserToken(String uid, final Observer<String> observer) {
        FirebaseDatabase.getInstance().getReference("users").child(uid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                observer.onNext(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
