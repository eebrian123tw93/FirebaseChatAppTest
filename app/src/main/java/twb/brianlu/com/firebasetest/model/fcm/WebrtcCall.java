package twb.brianlu.com.firebasetest.model.fcm;

import java.io.Serializable;

import lombok.Data;

@Data
public class WebrtcCall implements Serializable {
    private String roomId;
    private String selfUid;
    private String  displayName;
}
