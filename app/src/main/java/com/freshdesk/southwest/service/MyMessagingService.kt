package com.freshdesk.southwest.service

import android.util.Log
import com.freshdesk.southwest.TAG
import com.freshworks.sdk.freshdesk.FreshdeskSDK
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        println("Token : $p0")
        FreshdeskSDK.setPushRegistrationToken(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.d(TAG, "Firebase Message : ${p0.data}")
        if (FreshdeskSDK.isFreshdeskSDKNotification(p0.data)) {
            FreshdeskSDK.handleFCMNotification(p0.data)
        }
    }
}