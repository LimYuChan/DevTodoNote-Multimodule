package com.note.core.data.network

import com.google.gson.GsonBuilder
import com.note.core.data.BuildConfig
import com.note.core.domain.AuthStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val gsonBuilder = GsonBuilder().setLenient().serializeNulls().create()
    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(gsonBuilder)
    private val scalarConverterFactory: ScalarsConverterFactory = ScalarsConverterFactory.create()

    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        }else{
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun providesAuthInterceptor(authStorage: AuthStorage): AuthInterceptor {
        return AuthInterceptor(authStorage)
    }

    @Provides
    @Singleton
    fun providesOkHttp(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(scalarConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}