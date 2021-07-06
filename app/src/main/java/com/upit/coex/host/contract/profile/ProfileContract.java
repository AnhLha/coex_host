package com.upit.coex.host.contract.profile;

import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

public class ProfileContract {
    public interface ProfileInterfaceViewModel extends BaseInterfaceViewModel{
        void changPassword(String oldP, String newP, String conP);

        void me();

        void editProfile(String id, String name, String phone);
    }

    public interface ProfileInterfaceView extends BaseInterfaceView{

    }
}
