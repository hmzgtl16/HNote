package com.example.hnote.core.design.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hnote.core.design.icon.AppIcons
import com.example.hnote.core.design.theme.AppTheme

@Composable
fun AppIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
) {

    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(),
        content = {
            icon()
        }
    )
}

@ThemePreviews
@Composable
fun AppIconButtonPreview() {
    AppTheme {
        AppIconButton(
            onClick = {},
            icon = {
                Icon(
                    imageVector = AppIcons.Back,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        )
    }
}