package com.upit.coex.host.service.firebase;


import com.upit.coex.host.service.logger.L;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.upit.coex.host.constants.CommonConstants.COEX_FIREBASE;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CoexFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        L.d(COEX_FIREBASE,"message",remoteMessage.toString());
    }
}
