package com.fazalulabid.samespacemusic.core.util

import com.fazalulabid.samespacemusic.data.datastore.DataStoreHelper
import javax.inject.Inject

class CacheManager @Inject constructor(
    private val dataStoreHelper: DataStoreHelper
) {

    private val cacheLifeTimeMillis = 2 * 60 * 60 * 1000 // 2 hours in milliseconds

    suspend fun isDataValid(): Boolean {
        val lastFetchedTime = dataStoreHelper.getLastFetchedTime() ?: 0
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastFetchedTime) < cacheLifeTimeMillis
    }
}