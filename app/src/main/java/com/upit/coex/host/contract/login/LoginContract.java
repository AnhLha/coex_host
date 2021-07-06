package com.upit.coex.host.contract.login;

import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

/**
 * Created by cin on 3/9/2020.
 */

public class LoginContract {
    public interface LoginInterfaceViewModel extends BaseInterfaceViewModel {
        void doLogin(String inputPhone, String passWord);

        void checkCo();


//        void onRegister();
//
//        void onForgotPassword();
    }

    public interface LoginInterfaceView extends BaseInterfaceView {
        void loginFailed();

        void loginSuccess();


    }
}
