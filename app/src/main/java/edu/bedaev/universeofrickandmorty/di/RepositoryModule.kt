package edu.bedaev.universeofrickandmorty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.bedaev.universeofrickandmorty.data.ListItemRepository
import edu.bedaev.universeofrickandmorty.database.RickAndMortyDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideListItemRepository(
        database: RickAndMortyDatabase
    ): ListItemRepository =
        ListItemRepository(database = database)
}