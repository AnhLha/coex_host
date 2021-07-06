package com.upit.coex.host.contract.addcoostep1;

import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

import java.util.ArrayList;

public class AddCooStep1Contract {
    public interface AddCooStep1InterfaceViewModel extends BaseInterfaceViewModel{
        void doStep2(String name, String location, String aboutspace, ArrayList<String> listImg);
    }

    public interface AddCooStep1InterfaceView extends BaseInterfaceView{
        void doStep2Success();

        void doStep2Failed();
    }
}
