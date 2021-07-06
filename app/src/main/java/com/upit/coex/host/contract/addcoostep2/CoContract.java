package com.upit.coex.host.contract.addcoostep2;

import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;
import com.upit.coex.host.model.request.co.CoRequest;
import com.upit.coex.host.model.request.co.RoomRequest;
import com.upit.coex.host.screen.base.activity.BaseActivity;

import java.util.List;

public class CoContract {
    public interface CoInterfaceViewModel extends BaseInterfaceViewModel{

        void setView (BaseActivity activity);

        void doCoo();

        void doStep2(String name, String add, String phone, List<Double> address, String about, List<String> list);

        void doStep3(Boolean cw, Boolean cd, Boolean cp, Boolean ca, Boolean cc, List<String> other, List<RoomRequest> listRoom);

        void createCo(CoRequest coRequest);

        void addRoom(String name, String about, String price, String maxPer);

        void dataCo();

//        void initLiveData();



    }

    public interface CoInterfaceView extends BaseInterfaceView{
        void isEmpty();

        void isNotEmpty();
    }
}
