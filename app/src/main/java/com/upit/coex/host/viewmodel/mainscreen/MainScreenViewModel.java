package com.upit.coex.host.viewmodel.mainscreen;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.model.data.mainscreen.TransactionData;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;

public class MainScreenViewModel extends BaseActivityViewModel<String, String, String> {
    private  MutableLiveData<TransactionData> mRoomLiveData = new MutableLiveData();

    public MainScreenViewModel() {
    }

    public MainScreenViewModel(MutableLiveData<TransactionData> mRoomLiveData) {
        this.mRoomLiveData = mRoomLiveData;
    }

    public MutableLiveData<TransactionData> getRoomLiveData(){
        return mRoomLiveData;
    }

    public void scanQRCode(String string){

    }
}
