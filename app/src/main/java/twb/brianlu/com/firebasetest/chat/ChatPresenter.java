package twb.brianlu.com.firebasetest.chat;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
import twb.brianlu.com.firebasetest.model.FileModel;
import twb.brianlu.com.firebasetest.model.Room;
import twb.brianlu.com.firebasetest.model.fcm.Notification;
import twb.brianlu.com.firebasetest.model.fcm.WebrtcCall;

public class ChatPresenter extends BasePresenter {

  private ChatView view;

  private Room room;

  private ChatMessageRVAdapter chatMessageRVAdapter;

  private TagsRVAdapter tagsRVAdapter;

  private List<String> tags;

  private static final String TAG = "ChatPresenter";
  private static final String URL_STORAGE_REFERENCE = "gs://firenbasetest.appspot.com/";
  private static final String FOLDER_STORAGE_IMG = "images";

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
              public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

              @Override
              public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

              @Override
              public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {}
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
              public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

              @Override
              public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

              @Override
              public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {}
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
              task -> {
                if (task.isSuccessful()) {
                  view.onSendMessageSuccess();
                  pushNewMessageNotification(message);
                  tagUnlock();
                }
              });
    }
  }

  public void sendFile(final String message, final FileModel fileModel) {
    // package file into ChatMessage model and send
    if (isLogin()) {
      ChatMessage chatMessage = new ChatMessage();
      chatMessage.setMessageText(message);
      chatMessage.setMessageUser(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
      chatMessage.setUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
      chatMessage.setMessageTime(new Date().getTime());
      chatMessage.setFileModel(fileModel);

      FirebaseDatabase.getInstance()
          .getReference("rooms")
          .child(room.getRoomId())
          .child("messages")
          .push()
          .setValue(chatMessage)
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  view.onSendMessageSuccess();
                  pushNewMessageNotification(
                      FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "傳送了照片");
                  tagUnlock();
                }
              });
    }
  }

  private void tagUnlock() {
    // tag unlock logic here
    int messagesPerTag = 10;
    if (chatMessageRVAdapter.getItemCount() % messagesPerTag == 0) unlockNewTagToOpposite();
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
              public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
  }

  public void pushNewMessageNotification(final String message) {
    final Observer<Response<ResponseBody>> observer =
        new Observer<Response<ResponseBody>>() {
          @Override
          public void onSubscribe(Disposable d) {}

          @Override
          public void onNext(Response<ResponseBody> responseBodyResponse) {}

          @Override
          public void onError(Throwable e) {}

          @Override
          public void onComplete() {}
        };
    //
    Observer<String> takenObserver =
        new Observer<String>() {
          @Override
          public void onSubscribe(Disposable d) {}

          @Override
          public void onNext(String s) {
            Notification notification = new Notification();
            notification.setTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            notification.setRoomId(room.getRoomId());
            notification.setBody(message);
            FCMApiService.getInstance().pushNotification(observer, s, notification, false);
          }

          @Override
          public void onError(Throwable e) {}

          @Override
          public void onComplete() {}
        };
    FirebaseDataService.getUserToken(room.getOppositeUid(), takenObserver);
  }

  public void pushNewTagUnlockNotification(final String tag) {
    final Observer<Response<ResponseBody>> observer =
        new Observer<Response<ResponseBody>>() {
          @Override
          public void onSubscribe(Disposable d) {}

          @Override
          public void onNext(Response<ResponseBody> responseBodyResponse) {}

          @Override
          public void onError(Throwable e) {}

          @Override
          public void onComplete() {}
        };

    Observer<String> takenObserver =
        new Observer<String>() {
          @Override
          public void onSubscribe(Disposable d) {}

          @Override
          public void onNext(String s) {
            Notification notification = new Notification();
            notification.setTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            notification.setRoomId(room.getRoomId());
            notification.setBody("對方解鎖新的Tag: " + tag);
            FCMApiService.getInstance().pushNotification(observer, s, notification, false);
          }

          @Override
          public void onError(Throwable e) {}

          @Override
          public void onComplete() {}
        };
    FirebaseDataService.getUserToken(room.getOppositeUid(), takenObserver);
  }

  public void phoneCall(final WebrtcCall webrtcCall) {
    final Observer<Response<ResponseBody>> observer =
        new Observer<Response<ResponseBody>>() {
          @Override
          public void onSubscribe(Disposable d) {}

          @Override
          public void onNext(Response<ResponseBody> responseBodyResponse) {}

          @Override
          public void onError(Throwable e) {}

          @Override
          public void onComplete() {}
        };

    Observer<String> takenObserver =
        new Observer<String>() {
          @Override
          public void onSubscribe(Disposable d) {}

          @Override
          public void onNext(String s) {
            //                        webrtcCall
            //                        webrtcCa
            //                        webrtcCall.
            FCMApiService.getInstance().phoneCall(observer, s, webrtcCall, false);
          }

          @Override
          public void onError(Throwable e) {}

          @Override
          public void onComplete() {}
        };
    FirebaseDataService.getUserToken(room.getOppositeUid(), takenObserver);
  }

  public void call() {
    UUID uuid = UUID.randomUUID();
    String roomId = uuid.toString();
    view.onCall(roomId);
    WebrtcCall webrtcCall = new WebrtcCall();
    webrtcCall.setRoomId(roomId);
    webrtcCall.setSelfUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
    webrtcCall.setDisplayName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
    phoneCall(webrtcCall);
  }

  public void sendFileFirebase(final Uri file) {

    FirebaseStorage firebaseStorageInstance = FirebaseStorage.getInstance();
    StorageReference storageReference =
        firebaseStorageInstance
            .getReferenceFromUrl(URL_STORAGE_REFERENCE)
            .child(FOLDER_STORAGE_IMG);
    String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    final String filename =
        currentUserUid
            + DateFormat.format("yyyy-MM-ddThhmmss.sss", new Date()).toString()
            + UUID.randomUUID();
    StorageReference imageGalleryRef = storageReference.child(filename + "_img");
    UploadTask uploadTask = imageGalleryRef.putFile(file);

    //    Task<Uri> urlTask =
    uploadTask
        .continueWithTask(
            task -> {
              if (!task.isSuccessful()) {
                throw task.getException();
              }

              // Continue with the task to get the download URL
              return imageGalleryRef.getDownloadUrl();
            })
        .addOnCompleteListener(
            task -> {
              if (task.isSuccessful()) {
                Uri downloadUrl = task.getResult();
                FileModel fileModel = new FileModel();
                fileModel.setFileName(filename);
                fileModel.setType("file");
                fileModel.setFileUrl(downloadUrl.toString());
                sendFile(
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "傳送了照片",
                    fileModel);
              } else {
                Log.e(TAG, "onComplete: taskFailed");
              }
            });
  }
}
