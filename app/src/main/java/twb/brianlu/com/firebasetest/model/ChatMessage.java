package twb.brianlu.com.firebasetest.model;

import java.util.Date;

import lombok.Data;

@Data
public class ChatMessage {

  private String messageText;
  private String userUid;
  private String messageUser;
  private long messageTime;

  public ChatMessage(String messageText, String messageUser, String userUid) {
    this.messageText = messageText;
    this.messageUser = messageUser;
    messageTime = new Date().getTime();
    this.userUid = userUid;
  }

  public ChatMessage() {}
}
