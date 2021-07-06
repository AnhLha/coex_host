package com.upit.coex.host.contract.base;

import androidx.lifecycle.MutableLiveData;


/**
 * Created by cin on 3/9/2020.
 */

public interface BaseInterfaceViewModel {
     boolean requestPermission(String[] permissions);

     void destroyView();

     MutableLiveData getMutableLiveData();

     //void setView(View t);
}
