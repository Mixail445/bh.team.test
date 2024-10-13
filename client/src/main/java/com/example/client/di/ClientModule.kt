package com.example.client.di

import com.example.client.data.Api
import com.example.client.data.BASE_URL
import com.example.client.data.ClientRemoteSource
import com.example.client.data.ClientRepositoryImpl
import com.example.client.data.RemoteSourceImpl
import com.example.client.domain.ClientRepository
import com.example.common.DispatchersProvider
import com.example.common.DispatchersProviderImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientModule {

    @Singleton
    @Provides
    fun provideRemoteSource(
        api: Api,
        dispatchersProvider: DispatchersProvider,
    ): ClientRemoteSource = RemoteSourceImpl(api, dispatchersProvider)

    @Singleton
    @Provides
    fun provideDispatcher(): DispatchersProvider = DispatchersProviderImpl

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor())
            .build()

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi
            .Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
    @Singleton
    @Provides
    fun provideRepository(repositoryImpl: ClientRepositoryImpl): ClientRepository = repositoryImpl
}