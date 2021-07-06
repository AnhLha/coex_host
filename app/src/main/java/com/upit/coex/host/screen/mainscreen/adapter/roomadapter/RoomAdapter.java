package com.upit.coex.host.screen.mainscreen.adapter.roomadapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.model.data.co.RoomData;
import com.upit.coex.host.screen.detailroom.activity.RoomDetailActivity;
import com.upit.coex.host.screen.mainscreen.Imainsreen.IItemClickListener;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>{

    private List<RoomData> list;
    Activity activity;

    public RoomAdapter(List<RoomData> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    public RoomAdapter(List<RoomData> mRoom) {
        this.list = mRoom;
    }

    @NonNull
    @Override
    public RoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_room, parent, false);

        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.RoomViewHolder holder, int position) {
        RoomData room = list.get(position);
        holder.txtMaxPer.setText("Max " + room.getMaxPerson() + " persons");
        String currency = String.format("%,d", room.getPrice());
        holder.txtPrice.setText(currency+" VND/person/hour");
        holder.txtAbout.setText(room.getAbout());
        holder.txtName.setText(room.getName());

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (position >= 0){

                    String idRoom = list.get(position).getId();


                    Log.d("cin", "onclick room adapter 2" + idRoom);
                    Intent intent = new Intent(activity, RoomDetailActivity.class);
                    intent.putExtra(CommonConstants.INTENT_DATA_ROOM, list.get(position));
                    activity.startActivity(intent);
                    Log.d("cin", "onclick room adapter 3" + idRoom);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
//        L.d("hehe","size",list == null ? 0+"" : list.getData().size()+"");
        return list == null ? 0 : list.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView txtName, txtAbout, txtPrice, txtMaxPer;
        ImageView icon;
        Button btnCheck;

        IItemClickListener itemClickListener;

        public IItemClickListener getItemClickListener() {
            return itemClickListener;
        }

        public void setItemClickListener(IItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtSharedRoom);
            txtAbout = itemView.findViewById(R.id.txtYouWill);
            txtPrice = itemView.findViewById(R.id.txtCost);
            txtMaxPer = itemView.findViewById(R.id.txtMaxPersonRoom);
            btnCheck = itemView.findViewById(R.id.btnCheckStatusOfRoom);

            // button click
            btnCheck.setOnClickListener(this);
//            Log.d("cin", "onclick room adapter1");
        }

        @Override
        public void onClick(View v) {
            Log.d("cin", "onclick room adapter");
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }


}
