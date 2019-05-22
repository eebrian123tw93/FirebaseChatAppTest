package twb.brianlu.com.firebasetest.rooms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import twb.brianlu.com.firebasetest.R;


public class RoomsFragment extends Fragment implements RoomsView {
    private RecyclerView roomsRecyclerView;
    private RoomsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rooms, container, false);
        roomsRecyclerView = v.findViewById(R.id.rooms_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        roomsRecyclerView.setLayoutManager(layoutManager);


        presenter = new RoomsPresenter(this);
        presenter.setRoomsRVAdapter();


        return v;
    }

    @Override
    public void onSetMessage(String message, int type) {

    }

    @Override
    public void onSetRoomsAdapter(RecyclerView.Adapter adapter) {
        roomsRecyclerView.setAdapter(adapter);
    }
}
