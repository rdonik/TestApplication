package test.application.grafic.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.application.grafic.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val applicationJson = "application/json"

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    fun provideRetrofitClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        baseInterceptor: Interceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(Interceptor {
                val request: Request = it.request().newBuilder()
                    .header("Accept", applicationJson)
                    .build()
                return@Interceptor it.proceed(request)
            })
            .addInterceptor(Interceptor {
                val request: Request = it.request().newBuilder()
                    .header("Accept", applicationJson)
                    .header("Content-Type", applicationJson)
                    .build()
                return@Interceptor it.proceed(request)
            })
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(baseInterceptor)
            .addInterceptor(chuckerInterceptor)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext appContext: Context) =
        ChuckerInterceptor.Builder(appContext).build()

    @Provides
    @Singleton
    fun provideBaseInterceptor(): Interceptor = Interceptor.invoke { chain ->
        val newUrl = chain
            .request()
            .url
            .newBuilder()
            .build()
        val request = chain
            .request()
            .newBuilder()
            .url(newUrl)
            .build()
        return@invoke chain.proceed(request)
    }
}