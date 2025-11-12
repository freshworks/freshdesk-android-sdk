package com.freshdesk.southwest.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.freshdesk.southwest.R
import com.freshdesk.southwest.components.FieldConfig
import com.freshdesk.southwest.components.FormField
import com.freshdesk.southwest.components.buttons.ClearButton
import com.freshdesk.southwest.data.User
import com.freshdesk.southwest.ui.theme.SouthWestTheme
import com.freshdesk.southwest.utils.logi
import com.freshdesk.southwest.utils.toMap

@Composable
fun UpdateUserDialog(
    onConfirmed: (Map<String, Any>, String) -> Unit,
    onDismissed: () -> Unit
) {
    val userProperties = rememberSaveable { mutableStateOf("") }
    val userPropertiesJwt = rememberSaveable { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = stringResource(id = R.string.update_user), color = colorScheme.primary)
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                ShowDescription(R.string.properties_description)
                FormField(
                    labelId = R.string.custom_properties,
                    value = userProperties.value,
                    config = FieldConfig(
                        isRequired = false,
                        isBlank = userProperties.value.isBlank(),
                        trailingIcon = { ClearButton { userProperties.value = "" } }
                    ),
                    onValueChange = { userProperties.value = it }
                )
                ShowDescription(R.string.jwt_properties_description)
                FormField(
                    labelId = R.string.user_property_as_jwt,
                    value = userPropertiesJwt.value,
                    config = FieldConfig(
                        isRequired = false,
                        isBlank = userPropertiesJwt.value.isBlank(),
                        trailingIcon = { ClearButton { userPropertiesJwt.value = "" } }
                    ),
                    onValueChange = { userPropertiesJwt.value = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    logi { "User Properties to be saved: ${userProperties.value}" }
                    onConfirmed(userProperties.value.toMap(), userPropertiesJwt.value)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.surface
                )
            ) { Text(text = stringResource(id = R.string.update)) }
        },
        dismissButton = {
            Button(
                onClick = { onDismissed.invoke() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.surface
                )
            ) { Text(text = stringResource(id = R.string.dismiss)) }
        }
    )
}

@Composable
fun ContactDetails(user: User, onValueChange: (User) -> Unit) {
    FormField(
        labelId = R.string.email,
        value = user.email,
        config = FieldConfig(
            isBlank = user.email.isBlank(),
            trailingIcon = { ClearButton { onValueChange.invoke(user.copy(email = "")) } }
        ),
        onValueChange = { onValueChange.invoke(user.copy(email = it)) }
    )
    FormField(
        labelId = R.string.phone_country,
        value = user.phoneCountry,
        config = FieldConfig(
            isBlank = user.phoneCountry.isBlank(),
            trailingIcon = { ClearButton { onValueChange.invoke(user.copy(phoneCountry = "")) } }
        ),
        onValueChange = { onValueChange.invoke(user.copy(phoneCountry = it)) }
    )
    FormField(
        labelId = R.string.phone_number,
        value = user.phoneNumber,
        config = FieldConfig(
            isBlank = user.phoneNumber.isBlank(),
            trailingIcon = { ClearButton { onValueChange.invoke(user.copy(phoneNumber = "")) } }
        ),
        onValueChange = { onValueChange.invoke(user.copy(phoneNumber = it)) }
    )
}

@Preview
@Composable
fun ShowUserDialog() {
    SouthWestTheme {
        UpdateUserDialog(onConfirmed = { map, token ->
        }, onDismissed = {
        })
    }
}
