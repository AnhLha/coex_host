package com.upit.coex.host.contract.searchaddress;

import com.upit.coex.host.contract.base.BaseInterfaceViewModel;

public class SearchAddressContract {
    public interface SearchAddressInterfaceViewModel extends BaseInterfaceViewModel {

        void moveCamera(double lat, double lng);

        void googleApiClientStart();

        void googleApiClientStop();

        void removeAllMarker();

        void checkToken();

        void removeDisposal();

    }

}
