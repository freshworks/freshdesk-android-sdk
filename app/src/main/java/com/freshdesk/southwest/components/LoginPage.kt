package com.freshdesk.southwest.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freshdesk.southwest.R
import com.freshdesk.southwest.ui.activity.DIVIDER_ALPHA_3F
import com.freshdesk.southwest.ui.activity.WEIGHT_2F
import com.freshdesk.southwest.ui.theme.SouthWestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    userName: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        placeholder = { stringResource(id = R.string.user_name) },
        value = userName,
        singleLine = true,
        onValueChange = onValueChange,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    )
}

@Composable
fun DividerWithOr() {
    Row(
        modifier = Modifier
            .padding(top = 30.dp)
            .padding(horizontal = 20.dp)
            .wrapContentHeight(),
    ) {
        Divider(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .weight(1f)
                .alpha(DIVIDER_ALPHA_3F)
                .align(CenterVertically)
        )
        Text(
            text = stringResource(id = R.string.or),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.weight(WEIGHT_2F),
            textAlign = TextAlign.Center
        )
        Divider(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .weight(1f)
                .alpha(DIVIDER_ALPHA_3F)
                .align(CenterVertically)

        )
    }
}

@Preview
@Composable
fun ShowLoginPage() {
    SouthWestTheme {
        FormField(userName = "Durai", onValueChange = {})
    }
}
