package com.freshdesk.southwest.data.model

data class ConfigurationState(
    val toastEventsState: Boolean,
    val userActionState: Boolean,
    val customLinkHandlerState: Boolean
)