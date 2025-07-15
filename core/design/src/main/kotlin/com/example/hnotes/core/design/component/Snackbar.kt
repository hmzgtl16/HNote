package com.example.hnotes.core.design.component

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    actionColor: Color,
    actionContentColor: Color
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        actionContentColor = actionContentColor
    )
}