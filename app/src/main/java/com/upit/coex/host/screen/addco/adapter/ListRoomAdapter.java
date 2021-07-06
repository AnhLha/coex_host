package com.upit.coex.host.screen.addco.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.host.model.request.co.RoomRequest;

import java.util.List;

import static com.upit.coex.host.R.*;

public class ListRoomAdapter extends RecyclerView.Adapter<ListRoomAdapter.RoomViewHolder>{

    private List<RoomRequest> list;
    private Activity activity;

    public ListRoomAdapter(Activity activity, List<RoomRequest> mRoom) {
        this.activity = activity;
        this.list = mRoom;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(layout.add_room, parent, false);

        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        RoomRequest room = list.get(position);

        String currency = String.format("%,d", room.getPrice());
        holder.txtMaxPer.setText("Max " + room.getMaxPerson() + " persons");
        holder.txtPrice.setText(currency+" VND/person/hour");
        holder.txtAbout.setText(room.getAbout());
        holder.txtName.setText(room.getName());

    }

    @Override
    public int getItemCount() {
//        L.d("hehe","size",list == null ? 0+"" : list.getData().size()+"");
        return list == null ? 0 : list.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAbout, txtPrice, txtMaxPer;
        ImageView icon;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(id.txtSharedRoom);
            txtAbout = itemView.findViewById(id.txtYouWill);
            txtPrice = itemView.findViewById(id.txtCost);
            txtMaxPer = itemView.findViewById(id.txtMaxPersonRoom);
        }
    }


}
