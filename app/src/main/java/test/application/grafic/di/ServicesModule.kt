package test.application.grafic.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import test.application.grafic.network.services.PointsApiService

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    fun provideSignInApiService(retrofit: Retrofit): PointsApiService =
        retrofit.create(PointsApiService::class.java)
}