package dev.rinav.composepapers.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rinav.composepapers.BuildConfig
import dev.rinav.composepapers.core.C
import dev.rinav.composepapers.core.remote.AuthInterceptor
import dev.rinav.composepapers.data.remote.ImageApiService
import dev.rinav.composepapers.data.repository.ImageRepositoryImpl
import dev.rinav.composepapers.domain.repository.ImageRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    private val client: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .addInterceptor(AuthInterceptor())
        .build()

    @Provides
    @Singleton
    fun providesUnsplashApiService(): ImageApiService {

        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(C.BASE_URL).client(client).build()
            .create(ImageApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        apiService: ImageApiService
    ): ImageRepository {
        return ImageRepositoryImpl(
            apiService = apiService
        )
    }
}