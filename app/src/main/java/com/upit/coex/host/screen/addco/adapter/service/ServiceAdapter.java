package com.upit.coex.host.screen.addco.adapter.service;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.host.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>  {

    private List<Service> list;
    private Activity activity;
//    public ItemClickListener itemClickListener;

    public ServiceAdapter(List<Service> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = list.get(position);
        holder.iconService.setImageResource(service.getId());
        holder.txtService.setText(service.getDetail());
    }


    @Override
    public int getItemCount() {
//        L.d("hehe","size",list == null ? 0+"" : list.getData().size()+"");
        return list == null ? 0 : list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtService;
        ImageView iconService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtService = itemView.findViewById(R.id.txtIcon);
            iconService = itemView.findViewById(R.id.iconService);
        }
    }
}
