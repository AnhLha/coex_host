package com.upit.coex.host.screen.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.upit.coex.host.R;

public class DialogConfirm {
    Activity activity;
    AlertDialog dialog;

    String title, message;
    public DialogConfirm(Activity activity, String title, String mes) {
        this.title = title;
        this.message = mes;
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.confirm_change, null);
        builder.setView(view);
        builder.setCancelable(false);
        //
        Button btnCancel, btnAccepted;
        TextView txtTitle, txtMessage;

        txtMessage = view.findViewById(R.id.txtMessage);
        txtTitle = view.findViewById(R.id.txtTitle);
        btnAccepted = view.findViewById(R.id.btnAccepted);
        btnCancel = view.findViewById(R.id.btnCancel);

        txtMessage.setText(message);
        txtTitle.setText(title);

        btnAccepted.setOnClickListener(v -> {
            activity.finish();
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog = builder.create();
        dialog.show();
    }

}