package twb.brianlu.com.firebasetest.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatMessage {

  private String messageText;
  private String userUid;
  private String messageUser;
  private long messageTime;
  private FileModel fileModel;

  public ChatMessage() {}

  public ChatMessage(String messageText, String messageUser, String userUid) {
    this.messageText = messageText;
    this.messageUser = messageUser;
    messageTime = new Date().getTime();
    this.userUid = userUid;
    fileModel = null;
  }
}
