package com.example.hnote.core.datastore

import com.example.hnote.core.model.Theme

fun UiThemeConfigProto.asTheme(): Theme = when (this) {
    UiThemeConfigProto.UI_THEME_CONFIG_UNSPECIFIED -> Theme.FOLLOW_SYSTEM
    UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM -> Theme.FOLLOW_SYSTEM
    UiThemeConfigProto.UI_THEME_CONFIG_LIGHT -> Theme.LIGHT
    UiThemeConfigProto.UI_THEME_CONFIG_DARK -> Theme.DARK
    UiThemeConfigProto.UNRECOGNIZED -> Theme.FOLLOW_SYSTEM
}

fun Theme.asProto(): UiThemeConfigProto = when (this) {
    Theme.FOLLOW_SYSTEM -> UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM
    Theme.LIGHT -> UiThemeConfigProto.UI_THEME_CONFIG_LIGHT
    Theme.DARK -> UiThemeConfigProto.UI_THEME_CONFIG_DARK
}