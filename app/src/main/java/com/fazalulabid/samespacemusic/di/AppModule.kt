package com.fazalulabid.samespacemusic.di

import android.app.Application
import android.content.Context
import androidx.core.content.contentValuesOf
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import coil.ImageLoader
import com.fazalulabid.samespacemusic.BuildConfig
import com.fazalulabid.samespacemusic.core.util.NetworkConnectionInterceptor
import com.fazalulabid.samespacemusic.data.datastore.DataStoreHelper
import com.fazalulabid.samespacemusic.data.db.MusicTracksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val CONNECT_TIMEOUT = 20L
    private const val READ_TIMEOUT = 60L

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(NetworkConnectionInterceptor(context))
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMusicTracksDatabase(app: Application): MusicTracksDatabase =
        Room.databaseBuilder(
            app,
            MusicTracksDatabase::class.java,
            MusicTracksDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideDataStoreHelper(@ApplicationContext context: Context) = DataStoreHelper(context)

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader =
        ImageLoader.Builder(app)
            .crossfade(true)
            .crossfade(200)
            .build()

    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer =
        ExoPlayer.Builder(context).build()
}