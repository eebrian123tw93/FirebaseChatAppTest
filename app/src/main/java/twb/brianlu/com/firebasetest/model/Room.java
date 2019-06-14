package twb.brianlu.com.firebasetest.model;

import java.util.List;

import lombok.Data;

@Data
public class Room {
  private String roomId;
  private String selfUId;
  private String oppositeDisplayName;
  private String oppositeUid;
  private List<String> oppositeTags;
  private ChatMessage lastMessage;
}
