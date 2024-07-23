package test.application.grafic.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.application.grafic.ui.MainViewModelDelegate
import test.application.grafic.ui.MainViewModelDelegateImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelDelegate {

    @Singleton
    @Provides
    fun provideMainViewModelDelegate(): MainViewModelDelegate =
        MainViewModelDelegateImpl()

}