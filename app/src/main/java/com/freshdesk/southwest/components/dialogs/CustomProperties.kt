package com.freshdesk.southwest.components.dialogs

import androidx.compose.runtime.Composable
import com.freshdesk.southwest.R
import com.freshdesk.southwest.components.FieldConfig
import com.freshdesk.southwest.components.FormField
import com.freshdesk.southwest.components.buttons.ClearButton

@Composable
fun CustomProperties(properties: String, onValueChange: (String) -> Unit) {
    ShowDescription(R.string.properties_description)
    FormField(
        labelId = R.string.custom_properties,
        value = properties,
        config = FieldConfig(
            isBlank = properties.isEmpty(),
            trailingIcon = {
                ClearButton {
                    onValueChange.invoke("")
                }
            }
        ),
        onValueChange = {
            onValueChange.invoke(it)
        }
    )
}