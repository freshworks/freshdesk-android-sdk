package com.freshdesk.southwest.service

import com.freshdesk.southwest.utils.logd
import com.freshworks.sdk.freshdesk.FreshdeskSDK
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        FreshdeskSDK.setPushRegistrationToken(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if (FreshdeskSDK.isFreshdeskSDKNotification(p0.data)) {
            logd { "Received a Freshdesk Notification" }
            FreshdeskSDK.handleFCMNotification(p0.data)
        }
    }
}