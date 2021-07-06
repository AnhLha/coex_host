package com.upit.coex.host.contract.forgotpassword;

import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

public class ForgotPasswordContract {
    public interface ForgotPasswordInterfaceViewModel extends BaseInterfaceViewModel{
        void doSend(String email);
    }

    public interface ForgotPasswordInterfaceView extends BaseInterfaceView{
        void sendSuccess();

        void sendFailed();
    }
}
