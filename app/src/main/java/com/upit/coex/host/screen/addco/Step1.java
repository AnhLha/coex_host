package com.upit.coex.host.screen.addco;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.addcoostep2.CoContract;
import com.upit.coex.host.model.data.co.Photo;
import com.upit.coex.host.model.request.co.CoRequest;
import com.upit.coex.host.screen.addco.adapter.PhotoGridAdapter;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.dialog.DialogLoad;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.co.CoActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class Step1 extends BaseActivity<CoActivityViewModel> implements CoContract.CoInterfaceView {
    DialogLoad dialogLoading;

    public static final int GALLERY_REQUEST = 100;

    public static final int MAP_REQUEST = 101;

    private static final int START_SEARCH_ACTIVITY_FOR_RESULT_CODE = 2;
    EditText edtNameSpace;
    EditText edtAddress;
    EditText edtAbout;
    EditText edtPhone;
    Button btnNextTep2;
    ImageButton imgSelectAddress;
    GridView gridView;
    ArrayList<String> listURI = new ArrayList<>();
    ArrayList<Photo> mList = new ArrayList<>();
    List<Double> mLocation = new ArrayList<>();
    String mAddress;
    PhotoGridAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_coo_step_1);
        mViewModal = ViewModelProviders.of(this).get(CoActivityViewModel.class);
        //
        //Log.d("bao.nt", "7");

        mList.add(new Photo(R.drawable.plus_img));
        Log.d("ListPic1111", mList.size() + "");
        bindView();
        addPhoto();


        checkPermission();

        mViewModal.getmLiveSuccess().observe(this, new Observer<Pair>() {
            @Override
            public void onChanged(Pair s) {
                Log.d("anhtu", "create step 1------------------");
                Log.d("ListPicture21", s.first.toString() + "");
                if (s.first.equals(CommonConstants.STEP_1)) {
                    Log.d("ListPicture22", s.first.toString() + "");

//                    CoexToast.makeToast(Step1.this, Toast.LENGTH_LONG, s.second.toString());
                    dialogLoading.dissLoadingDialog();
                    Intent intent = new Intent(Step1.this, Step2.class);
                    CoRequest co = new CoRequest(edtNameSpace.getText().toString(), edtAbout.getText().toString(), edtPhone.getText().toString(), listURI, mAddress, mLocation);
                    intent.putExtra("co", co);
                    Log.d("ListPicture1", mList.size() + " " + listURI.size());
                    startActivity(intent);
                    //finish();
                } else {
                    Log.d("ListPicture2222", s.first.toString() + "");

                }
            }
        });

        //
        mViewModal.getmLiveFail().observe(this, new Observer<Pair>() {
            @Override
            public void onChanged(Pair pair) {
                if (pair.first.equals(CommonConstants.STEP_1)) {
                    dialogLoading.dissLoadingDialog();
                    CoexToast.makeToast(Step1.this, Toast.LENGTH_LONG, pair.second.toString());
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        L.d("bao.nt", "new intent step 1");
    }

    @Override
    protected void onStart() {
        super.onStart();

        L.d("bao.nt", "on start step 1");

    }

    @Override
    public void isEmpty() {
//        CoexToast.makeToast(this, Toast.LENGTH_LONG, "Dien day du thong tin");
    }

    @Override
    public void isNotEmpty() {

    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        dialogLoading = new DialogLoad(this);
        edtNameSpace = findViewById(R.id.edtNameSpace);
        edtAddress = findViewById(R.id.edtNewAddressCoo);
        edtAbout = findViewById(R.id.edtAboutSpaceNew);
        edtPhone = findViewById(R.id.edtPhone);
        btnNextTep2 = findViewById(R.id.btnStep1);
        gridView = findViewById(R.id.gridView);
        imgSelectAddress = findViewById(R.id.iconSelectAddress);

        // forcus
        edtNameSpace.setFocusable(true);
        edtNameSpace.requestFocus();

        imgSelectAddress.setOnClickListener(view -> {
            //open search screen
//            Intent searchIntent = new Intent(this, SearchAddressActivity.class);
//            startActivityForResult(searchIntent, START_SEARCH_ACTIVITY_FOR_RESULT_CODE);
            doMap();
        });

        edtAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.d("bao.nt", "=======================" + hasFocus);
                    Log.d("bao.nt", "size " + mLocation.size());
                    if (mLocation.size() == 0) {
                        dialogLoading.startLoadingDialog();
                        doMap();
//                    dialogLoading.dissLoadingDialog();
                    }
                }

            }
        });

        //
        btnNextTep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoading.startLoadingDialog();
                mViewModal.doStep2(edtNameSpace.getText().toString(), mAddress, edtPhone.getText().toString(), mLocation, edtAbout.getText().toString(), listURI);
            }
        });
    }

    public void addPhoto() {
        adapter = new PhotoGridAdapter(this, mList);
        Log.d("ListPic11112", mList.size() + "");
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == (mList.size() - 1)) {
                    addPhotoGalley();
                    Log.d("ListPicture", "a" + mList.size());
                }
            }
        });

    }

    public void addPhotoGalley() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        Log.d("ListPicture", mList.size() + "");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {
            final Uri imgUri = data.getData();
            mList.add(0, new Photo(imgUri.toString()));
            Log.d("ListPicture", mList.size() + "");
            adapter.notifyDataSetChanged();
            listURI.add(imgUri.toString());
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == START_SEARCH_ACTIVITY_FOR_RESULT_CODE) {
                dialogLoading.dissLoadingDialog();
                //Place place = (Place) data.getSerializableExtra("place");
                if (mLocation.size() == 0) {
                    String name = data.getStringExtra("nameAdd");
                    String address = data.getStringExtra("addressAdd");
                    Double l1 = data.getDoubleExtra("l1", 0.0);
                    Double l2 = data.getDoubleExtra("l2", 0.0);
                    edtAddress.setText(name);
                    mAddress = address;
                    mLocation.add(l2);
                    mLocation.add(l1);
                } else {
                    String name = data.getStringExtra("nameAdd");
                    String address = data.getStringExtra("addressAdd");
                    Double l1 = data.getDoubleExtra("l1", 0.0);
                    Double l2 = data.getDoubleExtra("l2", 0.0);
                    edtAddress.setText(name);
                    mAddress = address;
                    mLocation.remove(0);
                    mLocation.remove(1);
                    mLocation.add(l2);
                    mLocation.add(l1);
                }
            }
        }else {
            dialogLoading.dissLoadingDialog();
        }
    }

    public void checkPermission() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        mViewModal.requestPermission(permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        L.d(TAG, TAG_ACIVITY, "onRequestPermissionsResult!");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Toast.makeText(this, "back 1", Toast.LENGTH_SHORT).show();

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
            Log.d("bao.nt", "------------------------------");
            CoexSharedPreference.getInstance().put(CommonConstants.TOKEN, "");
            this.finish();

        }
        return super.onKeyDown(keyCode, event);
    }


    public void doMap() {
        Intent searchIntent = new Intent(this, SearchAddressActivity.class);
        startActivityForResult(searchIntent, START_SEARCH_ACTIVITY_FOR_RESULT_CODE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("bao.nt", "des step 1");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("anhtu", "pau step 1");
    }
}
