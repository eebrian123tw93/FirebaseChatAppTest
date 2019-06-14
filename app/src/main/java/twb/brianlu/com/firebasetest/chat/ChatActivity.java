package twb.brianlu.com.firebasetest.chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.Random;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.apprtc.CallActivity;
import twb.brianlu.com.firebasetest.model.ChatMessage;

public class ChatActivity extends AppCompatActivity
    implements ChatView, View.OnClickListener, View.OnLongClickListener {
  private static final String TAG = "ChatActivity";
  private static int SIGN_IN_REQUEST_CODE = 1000;
  private FirebaseListAdapter<ChatMessage> adapter;
  private ImageView sendImageView;
  private ChatPresenter chatPresenter;
  private RecyclerView messageRecyclerView;
  private RecyclerView tagsRecyclerView;
  private TwinklingRefreshLayout refreshLayout;
  private EditText input;
  private Button imgGalleryButton;

  private static final int CONNECTION_REQUEST = 1;

  private static final int PICK_IMAGE_CODE = 2;

  private static boolean commandLineRun = false;
  private SharedPreferences sharedPref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);

    // Get setting keys.
    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

    sendImageView = findViewById(R.id.send_imageView);
    sendImageView.setOnClickListener(this);
    sendImageView.setOnLongClickListener(this);
    input = findViewById(R.id.message_editText);
    messageRecyclerView = findViewById(R.id.messages_recyclerView);
    imgGalleryButton = findViewById(R.id.img_gallery_button);
    imgGalleryButton.setOnClickListener(this);

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
    refreshLayout.setOnRefreshListener(
        new RefreshListenerAdapter() {

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
    //                R.layout.item_self_message,
    // FirebaseDatabase.getInstance().getReference("rooms/" + "roomId")) {
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
  public void onSetMessage(String message, int type) {}

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.send_imageView:
        chatPresenter.sendMessage(input.getText().toString());
        break;
      case R.id.img_gallery_button:
        Log.i(TAG, "onClick: gallery");
        photoGalleryIntent();
        break;
    }
  }

  private void photoGalleryIntent() {
    //    BSImagePicker singleSelectionPicker = new
    // BSImagePicker.Builder("twb.conwaybrian.com.twbandroid.fileprovider")
    //            .setMaximumDisplayingImages(0) //Default: Integer.MAX_VALUE. Don't worry about
    // performance :)
    //            .setSpanCount(1) //Default: 3. This is the number of columns
    //
    //            .setGridSpacing(Utils.dp2px(2)) //Default: 2dp. Remember to pass in a value in
    // pixel.
    //            .setPeekHeight(Utils.dp2px(360)) //Default: 360dp. This is the initial height of
    // the dialog.
    //            //Default: show. Set this if you don't want user to take photo.
    //            .hideGalleryTile() //Default: show. Set this if you don't want to further let user
    // select from a gallery app. In such case, I suggest you to set maximum displaying images to
    // Integer.MAX_VALUE.
    //            .setTag("A request ID") //Default: null. Set this if you need to identify which
    // picker is calling back your fragment / activity.
    //            //Default: true. Set this if you do not want the picker to dismiss right after
    // selection. But then you will have to dismiss by yourself.
    //            .build();

    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "choose photo"), PICK_IMAGE_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK) {
      Uri selectedImageUri = data.getData();
      if (selectedImageUri != null) chatPresenter.sendFileFirebase(selectedImageUri);
      else Log.e(TAG, "onActivityResult: selectedImageUri is null");
    }
  }

  @Override
  public boolean onLongClick(View v) {
    switch (v.getId()) {
      case R.id.send_imageView:
        chatPresenter.call();
        break;
    }
    return false;
  }

  @Override
  public void onCall(String roomId) {
    Log.i(TAG, roomId);
    connectToRoom(roomId, false, false, false, 0);
  }

  private void connectToRoom(
      String roomId,
      boolean commandLineRun,
      boolean loopback,
      boolean useValuesFromIntent,
      int runTimeMs) {
    ChatActivity.commandLineRun = commandLineRun;

    // roomId is random for loopback.
    if (loopback) {
      roomId = Integer.toString((new Random()).nextInt(100000000));
    }

    String roomUrl =
        sharedPref.getString(
            keyprefRoomServerUrl, getString(R.string.pref_room_server_url_default));

    // Video call enabled flag.
    boolean videoCallEnabled =
        sharedPrefGetBoolean(
            R.string.pref_videocall_key,
            CallActivity.EXTRA_VIDEO_CALL,
            R.string.pref_videocall_default,
            useValuesFromIntent);

    // Use screencapture option.
    boolean useScreencapture =
        sharedPrefGetBoolean(
            R.string.pref_screencapture_key,
            CallActivity.EXTRA_SCREENCAPTURE,
            R.string.pref_screencapture_default,
            useValuesFromIntent);

    // Use Camera2 option.
    boolean useCamera2 =
        sharedPrefGetBoolean(
            R.string.pref_camera2_key,
            CallActivity.EXTRA_CAMERA2,
            R.string.pref_camera2_default,
            useValuesFromIntent);

    // Get default codecs.
    String videoCodec =
        sharedPrefGetString(
            R.string.pref_videocodec_key,
            CallActivity.EXTRA_VIDEOCODEC,
            R.string.pref_videocodec_default,
            useValuesFromIntent);
    String audioCodec =
        sharedPrefGetString(
            R.string.pref_audiocodec_key,
            CallActivity.EXTRA_AUDIOCODEC,
            R.string.pref_audiocodec_default,
            useValuesFromIntent);

    // Check HW codec flag.
    boolean hwCodec =
        sharedPrefGetBoolean(
            R.string.pref_hwcodec_key,
            CallActivity.EXTRA_HWCODEC_ENABLED,
            R.string.pref_hwcodec_default,
            useValuesFromIntent);

    // Check Capture to texture.
    boolean captureToTexture =
        sharedPrefGetBoolean(
            R.string.pref_capturetotexture_key,
            CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED,
            R.string.pref_capturetotexture_default,
            useValuesFromIntent);

    // Check FlexFEC.
    boolean flexfecEnabled =
        sharedPrefGetBoolean(
            R.string.pref_flexfec_key,
            CallActivity.EXTRA_FLEXFEC_ENABLED,
            R.string.pref_flexfec_default,
            useValuesFromIntent);

    // Check Disable Audio Processing flag.
    boolean noAudioProcessing =
        sharedPrefGetBoolean(
            R.string.pref_noaudioprocessing_key,
            CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED,
            R.string.pref_noaudioprocessing_default,
            useValuesFromIntent);

    boolean aecDump =
        sharedPrefGetBoolean(
            R.string.pref_aecdump_key,
            CallActivity.EXTRA_AECDUMP_ENABLED,
            R.string.pref_aecdump_default,
            useValuesFromIntent);

    boolean saveInputAudioToFile =
        sharedPrefGetBoolean(
            R.string.pref_enable_save_input_audio_to_file_key,
            CallActivity.EXTRA_SAVE_INPUT_AUDIO_TO_FILE_ENABLED,
            R.string.pref_enable_save_input_audio_to_file_default,
            useValuesFromIntent);

    // Check OpenSL ES enabled flag.
    boolean useOpenSLES =
        sharedPrefGetBoolean(
            R.string.pref_opensles_key,
            CallActivity.EXTRA_OPENSLES_ENABLED,
            R.string.pref_opensles_default,
            useValuesFromIntent);

    // Check Disable built-in AEC flag.
    boolean disableBuiltInAEC =
        sharedPrefGetBoolean(
            R.string.pref_disable_built_in_aec_key,
            CallActivity.EXTRA_DISABLE_BUILT_IN_AEC,
            R.string.pref_disable_built_in_aec_default,
            useValuesFromIntent);

    // Check Disable built-in AGC flag.
    boolean disableBuiltInAGC =
        sharedPrefGetBoolean(
            R.string.pref_disable_built_in_agc_key,
            CallActivity.EXTRA_DISABLE_BUILT_IN_AGC,
            R.string.pref_disable_built_in_agc_default,
            useValuesFromIntent);

    // Check Disable built-in NS flag.
    boolean disableBuiltInNS =
        sharedPrefGetBoolean(
            R.string.pref_disable_built_in_ns_key,
            CallActivity.EXTRA_DISABLE_BUILT_IN_NS,
            R.string.pref_disable_built_in_ns_default,
            useValuesFromIntent);

    // Check Disable gain control
    boolean disableWebRtcAGCAndHPF =
        sharedPrefGetBoolean(
            R.string.pref_disable_webrtc_agc_and_hpf_key,
            CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF,
            R.string.pref_disable_webrtc_agc_and_hpf_key,
            useValuesFromIntent);

    // Get video resolution from settings.
    int videoWidth = 0;
    int videoHeight = 0;
    if (useValuesFromIntent) {
      videoWidth = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_WIDTH, 0);
      videoHeight = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_HEIGHT, 0);
    }
    if (videoWidth == 0 && videoHeight == 0) {
      String resolution =
          sharedPref.getString(keyprefResolution, getString(R.string.pref_resolution_default));
      String[] dimensions = resolution.split("[ x]+");
      if (dimensions.length == 2) {
        try {
          videoWidth = Integer.parseInt(dimensions[0]);
          videoHeight = Integer.parseInt(dimensions[1]);
        } catch (NumberFormatException e) {
          videoWidth = 0;
          videoHeight = 0;
          Log.e(TAG, "Wrong video resolution setting: " + resolution);
        }
      }
    }

    // Get camera fps from settings.
    int cameraFps = 0;
    if (useValuesFromIntent) {
      cameraFps = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_FPS, 0);
    }
    if (cameraFps == 0) {
      String fps = sharedPref.getString(keyprefFps, getString(R.string.pref_fps_default));
      String[] fpsValues = fps.split("[ x]+");
      if (fpsValues.length == 2) {
        try {
          cameraFps = Integer.parseInt(fpsValues[0]);
        } catch (NumberFormatException e) {
          cameraFps = 0;
          Log.e(TAG, "Wrong camera fps setting: " + fps);
        }
      }
    }

    // Check capture quality slider flag.
    boolean captureQualitySlider =
        sharedPrefGetBoolean(
            R.string.pref_capturequalityslider_key,
            CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
            R.string.pref_capturequalityslider_default,
            useValuesFromIntent);

    // Get video and audio start bitrate.
    int videoStartBitrate = 0;
    if (useValuesFromIntent) {
      videoStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_VIDEO_BITRATE, 0);
    }
    if (videoStartBitrate == 0) {
      String bitrateTypeDefault = getString(R.string.pref_maxvideobitrate_default);
      String bitrateType = sharedPref.getString(keyprefVideoBitrateType, bitrateTypeDefault);
      if (!bitrateType.equals(bitrateTypeDefault)) {
        String bitrateValue =
            sharedPref.getString(
                keyprefVideoBitrateValue, getString(R.string.pref_maxvideobitratevalue_default));
        videoStartBitrate = Integer.parseInt(bitrateValue);
      }
    }

    int audioStartBitrate = 0;
    if (useValuesFromIntent) {
      audioStartBitrate = getIntent().getIntExtra(CallActivity.EXTRA_AUDIO_BITRATE, 0);
    }
    if (audioStartBitrate == 0) {
      String bitrateTypeDefault = getString(R.string.pref_startaudiobitrate_default);
      String bitrateType = sharedPref.getString(keyprefAudioBitrateType, bitrateTypeDefault);
      if (!bitrateType.equals(bitrateTypeDefault)) {
        String bitrateValue =
            sharedPref.getString(
                keyprefAudioBitrateValue, getString(R.string.pref_startaudiobitratevalue_default));
        audioStartBitrate = Integer.parseInt(bitrateValue);
      }
    }

    // Check statistics display option.
    boolean displayHud =
        sharedPrefGetBoolean(
            R.string.pref_displayhud_key,
            CallActivity.EXTRA_DISPLAY_HUD,
            R.string.pref_displayhud_default,
            useValuesFromIntent);

    boolean tracing =
        sharedPrefGetBoolean(
            R.string.pref_tracing_key,
            CallActivity.EXTRA_TRACING,
            R.string.pref_tracing_default,
            useValuesFromIntent);

    // Check Enable RtcEventLog.
    boolean rtcEventLogEnabled =
        sharedPrefGetBoolean(
            R.string.pref_enable_rtceventlog_key,
            CallActivity.EXTRA_ENABLE_RTCEVENTLOG,
            R.string.pref_enable_rtceventlog_default,
            useValuesFromIntent);

    boolean useLegacyAudioDevice =
        sharedPrefGetBoolean(
            R.string.pref_use_legacy_audio_device_key,
            CallActivity.EXTRA_USE_LEGACY_AUDIO_DEVICE,
            R.string.pref_use_legacy_audio_device_default,
            useValuesFromIntent);

    // Get datachannel options
    boolean dataChannelEnabled =
        sharedPrefGetBoolean(
            R.string.pref_enable_datachannel_key,
            CallActivity.EXTRA_DATA_CHANNEL_ENABLED,
            R.string.pref_enable_datachannel_default,
            useValuesFromIntent);
    boolean ordered =
        sharedPrefGetBoolean(
            R.string.pref_ordered_key,
            CallActivity.EXTRA_ORDERED,
            R.string.pref_ordered_default,
            useValuesFromIntent);
    boolean negotiated =
        sharedPrefGetBoolean(
            R.string.pref_negotiated_key,
            CallActivity.EXTRA_NEGOTIATED,
            R.string.pref_negotiated_default,
            useValuesFromIntent);
    int maxRetrMs =
        sharedPrefGetInteger(
            R.string.pref_max_retransmit_time_ms_key,
            CallActivity.EXTRA_MAX_RETRANSMITS_MS,
            R.string.pref_max_retransmit_time_ms_default,
            useValuesFromIntent);
    int maxRetr =
        sharedPrefGetInteger(
            R.string.pref_max_retransmits_key,
            CallActivity.EXTRA_MAX_RETRANSMITS,
            R.string.pref_max_retransmits_default,
            useValuesFromIntent);
    int id =
        sharedPrefGetInteger(
            R.string.pref_data_id_key,
            CallActivity.EXTRA_ID,
            R.string.pref_data_id_default,
            useValuesFromIntent);
    String protocol =
        sharedPrefGetString(
            R.string.pref_data_protocol_key,
            CallActivity.EXTRA_PROTOCOL,
            R.string.pref_data_protocol_default,
            useValuesFromIntent);

    // Start AppRTCMobile activity.
    Log.d(TAG, "Connecting to room " + roomId + " at URL " + roomUrl);
    if (validateUrl(roomUrl)) {
      Uri uri = Uri.parse(roomUrl);
      Intent intent = new Intent(this, CallActivity.class);
      intent.setData(uri);
      intent.putExtra(CallActivity.EXTRA_ROOMID, roomId);
      intent.putExtra(CallActivity.EXTRA_LOOPBACK, loopback);
      intent.putExtra(CallActivity.EXTRA_VIDEO_CALL, videoCallEnabled);
      intent.putExtra(CallActivity.EXTRA_SCREENCAPTURE, useScreencapture);
      intent.putExtra(CallActivity.EXTRA_CAMERA2, useCamera2);
      intent.putExtra(CallActivity.EXTRA_VIDEO_WIDTH, videoWidth);
      intent.putExtra(CallActivity.EXTRA_VIDEO_HEIGHT, videoHeight);
      intent.putExtra(CallActivity.EXTRA_VIDEO_FPS, cameraFps);
      intent.putExtra(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED, captureQualitySlider);
      intent.putExtra(CallActivity.EXTRA_VIDEO_BITRATE, videoStartBitrate);
      intent.putExtra(CallActivity.EXTRA_VIDEOCODEC, videoCodec);
      intent.putExtra(CallActivity.EXTRA_HWCODEC_ENABLED, hwCodec);
      intent.putExtra(CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture);
      intent.putExtra(CallActivity.EXTRA_FLEXFEC_ENABLED, flexfecEnabled);
      intent.putExtra(CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, noAudioProcessing);
      intent.putExtra(CallActivity.EXTRA_AECDUMP_ENABLED, aecDump);
      intent.putExtra(CallActivity.EXTRA_SAVE_INPUT_AUDIO_TO_FILE_ENABLED, saveInputAudioToFile);
      intent.putExtra(CallActivity.EXTRA_OPENSLES_ENABLED, useOpenSLES);
      intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC);
      intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC);
      intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS);
      intent.putExtra(CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF, disableWebRtcAGCAndHPF);
      intent.putExtra(CallActivity.EXTRA_AUDIO_BITRATE, audioStartBitrate);
      intent.putExtra(CallActivity.EXTRA_AUDIOCODEC, audioCodec);
      intent.putExtra(CallActivity.EXTRA_DISPLAY_HUD, displayHud);
      intent.putExtra(CallActivity.EXTRA_TRACING, tracing);
      intent.putExtra(CallActivity.EXTRA_ENABLE_RTCEVENTLOG, rtcEventLogEnabled);
      intent.putExtra(CallActivity.EXTRA_CMDLINE, commandLineRun);
      intent.putExtra(CallActivity.EXTRA_RUNTIME, runTimeMs);
      intent.putExtra(CallActivity.EXTRA_USE_LEGACY_AUDIO_DEVICE, useLegacyAudioDevice);

      intent.putExtra(CallActivity.EXTRA_DATA_CHANNEL_ENABLED, dataChannelEnabled);

      if (dataChannelEnabled) {
        intent.putExtra(CallActivity.EXTRA_ORDERED, ordered);
        intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS_MS, maxRetrMs);
        intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS, maxRetr);
        intent.putExtra(CallActivity.EXTRA_PROTOCOL, protocol);
        intent.putExtra(CallActivity.EXTRA_NEGOTIATED, negotiated);
        intent.putExtra(CallActivity.EXTRA_ID, id);
      }

      if (useValuesFromIntent) {
        if (getIntent().hasExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA)) {
          String videoFileAsCamera =
              getIntent().getStringExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA);
          intent.putExtra(CallActivity.EXTRA_VIDEO_FILE_AS_CAMERA, videoFileAsCamera);
        }

        if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE)) {
          String saveRemoteVideoToFile =
              getIntent().getStringExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE);
          intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE, saveRemoteVideoToFile);
        }

        if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH)) {
          int videoOutWidth =
              getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, 0);
          intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH, videoOutWidth);
        }

        if (getIntent().hasExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT)) {
          int videoOutHeight =
              getIntent().getIntExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, 0);
          intent.putExtra(CallActivity.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT, videoOutHeight);
        }
      }

      startActivityForResult(intent, CONNECTION_REQUEST);
    }
  }

  private boolean validateUrl(String url) {
    if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
      return true;
    }

    new AlertDialog.Builder(this)
        .setTitle(getText(R.string.invalid_url_title))
        .setMessage(getString(R.string.invalid_url_text, url))
        .setCancelable(false)
        .setNeutralButton(
            R.string.ok,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
              }
            })
        .create()
        .show();
    return false;
  }

  /**
   * Get a value from the shared preference or from the intent, if it does not exist the default is
   * used.
   */
  private String sharedPrefGetString(
      int attributeId, String intentName, int defaultId, boolean useFromIntent) {
    String defaultValue = getString(defaultId);
    if (useFromIntent) {
      String value = getIntent().getStringExtra(intentName);
      if (value != null) {
        return value;
      }
      return defaultValue;
    } else {
      String attributeName = getString(attributeId);
      return sharedPref.getString(attributeName, defaultValue);
    }
  }

  /**
   * Get a value from the shared preference or from the intent, if it does not exist the default is
   * used.
   */
  private boolean sharedPrefGetBoolean(
      int attributeId, String intentName, int defaultId, boolean useFromIntent) {
    boolean defaultValue = Boolean.parseBoolean(getString(defaultId));
    if (useFromIntent) {
      return getIntent().getBooleanExtra(intentName, defaultValue);
    } else {
      String attributeName = getString(attributeId);
      return sharedPref.getBoolean(attributeName, defaultValue);
    }
  }

  /**
   * Get a value from the shared preference or from the intent, if it does not exist the default is
   * used.
   */
  private int sharedPrefGetInteger(
      int attributeId, String intentName, int defaultId, boolean useFromIntent) {
    String defaultString = getString(defaultId);
    int defaultValue = Integer.parseInt(defaultString);
    if (useFromIntent) {
      return getIntent().getIntExtra(intentName, defaultValue);
    } else {
      String attributeName = getString(attributeId);
      String value = sharedPref.getString(attributeName, defaultString);
      try {
        return Integer.parseInt(value);
      } catch (NumberFormatException e) {
        Log.e(TAG, "Wrong setting for: " + attributeName + ":" + value);
        return defaultValue;
      }
    }
  }

  private String keyprefResolution;
  private String keyprefFps;
  private String keyprefVideoBitrateType;
  private String keyprefVideoBitrateValue;
  private String keyprefAudioBitrateType;
  private String keyprefAudioBitrateValue;
  private String keyprefRoomServerUrl;
  private String keyprefRoom;

  //  @Override
  //  public void clickImageChat(View view, int position, String nameUser, String urlPhotoUser,
  // String urlPhotoClick) {
  //    Intent intent = new Intent(this,FullScreenImageActivity.class);
  //    intent.putExtra("nameUser",nameUser);
  //    intent.putExtra("urlPhotoUser",urlPhotoUser);
  //    intent.putExtra("urlPhotoClick",urlPhotoClick);
  //    startActivity(intent);
  //  }
}
