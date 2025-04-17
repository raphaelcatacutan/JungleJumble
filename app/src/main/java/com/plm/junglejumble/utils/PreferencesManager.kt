package com.plm.junglejumble.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferencesManager {

    private val MUSIC_KEY = booleanPreferencesKey("music")
    private val SOUNDS_KEY = booleanPreferencesKey("sounds")

    suspend fun saveMusic(enabled: Boolean, context: Context) {
        context.dataStore.edit { settings ->
            settings[MUSIC_KEY] = enabled
        }
    }

    fun getMusic(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { it[MUSIC_KEY] ?: false }
    }

    suspend fun saveSounds(enabled: Boolean, context: Context) {
        context.dataStore.edit { settings ->
            settings[SOUNDS_KEY] = enabled
        }
    }

    fun getSounds(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { it[SOUNDS_KEY] ?: false }
    }

}
