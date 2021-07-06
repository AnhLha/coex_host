package com.upit.coex.host.screen.addco.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.host.R;
import com.upit.coex.host.screen.addco.adapter.Interface.ItemClickListener;

import java.util.List;

public class ListOtherAdapter extends RecyclerView.Adapter<ListOtherAdapter.ViewHolder>   {

    private List<String> list;
    private Activity activity;

    public ListOtherAdapter(List<String> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_other_service, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String other = list.get(position);
        holder.txtOther.setText(other);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Log.d("cin", "1");
                if (position >= 0 ){
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
//        L.d("hehe","size",list == null ? 0+"" : list.getData().size()+"");
        return list == null ? 0 : list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtOther;
        ImageButton imgClose;
        private ItemClickListener itemClickListener;

        public ItemClickListener getItemClickListener() {
            return itemClickListener;
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOther = itemView.findViewById(R.id.otherCoffee);
            imgClose = itemView.findViewById(R.id.close_other_coffee);
            Log.d("cin", "3");
            imgClose.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("cin", "2");
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
