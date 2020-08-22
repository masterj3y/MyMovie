package io.github.masterj3y.mymovie.core.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.masterj3y.mymovie.core.database.AppDatabase
import io.github.masterj3y.mymovie.core.network.AuthenticationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient().newBuilder()
        .addInterceptor(AuthenticationInterceptor())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("http://www.omdbapi.com/")
            .build()

    @Provides
    @Singleton
    fun provideAppDatabase(context: Application): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "DB").build()
}