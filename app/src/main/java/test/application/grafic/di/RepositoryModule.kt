package test.application.grafic.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.application.grafic.network.services.PointsApiService
import test.application.grafic.network.repositorys.PointsRepository
import test.application.grafic.network.repositorys.PointsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideSignInRepository(signInApiService: PointsApiService): PointsRepository =
        PointsRepositoryImpl(signInApiService)
}