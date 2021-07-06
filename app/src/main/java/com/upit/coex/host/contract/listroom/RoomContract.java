package com.upit.coex.host.contract.listroom;

import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

public class RoomContract {
    public interface RoomInterfaceViewModel extends BaseInterfaceViewModel {

        void addRoom(String name, String about, String price, String maxPer);

    }

    public interface RoomInterfaceView extends BaseInterfaceView {
        void isEmpty();

        void isNotEmpty();
    }
}
