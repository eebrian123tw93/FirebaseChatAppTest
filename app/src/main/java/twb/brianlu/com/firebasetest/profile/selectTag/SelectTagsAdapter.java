package twb.brianlu.com.firebasetest.profile.selectTag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.R;

public class SelectTagsAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<String> mDataList;

    public SelectTagsAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
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

        @SuppressLint("ViewHolder") View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_tag, null);

        TextView textView = view.findViewById(R.id.tags_text);
        String tag = mDataList.get(position);
        textView.setText(tag);

        return view;
    }

    public void addTag(String tag) {
        mDataList.add(tag);
        notifyDataSetChanged();
    }

    public void addTags(List<String> tags) {
        mDataList.addAll(tags);
        notifyDataSetChanged();
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }


}
