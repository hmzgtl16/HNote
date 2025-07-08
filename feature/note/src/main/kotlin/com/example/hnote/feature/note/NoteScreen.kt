package com.example.hnote.feature.note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnote.core.design.component.AppAlertDialog
import com.example.hnote.core.design.component.AppBackground
import com.example.hnote.core.design.component.AppBottomAppBar
import com.example.hnote.core.design.component.AppIconButton
import com.example.hnote.core.design.component.AppOutlinedTextField
import com.example.hnote.core.design.component.AppTextButton
import com.example.hnote.core.design.component.ThemePreviews
import com.example.hnote.core.design.icon.AppIcons
import com.example.hnote.core.design.theme.AppTheme
import com.example.hnote.core.ui.DevicePreviews
import com.example.hnote.core.ui.PaletteModalBottomSheet
import com.example.hnote.core.ui.formatter
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun NoteRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel(),
) {
    val note by viewModel.note.collectAsStateWithLifecycle()
    val title by viewModel.title.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()
    val backgroundColor by viewModel.backgroundColor.collectAsStateWithLifecycle()
    val pinned by viewModel.pinned.collectAsStateWithLifecycle()
    val paletteVisibility by viewModel.paletteVisibility.collectAsStateWithLifecycle()
    val deleteDialogVisibility by viewModel.deleteDialogVisibility.collectAsStateWithLifecycle()
    val isDeleted by viewModel.isDeleted.collectAsStateWithLifecycle()

    BackHandler(
        onBack = {
            viewModel.saveNote()
            onBackClick()
        }
    )

    LaunchedEffect(key1 = isDeleted) {
        if (note == null && isDeleted) onBackClick()
    }

    NoteScreen(
        title = title,
        onTitleChange = viewModel::onTitleChange,
        description = description,
        onDescriptionChange = viewModel::onDescriptionChange,
        backgroundColor = backgroundColor,
        onBackgroundColorChange = viewModel::onBackgroundColorChange,
        pinned = pinned,
        onPinnedChange = viewModel::onPinnedChange,
        lastEdit = note?.updated,
        paletteVisibility = paletteVisibility,
        onPaletteVisibilityChange = viewModel::onPaletteVisibilityChange,
        deleteDialogVisibility = deleteDialogVisibility,
        onDeleteDialogVisibilityChange = viewModel::onDeleteDialogVisibilityChange,
        onCopyClick = viewModel::copyNote,
        onDeleteClick = viewModel::deleteNote,
        onBackClick = {
            viewModel.saveNote()
            onBackClick()
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    backgroundColor: Int?,
    onBackgroundColorChange: (color: Int?) -> Unit,
    pinned: Boolean,
    onPinnedChange: (Boolean) -> Unit,
    lastEdit: Instant?,
    paletteVisibility: Boolean,
    onPaletteVisibilityChange: (Boolean) -> Unit,
    deleteDialogVisibility: Boolean,
    onDeleteDialogVisibilityChange: (Boolean) -> Unit,
    onCopyClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val paletteModalBottomSheetState = rememberModalBottomSheetState()
    val formatedLastEdit = remember(key1 = lastEdit) {
        lastEdit
            ?.toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
            ?.format(format = formatter)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = if (backgroundColor != null) Color(color = backgroundColor)
        else MaterialTheme.colorScheme.background,
        bottomBar = {
            AppBottomAppBar(
                actions = {
                    AppIconButton(
                        onClick = {},
                        icon = {
                            Icon(
                                imageVector = AppIcons.Undo,
                                contentDescription = null,
                            )
                        }
                    )

                    AppIconButton(
                        onClick = {},
                        icon = {
                            Icon(
                                imageVector = AppIcons.Redo,
                                contentDescription = null,
                            )
                        }
                    )

                    AppIconButton(
                        onClick = { },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Checked,
                                contentDescription = null,
                            )
                        }
                    )

                    AppIconButton(
                        onClick = { },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Reminder,
                                contentDescription = null
                            )
                        }
                    )

                    AppIconButton(
                        onClick = { onPaletteVisibilityChange(true) },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Palette,
                                contentDescription = null
                            )
                        }
                    )

                    AppIconButton(
                        onClick = onCopyClick,
                        icon = {
                            Icon(
                                imageVector = AppIcons.Copy,
                                contentDescription = null
                            )
                        }
                    )

                    AppIconButton(
                        onClick = { onDeleteDialogVisibilityChange(true) },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Delete,
                                contentDescription = null
                            )
                        }
                    )
                },
                containerColor = backgroundColor?.let { Color(color = backgroundColor) }
                    ?: Color.Transparent
            )
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.Top
                ),
                content = {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(weight = 1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 16.dp,
                            alignment = Alignment.Top
                        ),
                        content = {

                            AppOutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = title,
                                onValueChange = onTitleChange,
                                placeholder = {
                                    Text(text = stringResource(id = R.string.feature_note_title_placeholder))
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Words,
                                    autoCorrectEnabled = true,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(focusDirection = FocusDirection.Down)
                                    }
                                )
                            )

                            AppOutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = description,
                                onValueChange = onDescriptionChange,
                                placeholder = {
                                    Text(text = stringResource(id = R.string.feature_note_description_placeholder))
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Words,
                                    autoCorrectEnabled = true,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                )
                            )
                        }
                    )

                    if (lastEdit != null)
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(
                                id = R.string.feature_note_last_edit,
                                formatedLastEdit!!
                            ),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall,
                        )
                }
            )
        }
    )

    if (paletteVisibility) {
        PaletteModalBottomSheet(
            sheetState = paletteModalBottomSheetState,
            onDismissRequest = { onPaletteVisibilityChange(false) },
            backgroundColor = backgroundColor,
            onBackgroundColorChange = {
                onBackgroundColorChange(it)
                onPaletteVisibilityChange(false)
            }
        )
    }

    if (deleteDialogVisibility) {
        AppAlertDialog(
            onDismissRequest = {
                onDeleteDialogVisibilityChange(false)
            },
            confirmButton = {
                AppTextButton(
                    onClick = {
                        onDeleteDialogVisibilityChange(false)
                        onDeleteClick()
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.feature_note_delete_note_confirm),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            },
            dismissButton = {
                AppTextButton(
                    onClick = {
                        onDeleteDialogVisibilityChange(false)
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.feature_note_delete_note_dismiss),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
            },
            title = {
                Text(
                    text = stringResource(
                        id = R.string.feature_note_delete_note_title,
                        title.ifEmpty {
                            stringResource(id = R.string.feature_note_title_unspecified)
                        }
                    ),
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.feature_note_delete_note_description),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        )
    }
}

@ThemePreviews
@DevicePreviews
@Composable
internal fun NoteScreenPreview() {
    AppTheme {
        AppBackground {
            NoteScreen(
                title = "",
                onTitleChange = {},
                description = "",
                onDescriptionChange = {},
                backgroundColor = null,
                onBackgroundColorChange = {},
                pinned = false,
                onPinnedChange = {},
                lastEdit = null,
                paletteVisibility = false,
                onPaletteVisibilityChange = {},
                deleteDialogVisibility = false,
                onDeleteDialogVisibilityChange = {},
                onCopyClick = {},
                onDeleteClick = {},
                onBackClick = {}
            )
        }
    }
}
