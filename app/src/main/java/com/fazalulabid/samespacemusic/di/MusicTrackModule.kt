package com.fazalulabid.samespacemusic.di

import com.fazalulabid.samespacemusic.core.util.CacheManager
import com.fazalulabid.samespacemusic.core.util.Constants
import com.fazalulabid.samespacemusic.data.datastore.DataStoreHelper
import com.fazalulabid.samespacemusic.data.db.MusicTracksDatabase
import com.fazalulabid.samespacemusic.data.remote.MusicTrackApiService
import com.fazalulabid.samespacemusic.data.repository.MusicTrackRepositoryImpl
import com.fazalulabid.samespacemusic.data.repository.datasource.MusicTrackLocalDataSource
import com.fazalulabid.samespacemusic.data.repository.datasource.MusicTrackRemoteDataSource
import com.fazalulabid.samespacemusic.data.repository.datasourceimpl.MusicTrackLocalDataSourceImpl
import com.fazalulabid.samespacemusic.data.repository.datasourceimpl.MusicTrackRemoteDataSourceImpl
import com.fazalulabid.samespacemusic.domain.repository.MusicTrackRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicTrackModule {

    @Provides
    @Singleton
    fun provideMusicTracksApiService(client: OkHttpClient): MusicTrackApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
            .create(MusicTrackApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMusicTracksRemoteDataSource(
        apiService: MusicTrackApiService
    ): MusicTrackRemoteDataSource = MusicTrackRemoteDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideMusicTracksLocalDataSource(
        db: MusicTracksDatabase
    ): MusicTrackLocalDataSource = MusicTrackLocalDataSourceImpl(db.musicTracksDao())

    @Provides
    @Singleton
    fun provideMusicTrackRepository(
        remoteDataSource: MusicTrackRemoteDataSource,
        localDataSource: MusicTrackLocalDataSource,
        dataStoreHelper: DataStoreHelper,
        cacheManager: CacheManager
    ): MusicTrackRepository =
        MusicTrackRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            dataStoreHelper = dataStoreHelper,
            cacheManager = cacheManager
        )
}