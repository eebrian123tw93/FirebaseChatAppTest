package twb.brianlu.com.firebasetest.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import twb.brianlu.com.firebasetest.R;

public class TagsRVAdapter extends RecyclerView.Adapter<TagsRVAdapter.ViewHolder> {


    private Context context;
    private TagsRVPresenter presenter;

    public TagsRVAdapter(Context context) {
        this.context = context;
        this.presenter = new TagsRVPresenter();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = null;
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_tag, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        presenter.bindData(viewHolder, i);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    public void addTags(List<String> tags) {
        presenter.addTags(tags);
        notifyDataSetChanged();
    }

    public void addTag(String tag) {
        presenter.addTag(tag);
        notifyDataSetChanged();
    }

    public void clear() {
        presenter.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements TagsVHView {

        private TextView tagTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagTextView = itemView.findViewById(R.id.tag_text);

        }

        @Override
        public void onSetTag(String tag) {
            tagTextView.setText(tag);
        }
    }

}
