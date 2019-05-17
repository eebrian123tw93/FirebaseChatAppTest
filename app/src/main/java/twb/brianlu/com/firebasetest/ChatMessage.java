package twb.brianlu.com.firebasetest;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {

  private String messageText;
  private String messageUser;
  private long messageTime;

  public ChatMessage(String messageText, String messageUser) {
    this.messageText = messageText;
    this.messageUser = messageUser;

    // Initialize to current time
    messageTime = new Date().getTime();
  }
}
