package com.freshdesk.southwest

import android.app.Application
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.freshdesk.southwest.data.DataStore
import com.freshdesk.southwest.data.DataStore.sharedPreferences
import com.freshdesk.southwest.ui.activity.SOUTH_WEST
import com.freshdesk.southwest.utils.logEvent
import com.freshdesk.southwest.utils.logd
import com.freshdesk.southwest.utils.toast
import com.freshworks.sdk.freshdesk.FreshdeskSDK
import com.freshworks.sdk.freshdesk.FreshdeskSDK.setFreshdeskUserInteractionListener
import com.freshworks.sdk.freshdesk.backend.model.User
import com.freshworks.sdk.freshdesk.core.FreshdeskWebviewListener
import com.freshworks.sdk.freshdesk.data.SDKConfig
import com.freshworks.sdk.freshdesk.events.SDKEventID
import com.freshworks.sdk.freshdesk.handlers.FreshdeskUserInteractionListener
import com.freshworks.logging.LogUploader
import com.freshworks.logging.log.LogEntry
import com.freshworks.logging.log.LogSession
import com.freshworks.logging.sampling.Logger
import com.freshworks.sdk.freshdesk.notification.NotificationConfig
import com.freshworks.sdk.freshdesk.utils.changeLocale

const val TAG = "SouthWest"

class SouthWestApp : Application(), FreshdeskUserInteractionListener {

    private val _unreadCount: MutableLiveData<Int> = MutableLiveData()
    val unreadCount: LiveData<Int> = _unreadCount

    private val _userState: MutableLiveData<String> = MutableLiveData()
    val userState: LiveData<String> = _userState

    private val _uUID: MutableLiveData<String> = MutableLiveData()
    val uuid: LiveData<String> = _uUID

    val customLocale = "ar"
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = this.getSharedPreferences(SOUTH_WEST, MODE_PRIVATE)
        setUpFreshdeskSDK()
        setWebViewListener()
        registerBroadcastReceiver()
        setFreshdeskUserInteractionListener(applicationContext as SouthWestApp)
    }

    private fun setUpFreshdeskSDK() {
        initializeSDK(
            DataStore.getSelectedAccount(),
            this
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FreshdeskSDK.setNotificationConfig(
                NotificationConfig(
                    soundEnabled = true,
                    smallIconResId = R.drawable.ic_southwest_notification,
                    largeIconResId = R.drawable.ic_southwest_notification,
                    importance = NotificationManager.IMPORTANCE_HIGH
                )
            )
        } else {
            FreshdeskSDK.setNotificationConfig(
                NotificationConfig(
                    soundEnabled = true,
                    smallIconResId = R.drawable.ic_southwest_notification,
                    largeIconResId = R.drawable.ic_southwest_notification,
                    importance = NotificationCompat.PRIORITY_HIGH
                )
            )
        }
    }

    private fun setWebViewListener() {
        val webViewListener: FreshdeskWebviewListener = object : FreshdeskWebviewListener {
            override fun onLocaleChangedByWebView() {
                changeLocale(this@SouthWestApp, customLocale)
            }
        }
        FreshdeskSDK.setWebViewListener(webViewListener)
    }

    private fun registerBroadcastReceiver() {
        val myReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    SDKEventID.UNREAD_COUNT -> {
                        _unreadCount.value = intent.getIntExtra(SDKEventID.UNREAD_COUNT, 0)
                        context?.logEvent(
                            UNREAD_COUNT,
                            _unreadCount.value.toString(),
                            true
                        )
                    }

                    SDKEventID.RESTORE_ID_GENERATED -> {
                        val restoreId = intent.getStringExtra(SDKEventID.RESTORE_ID_GENERATED)
                        DataStore.setRestoreId(restoreId ?: "")
                        context?.logEvent(RESTORE_ID, restoreId ?: "", true)
                    }

                    SDKEventID.GET_UUID_SUCCESS -> {
                        _uUID.value = intent.getStringExtra(SDKEventID.GET_UUID_SUCCESS)
                        context?.logEvent(GET_UUID_SUCCESS, _uUID.value ?: "", true)
                    }

                    SDKEventID.MESSAGE_SENT -> {
                        context?.logEvent(MESSAGE_SENT, "${intent.extras}")
                    }

                    SDKEventID.MESSAGE_RECEIVED -> {
                        context?.logEvent(MESSAGE_RECEIVED, "${intent.extras}")
                    }

                    SDKEventID.CSAT_RECEIVED -> {
                        context?.logEvent(CSAT_RECEIVED, "${intent.extras}")
                    }

                    SDKEventID.CSAT_UPDATED -> {
                        context?.logEvent(CSAT_UPDATED, "${intent.extras}")
                    }

                    SDKEventID.DOWNLOAD_FILE -> {
                        context?.logEvent(DOWNLOAD_FILE, "${intent.extras}")
                    }

                    else -> userEvents(intent, context)
                }
            }
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(
            myReceiver,
            IntentFilter().apply {
                addAction(SDKEventID.USER_CREATED)
                addAction(SDKEventID.RESTORE_ID_GENERATED)
                addAction(SDKEventID.UNREAD_COUNT)
                addAction(SDKEventID.USER_STATE_CHANGE)
                addAction(SDKEventID.GET_UUID_SUCCESS)
                addAction(SDKEventID.MESSAGE_SENT)
                addAction(SDKEventID.MESSAGE_RECEIVED)
                addAction(SDKEventID.CSAT_RECEIVED)
                addAction(SDKEventID.CSAT_UPDATED)
                addAction(SDKEventID.DOWNLOAD_FILE)
                addAction(SDKEventID.USER_CLEARED)
            }
        )
    }

    @Suppress("DEPRECATION")
    fun userEvents(intent: Intent?, context: Context?) {
        when (intent?.action) {
            SDKEventID.USER_CREATED -> {
                val user: User? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.extras?.getParcelable(SDKEventID.USER_CREATED, User::class.java)
                    } else {
                        intent.extras?.getParcelable(SDKEventID.USER_CREATED) as? User
                    }
                user?.let {
                    DataStore.setUserAlias(it.alias)
                    if (DataStore.getRestoreId().isEmpty()) {
                        DataStore.setRestoreId(it.restoreId ?: "")
                    }
                    context?.logEvent(USER_CREATED, user.alias, true)
                }
            }

            SDKEventID.USER_STATE_CHANGE -> {
                _userState.value = intent.getStringExtra(SDKEventID.USER_STATE_CHANGE)
                context?.logEvent(USER_STATE, _userState.value ?: "", true)
            }

            SDKEventID.USER_CLEARED -> {
                context?.logEvent(USER_CLEARED, "")
            }

            SDKEventID.USER_AUTHENTICATED -> {
                context?.logEvent(USER_AUTHENTICATED, "")
            }
        }
    }

    override fun onUserInteraction() {
        logd { "onUserInteraction() called" }
        if (DataStore.getUserActionState()) {
            toast("User interaction!!")
        }
    }

    companion object {

        private const val UNREAD_COUNT = "UNREAD COUNT"
        private const val RESTORE_ID = "RESTORE ID"
        private const val MESSAGE_SENT = "MESSAGE SENT"
        private const val MESSAGE_RECEIVED = "MESSAGE RECEIVED"
        private const val CSAT_RECEIVED = "CSAT RECEIVED"
        private const val CSAT_UPDATED = "CSAT UPDATED"
        private const val USER_STATE = "USER STATE"
        private const val USER_CLEARED = "USER CLEARED"
        private const val DOWNLOAD_FILE = "DOWNLOAD FILE"
        private const val GET_UUID_SUCCESS = "GET UUID SUCCESS"
        private const val USER_CREATED = "USER_CREATED"

        private const val USER_AUTHENTICATED = "USER AUTHENTICATED"

        fun initializeSDK(sdkConfig: SDKConfig, context: Context) {
            FreshdeskSDK.initialize(
                context,
                sdkConfig.copy(
                    debugMode = true
                )
            ) {
                Log.d(TAG, "SDK initialization complete")
            }
        }
    }
}
