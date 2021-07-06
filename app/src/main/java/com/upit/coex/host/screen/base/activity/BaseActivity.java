package com.upit.coex.host.screen.base.activity;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.host.screen.splash.activity.SplashActivity;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;
import com.upit.coex.host.viewmodel.splash.SplashActivityViewModel;

import static com.upit.coex.host.constants.splash.SplashConstant.REQUEST_PERMISSION;

/**
 * Created by cin on 3/9/2020.
 */

public abstract class BaseActivity<T extends BaseActivityViewModel> extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected T mViewModal;
    private Dialog mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        installDialog();
        mViewModal = (T) ViewModelProviders.of(this).get(BaseActivityViewModel.class);


    }

    private void installDialog() {
//        mDialog = new Dialog(this);
//        mDialog.setContentView(R.layout.load_dialog);
//        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
//        TextView text = (TextView) dialog.findViewById(R.id.text);
//        text.setText("Android custom dialog example!");
//        ImageView image = (ImageView) dialog.findViewById(R.id.image);
//        image.setImageResource(R.drawable.ic_launcher);
//
//        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//        // if button is clicked, close the custom dialog
//        dialogButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

//        dialog.show();
//    }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mViewModal.getLiveDataDialog().observe(this, object -> {
//
//        });
        mViewModal.getPermissions().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                ActivityCompat.requestPermissions(BaseActivity.this, (String[]) o,
                        REQUEST_PERMISSION);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (grantResults.length > 1) {
                    if(this instanceof  SplashActivity){
                        Log.d("cin", "sdfnjkasndkln");
                        ((SplashActivityViewModel) mViewModal).checkToken();
                    }
                    for(int result : grantResults){
                        if(result == PackageManager.PERMISSION_DENIED){
                            L.d(TAG, "Permission denied!");
                            CoexToast.makeToast(this, Toast.LENGTH_LONG, "Permission denied!");
                            return;
                        }
                    }
                    L.d(TAG, "Permission granted!");

                }
                else {
                    L.d(TAG, "Permission denied!");
                    CoexToast.makeToast(this, Toast.LENGTH_LONG, "Permission denied!");
                }
                break;
            }
        }
    }
}
