package com.upit.coex.host.contract.splash;

import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

/**
 * Created by cin on 3/9/2020.
 */

public class SplashContract {
    public interface SplashInterfaceViewModel extends BaseInterfaceViewModel {

        void checkToken();

        void removeDisposal();
    }
}
