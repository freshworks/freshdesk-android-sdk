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
import com.freshdesk.southwest.R.string.update_ticket
import com.freshdesk.southwest.components.FieldConfig
import com.freshdesk.southwest.components.FormField
import com.freshdesk.southwest.components.buttons.ClearButton
import com.freshdesk.southwest.ui.theme.SouthWestTheme
import com.freshdesk.southwest.utils.toMap

@Composable
fun UpdateTicketDialog(
    onConfirmed: (Map<String, Any>, String) -> Unit,
    onDismissed: () -> Unit
) {
    val ticketPropertiesMap = rememberSaveable { mutableStateOf("") }
    val ticketPropertiesJWT = rememberSaveable { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = {
        },
        title = {
            Text(text = stringResource(id = update_ticket), color = colorScheme.primary)
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                ShowDescription(R.string.properties_description)
                FormField(
                    labelId = R.string.custom_ticket_properties,
                    value = ticketPropertiesMap.value,
                    config = FieldConfig(
                        isRequired = false,
                        isBlank = ticketPropertiesMap.value.isBlank(),
                        trailingIcon = { ClearButton { ticketPropertiesMap.value = "" } }
                    ),
                    onValueChange = { ticketPropertiesMap.value = it }
                )
                ShowDescription(R.string.jwt_ticket_properties_description)
                FormField(
                    labelId = R.string.ticket_property_as_jwt,
                    value = ticketPropertiesJWT.value,
                    config = FieldConfig(
                        isRequired = false,
                        isBlank = ticketPropertiesJWT.value.isBlank(),
                        trailingIcon = { ClearButton { ticketPropertiesJWT.value = "" } }
                    ),
                    onValueChange = { ticketPropertiesJWT.value = it }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmed(ticketPropertiesMap.value.toMap(), ticketPropertiesJWT.value)
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
            ) {
                Text(text = stringResource(id = R.string.dismiss))
            }
        }
    )
}

@Preview
@Composable
fun ShowPropertiesDialog() {
    SouthWestTheme {
        UpdateTicketDialog(onConfirmed = { map, token -> }) {}
    }
}
