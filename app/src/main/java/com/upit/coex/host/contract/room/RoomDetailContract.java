package com.upit.coex.host.contract.room;

import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

public class RoomDetailContract {
    public interface RoomDetailInterfaceViewModel extends BaseInterfaceViewModel {
        void getDates(String id);

        void getUsers(String id, Long date);

        void editRoom(String id, String name, String about, String price, String maxPer);

    }

    public interface RoomDetailInterfaceView extends BaseInterfaceView {
    }
}
