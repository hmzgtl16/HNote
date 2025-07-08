package com.example.hnote.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hnote.core.design.component.AppAlertDialog
import com.example.hnote.core.design.component.AppBackground
import com.example.hnote.core.design.component.AppDateTimePicker
import com.example.hnote.core.design.component.AppDropdownMenuItem
import com.example.hnote.core.design.component.AppExposedDropdownMenu
import com.example.hnote.core.design.component.AppExposedDropdownMenuBox
import com.example.hnote.core.design.component.AppExposedDropdownMenuTextField
import com.example.hnote.core.design.component.AppTextButton
import com.example.hnote.core.design.component.ThemePreviews
import com.example.hnote.core.design.theme.AppTheme
import com.example.hnote.core.model.RepeatMode
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderDateTimePickerDialog(
    reminder: Instant?,
    repeatMode: RepeatMode,
    onConfirmClick: (Instant, RepeatMode) -> Unit,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var newReminder by remember { mutableStateOf(value = reminder ?: Clock.System.now()) }
    var newRepeatMode by remember { mutableStateOf(value = repeatMode) }
    var repeatModeMenuExpanded by remember { mutableStateOf(value = false) }

    AppAlertDialog(
        onDismissRequest = onCancelClick,
        confirmButton = {
            AppTextButton(
                onClick = { onConfirmClick(newReminder, newRepeatMode) },
                text = {
                    Text(
                        text = stringResource(id = R.string.feature_task_reminder_picker_confirm),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
        dismissButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    AppTextButton(
                        onClick = onDeleteClick,
                        enabled = reminder != null,
                        text = {
                            Text(
                                text = stringResource(id = R.string.feature_task_reminder_picker_delete)
                            )
                        }
                    )

                    AppTextButton(
                        onClick = onCancelClick,
                        text = {
                            Text(
                                text = stringResource(id = R.string.feature_task_reminder_picker_cancel)
                            )
                        }
                    )
                }
            )
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.feature_task_reminder_picker_title),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
        },
        text = {
            Column(
                modifier = Modifier.padding(all = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.CenterVertically
                ),
                content = {

                    AppDateTimePicker(
                        startDateTime = reminder?.toJavaLocalDateTime()
                            ?: LocalDateTime.now().plusHours(1),
                        yearsRange = IntRange(
                            start = LocalDateTime.now().year,
                            endInclusive = 2100
                        ),
                        onSnappedDateTime = { newReminder = it.toKotlinInstant() }
                    )

                    AppExposedDropdownMenuBox(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = repeatModeMenuExpanded,
                        onExpandedChange = { repeatModeMenuExpanded = it },
                        text = {
                            AppExposedDropdownMenuTextField(
                                modifier = Modifier.fillMaxWidth(),
                                expanded = repeatModeMenuExpanded,
                                value = stringResource(id = newRepeatMode.id()),
                                onValueChange = {},
                                label = {
                                    Text(text = stringResource(id = R.string.feature_task_reminder_picker_repeat_mode))
                                }
                            )
                        },
                        menu = {
                            AppExposedDropdownMenu(
                                expanded = repeatModeMenuExpanded,
                                onDismissRequest = { },
                                content = {
                                    RepeatMode.entries.forEach {
                                        AppDropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = stringResource(id = it.id()),
                                                    style = MaterialTheme.typography.bodyLarge
                                                )
                                            },
                                            onClick = {
                                                newRepeatMode = it
                                                repeatModeMenuExpanded = false
                                            },
                                            leadingIcon = {}
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@ThemePreviews
@Composable
fun ReminderDateTimePickerDialogPreview() {
    AppTheme {
        AppBackground {
            ReminderDateTimePickerDialog(
                reminder = null,
                repeatMode = RepeatMode.NONE,
                onConfirmClick = { _, _ -> },
                onCancelClick = {},
                onDeleteClick = {}
            )
        }
    }
}

