package twb.brianlu.com.firebasetest.model;

import java.util.List;

import lombok.Data;

@Data
public class Room {
    private String roomId;
    private List<ChatMessage>chatMessages;
}