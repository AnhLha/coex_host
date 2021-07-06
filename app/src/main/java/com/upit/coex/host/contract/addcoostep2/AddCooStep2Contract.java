package com.upit.coex.host.contract.addcoostep2;

import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

public class AddCooStep2Contract {

    public interface AddCooStep2InterfaceViewModel extends BaseInterfaceViewModel {
        void doStep3();
    }

    public interface AddCooStep2InterfaceView extends BaseInterfaceView {
        void doStep3Success();

        void doStep3Failed();
    }
}


