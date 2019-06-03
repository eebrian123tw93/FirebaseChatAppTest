package twb.brianlu.com.firebasetest.model.fcm;

import lombok.Data;

@Data
public class WebrtcCall {
    private String roomId;
    private String selfUid;
    private String  displayName;
}
