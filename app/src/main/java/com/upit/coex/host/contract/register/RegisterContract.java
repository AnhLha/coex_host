package com.upit.coex.host.contract.register;

import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

public class RegisterContract {
    public interface RegisterInterfaceViewModel extends BaseInterfaceViewModel{
        void doRegister(String email, String password, String passcon, boolean typeUser);
    }

    public interface RegisterInterfaceView extends BaseInterfaceView{
        void registerSuccess();

        void registerFailed();
    }
}
