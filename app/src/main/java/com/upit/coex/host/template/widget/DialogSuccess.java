package com.upit.coex.host.template.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.upit.coex.host.R;

import java.util.concurrent.CopyOnWriteArrayList;

public class DialogSuccess extends Dialog implements View.OnClickListener {

    private TextView mTvTitle, mTvContent, mTvPositiveButton;

    private CopyOnWriteArrayList<Listener> mListener = new CopyOnWriteArrayList<>();

    public DialogSuccess(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_success_layout);
        mTvTitle = this.findViewById(R.id.tv_title);
        mTvContent = this.findViewById(R.id.tv_content);
        mTvPositiveButton= this.findViewById(R.id.tv_positive_button);

        this.mTvPositiveButton.setOnClickListener(this);
    }

    public void setTitle(String title){
        this.mTvTitle.setText(title);
    }

    public void setContent(String content){
        this.mTvContent.setText(content);
    }

    public void setTextPositiveButton(String textPositiveButton){
        this.mTvPositiveButton.setText(textPositiveButton);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_positive_button){

        }
    }

    public void addListener(Listener listener){
        this.mListener.add(listener);
    }

    public static interface Listener{
        void respondWhenClickPositiveButton();
    }
}
