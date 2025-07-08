package com.example.hnote.core.design.component

import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import com.commandiron.wheel_picker_compose.core.SelectorProperties
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.example.hnote.core.design.theme.AppTheme
import java.time.LocalDateTime

@Composable
fun AppDateTimePicker(
    modifier: Modifier = Modifier,
    startDateTime: LocalDateTime,
    yearsRange: IntRange,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDateTime: (snappedDateTime: LocalDateTime) -> Unit
) {

    WheelDateTimePicker(
        modifier = modifier,
        startDateTime = startDateTime,
        yearsRange = yearsRange,
        textStyle = textStyle,
        textColor = textColor,
        selectorProperties = selectorProperties,
        onSnappedDateTime = onSnappedDateTime
    )
}

@ThemePreviews
@Composable
fun AppDateTimePickerPreview() {
    AppTheme {
        AppBackground(
            modifier = Modifier.height(height = 200.dp),
            content = {
                AppDateTimePicker(
                    startDateTime = LocalDateTime.now(),
                    yearsRange = 1900..2100,
                    onSnappedDateTime = {}
                )
            })
    }
}