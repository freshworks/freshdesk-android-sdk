package com.freshdesk.southwest.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freshdesk.southwest.R
import com.freshdesk.southwest.components.FieldConfig
import com.freshdesk.southwest.components.FormField
import com.freshdesk.southwest.components.buttons.ClearButton
import com.freshdesk.southwest.ui.theme.SouthWestTheme
import com.freshworks.sdk.freshdesk.data.SDKConfig
import com.google.gson.Gson

@Composable
fun LoadAccountForm(account: SDKConfig, onLoad: (SDKConfig) -> Unit, onDismiss: () -> Unit) {
    val gson = Gson()
    val selectedAccount = rememberSaveable { mutableStateOf(gson.toJson(account)) }
    val isConfirmEnabled = rememberSaveable { mutableStateOf(true) }
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            Text(
                text = stringResource(id = R.string.load_account)
            )
        },
        text = {
            AccountFormFields(gson.fromJson(selectedAccount.value, SDKConfig::class.java)) {
                selectedAccount.value = gson.toJson(it)
                isConfirmEnabled.value = !isMandatoryFieldEmpty(it)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onLoad(gson.fromJson(selectedAccount.value, SDKConfig::class.java))
                },
                enabled = isConfirmEnabled.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = stringResource(id = R.string.update)
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = stringResource(R.string.dismiss)
                )
            }
        }
    )
}

@Composable
fun AccountFormFields(selectedAccount: SDKConfig, onValueChange: (SDKConfig) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MandatoryFields(
            selectedAccount = selectedAccount,
            onValueChange = { onValueChange.invoke(it) }
        )
        LocaleField(selectedAccount, onValueChange = onValueChange)
        AuthTokenField(selectedAccount = selectedAccount, onValueChange = onValueChange)
    }
}

@Composable
fun MandatoryFields(selectedAccount: SDKConfig, onValueChange: (SDKConfig) -> Unit) {
    FormField(
        labelId = R.string.token,
        value = selectedAccount.token,
        config = FieldConfig(
            isRequired = true,
            isBlank = selectedAccount.token.isBlank(),
            trailingIcon = {
                ClearButton {
                    onValueChange.invoke(
                        selectedAccount.copy(token = "")
                    )
                }
            }
        ),
        onValueChange = {
            onValueChange.invoke(selectedAccount.copy(token = it.trim()))
        }
    )
    FormField(
        labelId = R.string.host,
        value = selectedAccount.host,
        config = FieldConfig(
            isRequired = true,
            isBlank = selectedAccount.host.isBlank(),
            trailingIcon = {
                ClearButton { onValueChange(selectedAccount.copy(host = "")) }
            }
        ),
        onValueChange = { onValueChange(selectedAccount.copy(host = it.trim())) }
    )
    FormField(
        labelId = R.string.sdk_id,
        value = selectedAccount.sdkID,
        config = FieldConfig(
            isRequired = true,
            isBlank = selectedAccount.sdkID.isBlank(),
            trailingIcon = {
                ClearButton { onValueChange(selectedAccount.copy(sdkID = "")) }
            }
        ),
        onValueChange = { onValueChange(selectedAccount.copy(sdkID = it.trim())) }
    )
}

@Composable
fun LocaleField(selectedAccount: SDKConfig, onValueChange: (SDKConfig) -> Unit) {
    FormField(
        labelId = R.string.locale,
        value = selectedAccount.locale,
        config = FieldConfig(
            isRequired = false,
            isBlank = selectedAccount.locale.isBlank(),
            trailingIcon = {
                ClearButton { onValueChange(selectedAccount.copy(locale = "")) }
            }
        ),
        onValueChange = { onValueChange(selectedAccount.copy(locale = it.trim())) }
    )
}

@Composable
fun AuthTokenField(selectedAccount: SDKConfig, onValueChange: (SDKConfig) -> Unit) {
    Text(
        text = stringResource(id = R.string.auth_token_desc),
        fontSize = 12.sp,
        modifier = Modifier.padding(horizontal = 8.dp),
        color = MaterialTheme.colorScheme.primary
    )
    FormField(
        labelId = R.string.auth_token,
        value = selectedAccount.jwt ?: "",
        config = FieldConfig(
            isRequired = false,
            isBlank = selectedAccount.jwt.isNullOrBlank(),
            trailingIcon = {
                ClearButton {
                    onValueChange(
                        selectedAccount.copy(jwt = "")
                    )
                }
            }
        ),
        onValueChange = {
            onValueChange(
                selectedAccount.copy(jwt = it.trim())
            )
        }
    )
}

private fun isMandatoryFieldEmpty(account: SDKConfig): Boolean {
    return (
        account.sdkID.isBlank() ||
            account.host.isBlank() || account.token.isBlank()
        )
}

@Composable
@Preview
fun ShowLoadAccountForm() {
    SouthWestTheme {
        LoadAccountForm(account = SDKConfig("", "", "", ""), onLoad = {}) {
        }
    }
}
