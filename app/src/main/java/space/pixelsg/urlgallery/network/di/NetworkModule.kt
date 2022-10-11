package space.pixelsg.urlgallery.network.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.pixelsg.urlgallery.di.AppScope
import space.pixelsg.urlgallery.network.api.Api

@Module
class NetworkModule {
    companion object {
        const val BASE_URL = "https://data.pixelsg.space/"
    }

    @Provides
    @AppScope
    fun provideGson() = Gson()

    @Provides
    @AppScope
    fun provideRetrofit(gson: Gson) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @AppScope
    fun provideApi(retrofit: Retrofit) = retrofit.create(Api::class.java)
}