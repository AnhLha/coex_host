package com.upit.coex.host.viewmodel.co;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.CoexApplication;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.addcoostep2.CoContract;
import com.upit.coex.host.model.data.co.CoData;
import com.upit.coex.host.model.data.co.ServiceData;
import com.upit.coex.host.model.data.error.BaseDataError;
import com.upit.coex.host.model.remote.co.CoAPI;
import com.upit.coex.host.model.request.co.CoRequest;
import com.upit.coex.host.model.request.co.RoomRequest;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.compositedisposal.CoexCommonCompositeDisposal;
import com.upit.coex.host.service.helper.CoexHelper;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import static com.upit.coex.host.constants.login.LoginConstant.listDiposal;
import static com.upit.coex.host.service.helper.CoexHelper.getResizedBitmap;
import static com.upit.coex.host.service.helper.CoexHelper.saveBitmap;

public class CoActivityViewModel extends BaseActivityViewModel<CoData, Pair, Pair> implements CoContract.CoInterfaceViewModel {
    //    String token = CoexSharedPreference.getInstance().get("token", String.class);
    String token = "";
    CoData co = new CoData();
    BaseActivity mView;
    //    public MutableLiveData mLiveStep3Success, mLiveStep3Failed , mLiveAddRoomSuccess,  mLiveAddRoomFalied;
    private static final String TAG = "CoActivityViewModel";

//    public CoActivityViewModel(){
//        super();
//        this.initLiveData();
//    }

    @Override
    public boolean requestPermission(String[] permissions) {
        L.d(TAG, "reqestpermission");
        if (!isPermission(permissions)) {
            L.d(TAG, "reqestpermission false");

            mPermissions.postValue(permissions);
            return false;
        }
        L.d(TAG, "reqestpermission true");

        return true;
    }


    @Override
    public void destroyView() {
        for (String iem :
                listDiposal) {
            CoexCommonCompositeDisposal.getInstance().removeDisposal(iem);
        }
    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return mLive;
    }

    @Override
    public void setView(BaseActivity activity) {
        this.mView = activity;
    }

    @Override
    public void doCoo() {
    }

    @Override
    public void doStep2(String name, String address, String phone, List<Double> location, String about, List<String> list) {
        if (!"".equals(name) && !"".equals(address) && !"".equals(about) && !"".equals(phone) && list.size() != 0 && location.size() != 0) {
            Log.d(TAG, "name|address : " + name + "|" + address);
            mLiveSuccess.postValue(new Pair<String, String>(CommonConstants.STEP_1, "Step 1 success"));
            L.d(TAG, name, address);
//            /co = new CoData(name, about, list, address, location);
            L.d(TAG, co.getName(), co.getAddress());
//            mLive.postValue(co);
        } else if ("".equals(name)) {
            mLiveFail.setValue(new Pair<String, String>(CommonConstants.STEP_1, "Please enter name space!"));
        } else if ("".equals(address) || location.size() == 0) {
            mLiveFail.setValue(new Pair<String, String>(CommonConstants.STEP_1, "Please enter address!"));
        } else if ("".equals(about)) {
            mLiveFail.setValue(new Pair<String, String>(CommonConstants.STEP_1, "Please enter about space!"));
        } else if ("".equals(phone)) {
            mLiveFail.setValue(new Pair<String, String>(CommonConstants.STEP_1, "Please enter phone space!"));
        } else if (list.size() == 0) {
            mLiveFail.setValue(new Pair<String, String>(CommonConstants.STEP_1, "Please select photo!"));
        }

    }

    @Override
    public void doStep3(Boolean cw, Boolean cd, Boolean cp, Boolean ca, Boolean cc, List<String> other, List<RoomRequest> listRoom) {
        Log.d(TAG, cw + "");
        ServiceData mService = new ServiceData(cw, cc, cd, cp, ca, other);
        Log.d(TAG, mService.toString() + "");
        mLiveSuccess.setValue(new Pair<String, String>(CommonConstants.STEP_2, "Step 2 success"));
        L.d(TAG, co.getAddress() + "|" + co.getServiceData().toString());
    }

    int index = 0;

    @Override
    public void createCo(CoRequest coRequest) {
        L.d("cin", "multi part get image " + coRequest.getmPhoto().get(index));
        if (isPermission(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            L.d("cin", "anh tu1");

            //RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"),file);

            MultipartBody.Builder photos = new MultipartBody.Builder();

            //MultipartBody.Part photos = null;

            photos.setType(MultipartBody.FORM);

            String sCoworing = CoexApplication.self().getGSon().toJson(coRequest.getmCo());

            //sCoworing = "{\"about\":\"ehejej\",\"address\":\"wywhwh\",\"location\":[0.45,2.32],\"rooms\":[],\"phone\":\"01233213\",\"name\":\"ywhyw\",\"service\":{\"airConditioning\":false,\"conversionCall\":false,\"drink\":true,\"other\":[],\"printer\":false,\"wifi\":true}}";

            RequestBody coworking = RequestBody.create(MediaType.parse("multipart/form-data"), sCoworing);
            photos.addFormDataPart("coworking", sCoworing);

//            RequestBody coworking = RequestBody.create(MediaType.parse("text/plain"),
//                    sCoworing);

            List<RequestBody> photoss = new ArrayList<>();

            RequestBody[] photosss = new RequestBody[coRequest.getmPhoto().size()];

            //MultipartBody.Part[] photos = new MultipartBody.Part[coRequest.getmPhoto().size() - 1];
            // for (index = 0; index < coRequest.getmPhoto().size() - 1; index++) {
            L.d("cin", "multi part get image " + coRequest.getmPhoto().get(index));

            for (index = 0; index < coRequest.getmPhoto().size(); index++) {

                File file = new File(CoexHelper.getRealPathFromURI(mView, Uri.parse(coRequest.getmPhoto().get(index))));
                L.d("cin", "absolute path " + file.getAbsolutePath());

                //nen anh
                Bitmap scaledBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                if (scaledBitmap == null) {
                    Log.d("cin", "aaaaaaaaaaaaa");
                }
                L.d("cin", "absolute path1 " + file.getAbsolutePath());
                scaledBitmap = getResizedBitmap(scaledBitmap, 3000);
                L.d("cin", "absolute path2 " + file.getAbsolutePath());
                File newFile = null;
                try {
                    L.d("cin", "absolute path3 " + file.getAbsolutePath());
                    newFile = saveBitmap(scaledBitmap);
                    L.d("cin", "absolute path4 " + file.getAbsolutePath());
                } catch (IOException e) {
                    L.d("cin", "absolute path5 " + file.getAbsolutePath());
                    e.printStackTrace();
                }
//                    /storage/emulated/0/DCIM/Screenshots/Screenshot_20200401-131049_Uplife.jpg
//                    /storage/emulated/0/DCIM/Screenshots/Screenshot_20200403-101900_Coex Host.jpg
//                    /storage/emulated/0/DCIM/Camera/IMG_20160321_044346.jpg
//                    load image in adaptercontent://media/external/images/media/84
//                    multi part get image content://media/external/images/media/84
//                    multi part get image content://media/external/images/media/77
//                    2020-04-03 13:17:08.376 28836-28836/com.example.coex_host D/cin:  absolute path /storage/emulated/0/DCIM/Camera/20200401_133602.jpg

                //********************************************
                L.d("cin", "absolute path 5" + file.getAbsolutePath());

                RequestBody photo = RequestBody.create(MediaType.parse("image/*"),
                        newFile);

                L.d("cin", "json " + sCoworing);
                photoss.add(photo);
                photosss[index] = photo;
                photos.addFormDataPart("photo", file.getName(), photo);

            }


            //RequestBody.create(MediaType.parse("text/plain"),sCoworing);

            String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
            L.d("cin", "json " + sCoworing + "\n----------" + photosss.toString());

            mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(CoAPI.class)
                    .create(CommonConstants.PREFIX_AUTHOR + token,
                            coworking,
                            photosss
                    )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(response -> {
                        mLiveSuccess.setValue(new Pair<String, String>(CommonConstants.CREATE_CO, "Create coo success"));
//                        mLive.postValue(response.getData());
                        Log.d("cin", response.getMessage() + response.getData().getName());
                    }, throwable -> {
                        BaseDataError error = new BaseDataError(throwable);
                        if (error.getmMessage() != null) {
                            mLiveFail.setValue(new Pair<String, String>(CommonConstants.CREATE_CO, error.getmMessage()));
                            Log.d("cin", error.getmMessage());
                        } else {
                            mLiveFail.setValue(new Pair<String, String>(CommonConstants.CREATE_CO, throwable.getMessage()));
                        }
                        Log.d("cin error", throwable.getMessage());
                        //truong hop loi mang, server die, timeout, url khong ton tai
                    }));
        }
    }

    @Override
    public void addRoom(String name, String about, String price, String maxPer) {
        if (!"".equals(name) && !"".equals(about) && !"".equals(price) && !"".equals(maxPer)) {
//                RoomRequest room = new RoomRequest(name, about, Integer.valueOf(price), Integer.valueOf(maxPer));
            mLiveSuccess.setValue(new Pair<String, String>(CommonConstants.ADD_ROOM, "Add room success"));

        } else if ("".equals(name)) {
            mLiveFail.setValue(new Pair<String, String>(CommonConstants.ADD_ROOM, "Please enter name!"));
        } else if ("".equals(about)) {
            mLiveFail.setValue(new Pair<String, String>(CommonConstants.ADD_ROOM, "Please enter about room!"));
        } else if ("".equals(price)) {
            mLiveFail.setValue(new Pair<String, String>(CommonConstants.ADD_ROOM, "Please enter price room!"));
        } else if ("".equals(maxPer)) {
            mLiveFail.setValue(new Pair<String, String>(CommonConstants.ADD_ROOM, "Please enter max person of room!"));
        }
    }

    @Override
    public void dataCo() {
        String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
        L.d("cin", token);
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(CoAPI.class)
                .doCoo("Bearer " + token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
//                    mLive.setValue(response.size());
                    Log.d("cin1", response.getData().size() + "");
                    if (response.getData().size() > 0) {
                        Log.d("cin2", response.getData().get(0).getName() + "");
                        mLive.setValue((CoData) response.getData().get(0));
                        Log.d("cin3", mLive.getValue().getName());
                    }

                }, throwable -> {
                    Log.d("cintttt", throwable.getMessage());
                }));
    }


}