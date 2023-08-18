package edu.bedaev.universeofrickandmorty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.bedaev.universeofrickandmorty.data.CharacterService
import edu.bedaev.universeofrickandmorty.data.EpisodeService
import edu.bedaev.universeofrickandmorty.data.LocationService
import edu.bedaev.universeofrickandmorty.network.api.CharactersApi
import edu.bedaev.universeofrickandmorty.network.api.EpisodeApi
import edu.bedaev.universeofrickandmorty.network.api.LocationApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://rickandmortyapi.com"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
            .followRedirects(true)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideCharacterNetworkService(characterApi: CharactersApi): CharacterService =
        CharacterService(api = characterApi)

    @Provides
    fun provideLocationNetworkService(locationApi: LocationApi): LocationService =
        LocationService(api = locationApi)

    @Provides
    fun provideEpisodesNetworkService(episodeApi: EpisodeApi): EpisodeService =
        EpisodeService(api = episodeApi)

}