package twb.brianlu.com.firebasetest.model.fcm;

import java.io.Serializable;

@lombok.Data
public class Data implements Serializable {
  private Notification notification;
  private WebrtcCall webrtcCall;
}
