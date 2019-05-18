package twb.brianlu.com.firebasetest.model;

import java.util.List;

import lombok.Data;

@Data
public class User {
    private String uid;
    private String email;
    private String displayName;
    private List<String> rooms;

}
