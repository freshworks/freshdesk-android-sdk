package com.freshdesk.southwest.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import com.freshdesk.southwest.components.buttons.ConfirmButton
import com.freshdesk.southwest.components.buttons.DismissButton
import com.freshdesk.southwest.ui.theme.SouthWestTheme
import java.util.Locale

@Composable
fun UpdateAuthTokenDialog(
    userState: String,
    authTokenValue: String,
    onValueChange: (String) -> Unit,
    onDismissed: () -> Unit
) {
    val authToken = rememberSaveable { mutableStateOf(authTokenValue) }
    AlertDialog(
        onDismissRequest = {
        },
        title = { Text(text = stringResource(id = R.string.update_jwt_auth_token)) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {
                FormField(
                    labelId = R.string.user_state,
                    value = userState.uppercase(Locale.ROOT),
                    config = FieldConfig(isReadOnly = true),
                    onValueChange = {}
                )
                FormField(
                    labelId = R.string.jwt_token,
                    value = authToken.value,
                    config = FieldConfig(
                        isRequired = false,
                        isBlank = authToken.value.isBlank(),
                        trailingIcon = {
                            ClearButton {
                                authToken.value = ""
                            }
                        }
                    ),
                    onValueChange = {
                        authToken.value = it
                    }
                )
            }
        },
        confirmButton = {
            ConfirmButton(R.string.authenticate) {
                onValueChange.invoke(authToken.value)
            }
        },
        dismissButton = {
            DismissButton {
                onDismissed.invoke()
            }
        }
    )
}

@Preview
@Composable
fun ShowSelectorDialog() {
    SouthWestTheme {
        UpdateAuthTokenDialog(
            userState = "AUTHENTICATED",
            authTokenValue = "",
            onValueChange = {}
        ) {}
    }
}