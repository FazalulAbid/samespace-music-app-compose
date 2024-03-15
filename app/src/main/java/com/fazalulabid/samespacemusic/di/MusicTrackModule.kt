package com.fazalulabid.samespacemusic.di

import com.fazalulabid.samespacemusic.BuildConfig
import com.fazalulabid.samespacemusic.data.db.MusicTracksDatabase
import com.fazalulabid.samespacemusic.data.remote.MusicTrackApiService
import com.fazalulabid.samespacemusic.data.repository.MusicTrackRepositoryImpl
import com.fazalulabid.samespacemusic.data.repository.datasource.MusicTrackLocalDataSource
import com.fazalulabid.samespacemusic.data.repository.datasourceimpl.MusicTrackLocalDataSourceImpl
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
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()
            .create(MusicTrackApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMusicTracksLocalDataSource(
        db: MusicTracksDatabase
    ): MusicTrackLocalDataSource = MusicTrackLocalDataSourceImpl(db.musicTracksDao())

    @Provides
    @Singleton
    fun provideMusicTrackRepository(
        apiService: MusicTrackApiService,
        musicTrackLocalDataSource: MusicTrackLocalDataSource
    ): MusicTrackRepository =
        MusicTrackRepositoryImpl(
            apiService = apiService,
            musicTrackLocalDataSource = musicTrackLocalDataSource
        )
}