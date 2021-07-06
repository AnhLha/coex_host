package com.upit.coex.host.screen.addco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.upit.coex.host.R;
import com.upit.coex.host.model.data.co.Photo;
import com.upit.coex.host.service.logger.L;

import java.util.ArrayList;

public class ListPhotoAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<Photo> mListPhoto;


    public ListPhotoAdapter(Context mContext, ArrayList<Photo> mListPhoto) {
        this.mContext = mContext;
        this.mListPhoto = mListPhoto;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        ViewGroup layout = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);
        ImageView imgPhoto = layout.findViewById(R.id.imageTool);

        Photo photo = mListPhoto.get(position);
        L.d("cin","load image in adapter"+photo.getUrl());
        Glide.with(mContext).load(photo.getUrl()).into(imgPhoto);

//        Glide.with(context).load(IMAGE_LINK_BASE + data.getPhoto().get(0)).into(imgMain);

        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return mListPhoto == null ? 0 : mListPhoto.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }
}