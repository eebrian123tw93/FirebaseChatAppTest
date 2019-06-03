package twb.brianlu.com.firebasetest.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.brianlu.com.firebasetest.api.FCMApiService;
import twb.brianlu.com.firebasetest.chat.adapter.ChatMessageRVAdapter;
import twb.brianlu.com.firebasetest.chat.adapter.TagsRVAdapter;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.fbDataService.FirebaseDataService;
import twb.brianlu.com.firebasetest.model.ChatMessage;
import twb.brianlu.com.firebasetest.model.Room;
import twb.brianlu.com.firebasetest.model.fcm.Notification;

public class ChatPresenter extends BasePresenter {

    private ChatView view;

    private Room room;

    private ChatMessageRVAdapter chatMessageRVAdapter;

    private TagsRVAdapter tagsRVAdapter;

    private List<String> tags;

    public ChatPresenter(ChatView view, String roomId) {
        this.view = view;
        chatMessageRVAdapter = new ChatMessageRVAdapter(context);
        tagsRVAdapter = new TagsRVAdapter(context);
        // debug
        room = new Room();
        room.setRoomId(roomId);
        String[] ids = room.getRoomId().split("_");
        for (String uid : ids) {
            if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                room.setSelfUId(uid);
            } else {
                room.setOppositeUid(uid);
            }
        }

        ///////////

        view.onSetMessagesAdapter(chatMessageRVAdapter);
        view.onSetTagsAdapter(tagsRVAdapter);
        loadMessages();
        loadTags();
        tags = readUserTags();
    }

    public void loadTags() {
        FirebaseDatabase.getInstance()
                .getReference("rooms")
                .child(room.getRoomId())
                .child("tags")
                .child(room.getOppositeUid())
                .addChildEventListener(
                        new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                System.out.println(s);
                                String value = dataSnapshot.getValue().toString();
                                System.out.println(dataSnapshot.getValue());
                                System.out.println(s);
                                tagsRVAdapter.addTag(value);
                                view.onScrollTagsToPosition(tagsRVAdapter.getItemCount() - 1);
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
        FirebaseDatabase.getInstance()
                .getReference("rooms")
                .child(room.getRoomId())
                .child("messages")
                .addChildEventListener(
                        new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                System.out.println(s);
                                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                                chatMessageRVAdapter.addMessage(chatMessage);
                                view.onScrollMessagesToPosition(chatMessageRVAdapter.getItemCount() - 1);
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

    public void sendMessage(final String message) {
        if (isLogin() && !message.isEmpty()) {
            ChatMessage chatMessage =
                    new ChatMessage(
                            message,
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid());
            FirebaseDatabase.getInstance()
                    .getReference("rooms")
                    .child(room.getRoomId())
                    .child("messages")
                    .push()
                    .setValue(chatMessage)
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        view.onSendMessageSuccess();
                                        pushNewMessageNotification(message);

                                        if (chatMessageRVAdapter.getItemCount() % (new Random().nextInt(5) + 10) == 0) {
                                            unlockNewTagToOpposite();
                                        }
                                    }
                                }
                            });
        }
    }

    public void unlockNewTagToOpposite() {
        final Set<String> selfUnlockTags = new HashSet<>();
        final Set<String> selfAllTags = new HashSet<>(tags);
        FirebaseDatabase.getInstance()
                .getReference("rooms")
                .child(room.getRoomId())
                .child("tags")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    selfUnlockTags.add(snapshot.getValue().toString());
                                }
                                System.out.println(selfUnlockTags.size());
                                selfAllTags.removeAll(selfUnlockTags);
                                final List<String> asList = new ArrayList(selfAllTags);
                                if (asList.size() > 1) {
                                    Collections.shuffle(asList);
                                    final String unlockTag = asList.get(0);
                                    FirebaseDataService.addTagToRoom(
                                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                            room.getRoomId(),
                                            unlockTag,
                                            new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if (task.isSuccessful()) {
                                                        pushNewTagUnlockNotification(unlockTag);
                                                    }
                                                }
                                            });
                                } else {

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
    }

    public void pushNewMessageNotification(final String message) {
        final Observer<Response<ResponseBody>> observer =
                new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };
        //
        Observer<String> takenObserver =
                new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        Notification notification = new Notification();
                        notification.setTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        notification.setRoomId(room.getRoomId());
                        notification.setBody(message);
                        FCMApiService.getInstance().pushNotification(observer, s, notification, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };
        FirebaseDataService.getUserToken(room.getOppositeUid(), takenObserver);
    }

    public void pushNewTagUnlockNotification(final String tag) {
        final Observer<Response<ResponseBody>> observer =
                new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };

        Observer<String> takenObserver =
                new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        Notification notification = new Notification();
                        notification.setTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        notification.setRoomId(room.getRoomId());
                        notification.setBody("對方解鎖新的Tag: " + tag);
                        FCMApiService.getInstance().pushNotification(observer, s, notification, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };
        FirebaseDataService.getUserToken(room.getOppositeUid(), takenObserver);
    }
}
