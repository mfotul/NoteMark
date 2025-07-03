package com.example.notemark.note.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notemark.R
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.core.presentation.util.ObserveAsEvent
import com.example.notemark.note.presentation.settings.components.SettingsTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenRoot(
    onBackClick: () -> Unit,
    onLogOut: () -> Unit
) {
    val viewModel: SettingsViewModel = koinViewModel()

    ObserveAsEvent(viewModel.events) {
        when (it) {
            SettingsEvent.OnLogOut -> onLogOut()
        }
    }

    SettingsScreen(
        onAction = {
            when (it) {
                SettingsAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun SettingsScreen(
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SettingsTopAppBar(
                onBackClick = { onAction(SettingsAction.OnBackClick) }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.clickable {
                    onAction(SettingsAction.OnLogOutClick)
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.log_out),
                    contentDescription = stringResource(R.string.log_out)
                )
                Text(
                    text = stringResource(R.string.log_out),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

        }
    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    NoteMarkTheme {
        SettingsScreen(
            onAction = {}
        )
    }
}