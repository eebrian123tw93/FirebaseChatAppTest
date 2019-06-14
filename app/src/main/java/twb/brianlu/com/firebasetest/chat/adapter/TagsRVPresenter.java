package twb.brianlu.com.firebasetest.chat.adapter;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.core.BasePresenter;

public class TagsRVPresenter extends BasePresenter {
  private List<String> tags;

  public TagsRVPresenter() {
    tags = new ArrayList<>();
  }

  public void bindData(TagsRVAdapter.ViewHolder viewHolder, int position) {

    //        ChatMessage chatMessage = chatMessages.get(position);
    //        viewHolder.onSetUsername(chatMessage.getMessageUser());
    //        viewHolder.onSetMessage(chatMessage.getMessageText());
    //        viewHolder.onSetMessageTime(String.valueOf(DateFormat.format("MM/dd(HH:mm)",
    // chatMessage.getMessageTime())));
    viewHolder.onSetTag(tags.get(position));
  }

  public int getItemCount() {
    return tags.size();
  }

  public void addTags(List<String> messages) {
    this.tags.addAll(messages);
  }

  public void addTag(String tag) {
    this.tags.add(tag);
  }

  public void clear() {
    this.tags.clear();
  }
}
