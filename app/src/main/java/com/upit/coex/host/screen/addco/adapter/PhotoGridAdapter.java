package com.upit.coex.host.screen.addco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.upit.coex.host.R;
import com.upit.coex.host.model.data.co.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoGridAdapter extends BaseAdapter {
    List<Photo> mList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;

    public PhotoGridAdapter(Context context, List<Photo> mList) {
        this.mList = mList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageViewCu);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Photo mPhoto = mList.get(position);
        if (mPhoto.getId() == 0){
          //  Log.d("ListPic1111", "0");
//            CoexRunnable.runFunctionSingle(()->{return null;}).subscribe(new SingleObserver() {
//                @Override
//                public void onSubscribe(Disposable d) {
//
//                }
//
//                @Override
//                public void onSuccess(Object value) {
//                    Glide.with(context).load(mPhoto.getUrl()).into(holder.imageView);
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//            });
//            CoexExecutor.executeThenPostOnMainThread(()->{ Glide.with(context).load(mPhoto.getUrl()).into(holder.imageView);});
            Glide.with(context).load(mPhoto.getUrl()).into(holder.imageView);
        }else {
//            Log.d("ListPic1111", "1");
            Glide.with(context).load(mPhoto.getId()).into(holder.imageView);
        }

        return convertView;

    }

    class ViewHolder{
        ImageView imageView;
    }
}