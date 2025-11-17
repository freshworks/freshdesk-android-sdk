package com.freshdesk.southwest.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.freshworks.sdk.freshdesk.data.SDKConfig
import com.google.gson.Gson

const val USER_NAME = "USER_NAME"
const val CONV_REF_ID = "CONV_REF_ID"
const val CONV_TOPIC_NAME = "CONV_TOPIC_NAME"
const val CONV_TOPIC_ID = "CONV_TOPIC_ID"
const val SELECTED_ACCOUNT = "SELECTED_ACCOUNT"
const val USER_ALIAS = "USER_ALIAS"
const val USER = "USER"
const val EVENT_SWITCH_STATE = "EVENT_SWITCH_STATE"
const val INBOUND_EVENT_NAME = "INBOUND_EVENT_NAME"
const val INBOUND_EVENT_DATA = "INBOUND_EVENT_DATA"
const val USER_ACTION_STATE = "USER_ACTION_STATE"

const val CUSTOM_LINK_HANDLER = "CUSTOM_LINK_HANDLER"

object DataStore {

    lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    fun getUserName(): String {
        return sharedPreferences.getString(USER_NAME, null) ?: ""
    }

    fun setUserName(userName: String) {
        sharedPreferences.edit { putString(USER_NAME, userName) }
    }

    fun setConvRefId(convRefId: String) {
        sharedPreferences.edit { putString(CONV_REF_ID, convRefId) }
    }

    fun setConvTopicName(topicNameOrId: String) {
        sharedPreferences.edit { putString(CONV_TOPIC_NAME, topicNameOrId) }
    }

    fun getConvTopicName(): String {
        return sharedPreferences.getString(CONV_TOPIC_NAME, null) ?: ""
    }

    fun setConvTopicId(topicId: String) {
        sharedPreferences.edit { putString(CONV_TOPIC_ID, topicId) }
    }

    fun getConvTopicId(): String {
        return sharedPreferences.getString(CONV_TOPIC_ID, null) ?: ""
    }

    fun setSelectedAccount(account: SDKConfig) {
        sharedPreferences.edit { putString(SELECTED_ACCOUNT, gson.toJson(account)) }
    }

    fun getSelectedAccount(): SDKConfig {
        val json = sharedPreferences.getString(SELECTED_ACCOUNT, null) ?: ""
        return gson.fromJson(json, SDKConfig::class.java)?.copy(
            ruleId = "sdkConfig",
            headerProps = emptyMap()
        ) ?: SDKConfig(
            host = "",
            token = "",
            sdkID = "",
            headerProps = emptyMap()
        )
    }

    fun setUserAlias(id: String) {
        sharedPreferences.edit { putString(USER_ALIAS, id) }
    }

    fun setUser(user: User) {
        sharedPreferences.edit { putString(USER, gson.toJson(user)) }
    }

    fun clearUser() {
        setUserName("")
        setUser(User())
        setUserAlias("")
        setConvRefId("")
        setConvTopicName("")
        setConvTopicId("")
        saveInboundEventData("")
        saveInboundEventName("")
        setCustomLinkHandlerState(false)
        saveUserActionState(false)
        saveToastEventsState(false)
        setSelectedAccount(getSelectedAccount().copy(jwt = ""))
    }

    fun saveToastEventsState(eventSwitchState: Boolean) {
        sharedPreferences.edit { putBoolean(EVENT_SWITCH_STATE, eventSwitchState) }
    }

    fun getToastEventsState(): Boolean {
        return sharedPreferences.getBoolean(EVENT_SWITCH_STATE, false)
    }

    fun saveUserActionState(userActionState: Boolean) {
        sharedPreferences.edit { putBoolean(USER_ACTION_STATE, userActionState) }
    }

    fun getUserActionState(): Boolean {
        return sharedPreferences.getBoolean(USER_ACTION_STATE, false)
    }

    fun saveInboundEventName(eventName: String) {
        sharedPreferences.edit { putString(INBOUND_EVENT_NAME, eventName) }
    }

    fun saveInboundEventData(eventData: String) {
        sharedPreferences.edit { putString(INBOUND_EVENT_DATA, eventData) }
    }

    fun getInboundEventName(): String {
        return sharedPreferences.getString(INBOUND_EVENT_NAME, null) ?: ""
    }

    fun getInboundEventData(): String {
        return sharedPreferences.getString(INBOUND_EVENT_DATA, null) ?: ""
    }

    fun setCustomLinkHandlerState(state: Boolean) {
        sharedPreferences.edit { putBoolean(CUSTOM_LINK_HANDLER, state) }
    }

    fun getCustomLinkHandlerState(): Boolean {
        return sharedPreferences.getBoolean(CUSTOM_LINK_HANDLER, false)
    }
}
