package twb.brianlu.com.firebasetest.model.fcm;

import java.io.Serializable;

import lombok.Data;

@Data
public class Notification implements Serializable {
  private String title;
  private String body;
  private String roomId;
}
