package com.fazalulabid.samespacemusic.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

const val DATASTORE_NAME = "music_app_datastore"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class DataStoreHelper(private val context: Context) {

    companion object {
        val LAST_FETCHED_TIME_KEY = longPreferencesKey("last_fetched_time")
    }

    suspend fun setLastFetchedTime(currentTime: Long) {
        context.datastore.edit { cacheData ->
            cacheData[LAST_FETCHED_TIME_KEY] = currentTime
        }
    }

    suspend fun getLastFetchedTime(): Long? {
        val cacheData = context.datastore.data.first()
        return cacheData[LAST_FETCHED_TIME_KEY]
    }
}