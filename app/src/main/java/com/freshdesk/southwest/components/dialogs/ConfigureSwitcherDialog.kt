package com.freshdesk.southwest.components.dialogs

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freshdesk.southwest.R
import com.freshdesk.southwest.components.buttons.ConfirmButton
import com.freshdesk.southwest.components.buttons.DismissButton
import com.freshdesk.southwest.data.DataStore.getCustomLinkHandlerState
import com.freshdesk.southwest.data.DataStore.getToastEventsState
import com.freshdesk.southwest.data.DataStore.getUserActionState
import com.freshdesk.southwest.data.model.ConfigurationState

@Composable
fun ConfigureSwitcherDialog(
    @StringRes title: Int,
    onConfirmed: (ConfigurationState) -> Unit,
    onDismissed: () -> Unit
) {
    var toastEventsState by remember { mutableStateOf(getToastEventsState()) }
    var userActionState by remember { mutableStateOf(getUserActionState()) }
    var customLinkHandlerState by remember { mutableStateOf(getCustomLinkHandlerState()) }

    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = stringResource(id = title),
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SwitcherItem(
                    labelResId = R.string.outbound_events,
                    checkedState = toastEventsState,
                    onCheckedChange = {
                        toastEventsState = it
                    }
                )

                SwitcherItem(
                    labelResId = R.string.user_action_events,
                    checkedState = userActionState,
                    onCheckedChange = {
                        userActionState = it
                    }
                )

                SwitcherItem(
                    labelResId = R.string.custom_link_handler,
                    checkedState = customLinkHandlerState,
                    onCheckedChange = {
                        customLinkHandlerState = it
                    }
                )
            }
        },
        confirmButton = {
            ConfirmButton(R.string.done) {
                onConfirmed(
                    ConfigurationState(
                        toastEventsState,
                        userActionState,
                        customLinkHandlerState
                    )
                )
            }
        },
        dismissButton = {
            DismissButton { onDismissed.invoke() }
        }
    )
}

@Composable
fun SwitcherItem(
    @StringRes labelResId: Int,
    checkedState: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = labelResId),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checkedState,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}