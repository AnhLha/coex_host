package com.upit.coex.host.screen.addco.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.upit.coex.host.R;

public class DialogLoading {
    Activity activity;
    AlertDialog dialog;

    public DialogLoading(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_load, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public void dissLoadingDialog(){
        dialog.dismiss();
    }
}
