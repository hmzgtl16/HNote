package com.example.hnote.core.datastore

import androidx.datastore.core.DataStore
import jakarta.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    
}