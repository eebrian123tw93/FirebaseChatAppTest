package twb.brianlu.com.firebasetest.profile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import twb.brianlu.com.firebasetest.R;

public class TagsAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<String> mDataList;

    public TagsAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
        mDataList.add("設定");
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
    public int getItemViewType(int position) {
        if (mDataList.size() - 1 == position) {
            return SETTING_TYPE;
        } else {
            return TAG_TYPE;
        }

    }

    private static int SETTING_TYPE = 0;
    private static int TAG_TYPE = 1;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        int type = getItemViewType(position);
        if (type == SETTING_TYPE) {
            @SuppressLint("ViewHolder") View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag_setting, null);
            ImageButton imageButton = view.findViewById(R.id.add_tag_button);
            imageButton.setClickable(false);
//            imageButton.getLayoutParams().height= (int) new Button(mContext).getTextSize();
//            imageButton.getLayoutParams().width=(int) new Button(mContext).getTextSize();
            return view;
        } else {
            @SuppressLint("ViewHolder") View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);
            TextView textView = view.findViewById(R.id.tag_text);
            String tag = mDataList.get(position);
            textView.setText(tag);

            return view;
        }
    }

    public void addTag(String tag) {
        mDataList.add(mDataList.size()-1,tag);
        notifyDataSetChanged();
    }

    public void addTags(List<String> tags) {
        for (String tag : tags) {
            mDataList.add(mDataList.size()-1,tag);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        mDataList.clear();
        mDataList.add("設定");
        notifyDataSetChanged();
    }


}
