package com.example.hnote.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.hnote.core.design.component.AppBackground
import com.example.hnote.core.design.component.ThemePreviews
import com.example.hnote.core.design.theme.AppTheme
import com.example.hnote.core.model.Note
import com.example.hnote.core.model.Reminder
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime

@Composable
fun ReminderCard(
    reminder: Reminder,
    modifier: Modifier = Modifier
) {
    val formattedTime = remember(reminder.time) {
        reminder.time
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .format(formatter)
    }

    val isExpired = remember(reminder.time) {
        reminder.time < Clock.System.now()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Start
        ),
        content = {
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (isExpired) {
                Text(
                    text = stringResource(id = R.string.core_ui_reminder_expired),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Icon(
                imageVector = reminder.repeatMode.icon(),
                contentDescription = "Repeat",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    )
}

@ThemePreviews
@Composable
fun ReminderCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>
) {
    AppTheme {
        AppBackground(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 80.dp),
            content = {
                notes[true]!![5].reminder?.let {
                    ReminderCard(
                        reminder = it,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        )
    }
}

