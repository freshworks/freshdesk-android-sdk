package com.freshdesk.southwest.data

import androidx.annotation.StringRes
import com.freshdesk.southwest.R

data class TextFieldDialog(
    val field1: String,
    val field2: String
)

data class DialogConfig(
    @StringRes val title: Int = R.string.open_topic,
    @StringRes val positiveText: Int = R.string.open,
    val showDescription: Boolean = false
)