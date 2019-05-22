package twb.brianlu.com.firebasetest.rooms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.model.Room;

interface DataChanged {
    void onDataChanged();
}

public class RoomsRVAdapter extends RecyclerView.Adapter<RoomsRVAdapter.ViewHolder> implements DataChanged {

    private Context context;
    private RoomsRVPresenter presenter;

    public RoomsRVAdapter(Context context) {
        this.context = context;
        presenter = new RoomsRVPresenter(this);
    }

    @Override
    public void onDataChanged() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_room2, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        presenter.onBindViewHolder(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }


    public void addRoom(Room room) {
        presenter.addRoom(room);
        notifyDataSetChanged();
    }

    public void removeRoom(Room room) {
        presenter.removeRoom(room);
        notifyDataSetChanged();
    }

    public void clearAll() {
        presenter.clearAll();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RoomsVHView {

        private TextView timeTextView;
        private TextView nameTextView;
        private TextView messageTextView;
        private CardView cardView;
        private SwipeLayout swipeLayout;
        private ImageView deleteImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time_textView);
            nameTextView = itemView.findViewById(R.id.roomName_textView);
            messageTextView = itemView.findViewById(R.id.message_textView);
            cardView = itemView.findViewById(R.id.room_cardView);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            deleteImageView = itemView.findViewById(R.id.delete_imageView);
            //set show mode.
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

            //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, itemView.findViewById(R.id.bottom_wrapper));
        }

        @Override
        public void onSetTime(String time) {
            timeTextView.setText(time);
        }

        @Override
        public void onSetName(String name) {
            nameTextView.setText(name);
        }

        @Override
        public void onSetMessage(String message) {
            messageTextView.setText(message);
        }

        @Override
        public void onSetClickListener(View.OnClickListener listener) {
            cardView.setOnClickListener(listener);
        }

        @Override
        public void onSetLongClickListener(View.OnLongClickListener listener) {
            cardView.setOnLongClickListener(listener);
        }

        @Override
        public void onSetSwipeListener(SwipeLayout.SwipeListener listener) {
            swipeLayout.addSwipeListener(listener);
        }

        @Override
        public void onSetDeleteButton(View.OnClickListener listener) {
            deleteImageView.setOnClickListener(listener);
        }
    }

}
