package twb.brianlu.com.firebasetest.profile.selectTag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhl.library.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twb.brianlu.com.firebasetest.R;

public class SelectTagsAdapter extends BaseAdapter implements OnInitSelectedPosition {

  private final Context mContext;
  private final List<String> mDataList;
  private Map<String, Boolean> isSelectedTags;

  public SelectTagsAdapter(Context context) {
    this.mContext = context;
    mDataList = new ArrayList<>();
    isSelectedTags = new HashMap<>();
  }

  @Override
  public int getCount() {
    return mDataList.size();
  }

  @Override
  public Object getItem(int position) {
    return mDataList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    @SuppressLint("ViewHolder")
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_tag, null);

    TextView textView = view.findViewById(R.id.tag_text);

    String tag = mDataList.get(position);
    textView.setText(tag);

    return view;
  }

  public void addTag(String tag) {
    if (!mDataList.contains(tag)) mDataList.add(tag);
    isSelectedTags.put(tag, false);
    notifyDataSetChanged();
  }

  public void addTags(List<String> tags) {
    //        mDataList.addAll(tags);
    for (String tag : tags) if (!mDataList.contains(tag)) mDataList.add(tag);
    for (String tag : tags) isSelectedTags.put(tag, false);
    notifyDataSetChanged();
  }

  public void setPositionSelect(int position, boolean select) {
    //        isSelectedTags.put(mDataList.get(position), select);
    setTagSelect(mDataList.get(position), select);
    notifyDataSetChanged();
  }

  public String getTagWithPosition(int position) {
    return mDataList.get(position);
  }

  public void setTagSelect(String tag, boolean select) {
    isSelectedTags.put(tag, select);
    //        mDataList.remove(tag);
    //        mDataList.add(0, tag);
    //        notifyDataSetChanged();
  }

  public void clear() {
    mDataList.clear();
    isSelectedTags.clear();
    notifyDataSetChanged();
  }

  @Override
  public boolean isSelectedPosition(int position) {
    return isSelectedTags.get(mDataList.get(position));
  }
}
