package com.upit.coex.host.viewmodel.searchaddress;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.contract.searchaddress.SearchAddressContract;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;

public class SearchAddressActivityViewModel extends BaseActivityViewModel implements SearchAddressContract.SearchAddressInterfaceViewModel{
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static final String TAG = "SearchAddressActivityViewModel";

    @Override
    public void moveCamera(double lat, double lng) {

    }

    @Override
    public void googleApiClientStart() {

    }

    @Override
    public void googleApiClientStop() {

    }

    @Override
    public void removeAllMarker() {

    }

    @Override
    public void checkToken() {

    }

    @Override
    public void removeDisposal() {

    }

    @Override
    public boolean requestPermission(String[] permissions) {
        if (!isPermission(permissions)){
            mPermissions.postValue(permissions);
            return false;
        }
        return true;
    }

    @Override
    public void destroyView() {

    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return null;
    }
}
