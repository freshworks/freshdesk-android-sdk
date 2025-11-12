package com.freshdesk.southwest.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.freshdesk.southwest.R
import com.freshdesk.southwest.components.CopyableField
import com.freshdesk.southwest.components.FieldConfig
import com.freshdesk.southwest.components.FormField
import com.freshdesk.southwest.components.buttons.ClearButton
import com.freshdesk.southwest.components.buttons.ConfirmButton
import com.freshdesk.southwest.components.buttons.DismissButton
import com.freshdesk.southwest.data.DialogConfig
import com.freshdesk.southwest.data.IdentifyUserData
import com.freshdesk.southwest.ui.theme.SouthWestTheme

@Composable
fun IdentifyUserDialog(
    config: DialogConfig,
    userData: IdentifyUserData,
    onConfirmed: (IdentifyUserData) -> Unit,
    onDismissed: () -> Unit
) {
    val user = rememberSaveable { mutableStateOf(userData) }
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            Text(
                text = stringResource(id = config.title),
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                CopyableField(labelId = R.string.user_alias, value = user.value.userAlias) {
                    onDismissed.invoke()
                }
                FormField(
                    labelId = R.string.external_id,
                    value = user.value.externalId,
                    config = FieldConfig(
                        isBlank = user.value.externalId.isBlank(),
                        trailingIcon = {
                            ClearButton {
                                user.value = user.value.copy(externalId = "")
                            }
                        }
                    ),
                    onValueChange = { user.value = user.value.copy(externalId = it) }
                )

                FormField(
                    labelId = R.string.restore_id,
                    value = user.value.restoreId,
                    config = FieldConfig(
                        isBlank = user.value.restoreId.isBlank(),
                        trailingIcon = {
                            ClearButton {
                                user.value = user.value.copy(restoreId = "")
                            }
                        }
                    ),
                    onValueChange = { user.value = user.value.copy(restoreId = it) }
                )
            }
        },
        confirmButton = {
            ConfirmButton(config.positiveText) {
                onConfirmed(user.value)
            }
        },
        dismissButton = {
            DismissButton { onDismissed.invoke() }
        }
    )
}

@Composable
@Preview
fun ShowIdentifyUserDialog() {
    SouthWestTheme {
        IdentifyUserDialog(
            config = DialogConfig(R.string.identify_user, R.string.identify_show),
            userData = IdentifyUserData("user123", "", ""),
            onConfirmed = {}
        ) {}
    }
}