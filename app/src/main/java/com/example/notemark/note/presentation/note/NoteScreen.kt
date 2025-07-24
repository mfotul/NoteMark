package com.example.notemark.note.presentation.note

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notemark.R
import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.presentation.designsystem.theme.NoteMarkTheme
import com.example.notemark.core.presentation.util.ObserveAsEvent
import com.example.notemark.core.presentation.util.SnackBarController
import com.example.notemark.core.presentation.util.toString
import com.example.notemark.note.presentation.components.NoteConfirmationDialog
import com.example.notemark.note.presentation.model.NoteUi
import com.example.notemark.note.presentation.model.toNoteUi
import com.example.notemark.note.presentation.note.components.NoteEditTopAppBar
import com.example.notemark.note.presentation.note.components.NoteEditBody
import com.example.notemark.note.presentation.note.components.NoteModeFloatingActionButton
import com.example.notemark.note.presentation.note.components.NoteViewBody
import com.example.notemark.note.presentation.note.components.NoteViewTopAppBar
import com.example.notemark.note.presentation.preview.PreviewModels
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteScreenRoot(
    isPortrait: Boolean,
    onBack: () -> Unit
) {
    val viewModel: NoteViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val activity = LocalActivity.current
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current

    val smallestWidthDp = remember(containerSize, density) {
        with(density) {
            minOf(
                containerSize.width.toDp(),
                containerSize.height.toDp()
            )
        }
    }

    if (state.mode == NoteMode.READER && smallestWidthDp < 600.dp)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    else
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    ObserveAsEvent(viewModel.events) { event ->
        when (event) {
            NoteEvent.OnBack,
            NoteEvent.OnClose -> {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                onBack()
            }
        }
    }

    state.note?.let { note ->
        NoteScreen(
            isPortrait = isPortrait,
            title = state.title,
            content = state.content,
            note = note.toNoteUi(),
            showDialog = state.showDialog,
            showUIElements = state.showUIElements,
            readerMode = state.readerMode,
            mode = state.mode,
            onAction = viewModel::onAction
        )
    }

}

@Composable
fun NoteScreen(
    isPortrait: Boolean,
    title: TextFieldValue,
    content: TextFieldValue,
    note: NoteUi,
    showDialog: Boolean,
    showUIElements: Boolean,
    readerMode: Boolean,
    mode: NoteMode,
    onAction: (NoteAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    var counter by remember { mutableIntStateOf(5) }
    val tween = tween<Float>(1000)

    ObserveAsEvent(SnackBarController.events, snackbarHostState) { event ->
        scope.launch {
            when (event.message) {
                is DataError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.toString(context),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    LaunchedEffect(mode) {
        if (!isPortrait && mode == NoteMode.EDIT) {
            focusRequester.requestFocus()
            onAction(
                NoteAction.OnChangeTitle(
                    title.copy(
                        selection = TextRange(title.text.length)
                    )
                )
            )
        }
    }

    LaunchedEffect(showUIElements) {
        if (showUIElements && readerMode) {
            counter = 5
            while (counter > 0) {
                delay(1000L)
                counter--
            }
            onAction(NoteAction.HideUIElements)
        }
    }

    BackHandler {
        onAction(NoteAction.OnBackClicked)
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        floatingActionButton = {
            if (mode != NoteMode.EDIT) {
                AnimatedVisibility(
                    visible = showUIElements,
                    enter = fadeIn(
                        animationSpec = tween
                    ),
                    exit = fadeOut(
                        animationSpec = tween
                    )
                ) {
                    NoteModeFloatingActionButton(
                        onAction = onAction,
                        readerMode = readerMode,
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
        contentWindowInsets = WindowInsets.systemBars,
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        val editContent: @Composable () -> Unit = {
            NoteEditTopAppBar(
                onCloseClick = { onAction(NoteAction.OnCloseClicked) },
                onSaveClick = { onAction(NoteAction.OnSaveClicked) },
                modifier = Modifier.padding(end = 16.dp)
            )
            NoteEditBody(
                isPortrait = isPortrait,
                title = title,
                content = content,
                focusRequester = focusRequester,
                onAction = onAction,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (mode) {
                NoteMode.EDIT ->
                    if (isPortrait)
                        Column { editContent() }
                    else
                        Box(
                            contentAlignment = Alignment.TopCenter,
                        ) { editContent() }

                NoteMode.READER,
                NoteMode.VIEW ->
                    if (isPortrait)
                        Column {
                            NoteViewTopAppBar(
                                onBackClick = { onAction(NoteAction.OnBackClicked) },
                            )
                            NoteViewBody(
                                isPortrait = true,
                                note = note,
                                modifier = Modifier
                                    .clickable(
                                        onClick = { onAction(NoteAction.OnScreenClick) },
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    )
                            )
                        }
                    else
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(
                                    onClick = { onAction(NoteAction.OnScreenClick) },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                )
                        ) {
                            AnimatedVisibility(
                                visible = showUIElements,
                                enter = fadeIn(
                                    animationSpec = tween
                                ),
                                exit = fadeOut(
                                    animationSpec = tween
                                )
                            ) {
                                NoteViewTopAppBar(
                                    onBackClick = { onAction(NoteAction.OnBackClicked) },
                                )
                            }
                            NoteViewBody(
                                isPortrait = false,
                                note = note,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
            }
        }
    }


    if (showDialog)
        NoteConfirmationDialog(
            title = stringResource(R.string.discard_changes),
            text = stringResource(R.string.are_you_sure_you_want_to_discard_changes),
            cancelButtonText = stringResource(R.string.keep_editing),
            confirmButtonText = stringResource(R.string.discard),
            onDismiss = { onAction(NoteAction.OnConfirmCancelClicked) },
            onConfirm = { onAction(NoteAction.OnConfirmDiscardClicked) }
        )
}

@Composable
@Preview(showBackground = true)
fun EditScreenPreview() {
    NoteMarkTheme {
        NoteScreen(
            isPortrait = true,
            title = TextFieldValue(PreviewModels.note.title),
            content = TextFieldValue(PreviewModels.note.content),
            note = PreviewModels.note.toNoteUi(),
            showDialog = false,
            showUIElements = true,
            readerMode = true,
            mode = NoteMode.READER,
            onAction = {},
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 900, heightDp = 500)
fun EditScreenPreviewLandscape() {
    NoteMarkTheme {
        NoteScreen(
            isPortrait = false,
            title = TextFieldValue(PreviewModels.note.title),
            content = TextFieldValue(PreviewModels.note.content),
            note = PreviewModels.note.toNoteUi(),
            showDialog = false,
            showUIElements = true,
            readerMode = false,
            mode = NoteMode.EDIT,
            onAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 1000, heightDp = 1500)
fun EditScreenPreviewTablet() {
    NoteMarkTheme {
        NoteScreen(
            isPortrait = true,
            title = TextFieldValue(PreviewModels.note.title),
            content = TextFieldValue(PreviewModels.note.content),
            note = PreviewModels.note.toNoteUi(),
            showDialog = false,
            showUIElements = true,
            readerMode = true,
            mode = NoteMode.READER,
            onAction = {}
        )
    }
}