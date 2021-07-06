package com.upit.coex.host.screen.mainscreen.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.upit.coex.host.R;
import com.upit.coex.host.screen.mainscreen.fragment.HomeFragment;
import com.upit.coex.host.screen.mainscreen.fragment.QRCodeFragment;
import com.upit.coex.host.screen.mainscreen.fragment.SettingFragment;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout mFlChoose1, mFlChoose2, mFlChoose3;
    private ImageView mImgChoose1, mImgChoose2, mImgChoose3;
    private int mItemIndex = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mFlChoose1 = findViewById(R.id.fl_choose_1);
        mFlChoose2 = findViewById(R.id.fl_choose_2);
        mFlChoose3 = findViewById(R.id.fl_choose_3);
        mImgChoose1 = findViewById(R.id.img_choose_1);
        mImgChoose2 = findViewById(R.id.img_choose_2);
        mImgChoose3 = findViewById(R.id.img_choose_3);
        mFlChoose1.setOnClickListener(this);
        mFlChoose2.setOnClickListener(this);
        mFlChoose3.setOnClickListener(this);
        chooseBottomNavigationItem(0);

    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void chooseBottomNavigationItem(int itemIndex) {
        if (mItemIndex == 0) {
            mImgChoose1.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.colorBlack, null)));

        } else if (mItemIndex == 1) {
            mImgChoose2.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.colorBlack, null)));

        } else {
            mImgChoose3.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.colorBlack, null)));

        }

        if (itemIndex == 0) {
            mImgChoose1.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.colorBtnNormal, null)));
            openFragment(HomeFragment.newInstance("", ""));
        } else if (itemIndex == 1) {
            mImgChoose2.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.colorBtnNormal, null)));
            openFragment(QRCodeFragment.newInstance("", ""));

        } else {
            mImgChoose3.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.colorBtnNormal, null)));
            openFragment(SettingFragment.newInstance("", ""));
        }

        mItemIndex = itemIndex;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_choose_1:
                chooseBottomNavigationItem(0);
                break;
            case R.id.fl_choose_2:
                chooseBottomNavigationItem(1);
                break;
            default:
                chooseBottomNavigationItem(2);

        }
    }

}
