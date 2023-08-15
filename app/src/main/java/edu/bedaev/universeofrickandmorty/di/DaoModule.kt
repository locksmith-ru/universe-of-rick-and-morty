package edu.bedaev.universeofrickandmorty.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.bedaev.universeofrickandmorty.database.RickAndMortyDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = RickAndMortyDatabase::class.java,
            name = RickAndMortyDatabase.DATABASE_NAME
        )
            .build()
    }
}