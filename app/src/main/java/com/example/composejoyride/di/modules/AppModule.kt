package com.example.composejoyride.di.modules

import android.content.Context
import androidx.room.Room
import com.example.composejoyride.data.network.interactors.ParseInteractor
import com.example.composejoyride.data.dao.ArticlesDao
import com.example.composejoyride.data.dao.NotesDao
import com.example.composejoyride.data.dao.PoemsDao
import com.example.composejoyride.data.databases.ArticlesDatabase
import com.example.composejoyride.data.databases.NotesDatabase
import com.example.composejoyride.data.databases.PoemsDatabase
import com.example.composejoyride.data.datasources.PoemOfDayStore
import com.example.composejoyride.data.datasources.ArsPoeticaAssetsDataSource
import com.example.composejoyride.data.network.interactors.PoemInteractor
import com.example.composejoyride.data.network.interactors.interfaces.IPoemInteractor
import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.data.repositories.NotesRepository
import com.example.composejoyride.data.repositories.PoemRepository
import com.example.composejoyride.data.repositories.RhymeRepository
import com.example.composejoyride.data.repositories.interfaces.IPoemRepository
import com.example.composejoyride.data.rhyme.IRhymeProvider
import com.example.composejoyride.data.rhyme.RifmovkaRhymeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notes",
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideNotesDao(database: NotesDatabase): NotesDao {
        return database.notesDao()
    }

    @Provides
    @Singleton
    fun provideArticlesDatabase(@ApplicationContext context: Context): ArticlesDatabase {
        return Room.databaseBuilder(
            context,
            ArticlesDatabase::class.java,
            "cache",
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideArticlesDao(database: ArticlesDatabase): ArticlesDao {
        return database.articlesDao()
    }

    @Provides
    @Singleton
    fun providePoemsDatabase(@ApplicationContext context: Context): PoemsDatabase {
        return Room.databaseBuilder(
            context,
            PoemsDatabase::class.java,
            "poems",
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun providePoemsDao(database: PoemsDatabase): PoemsDao {
        return database.poemsDao()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepository(notesDao)
    }

    @Provides
    @Singleton
    fun provideRhymeProvider(): IRhymeProvider = RifmovkaRhymeProvider()

    @Provides
    @Singleton
    fun provideRhymeRepository(rhymeProvider: IRhymeProvider): RhymeRepository {
        return RhymeRepository(rhymeProvider)
    }

    @Provides
    @Singleton
    fun provideInteractor(): ParseInteractor {
        return ParseInteractor()
    }

    @Provides
    @Singleton
    fun providePoemInteractor(
        poemsDao: PoemsDao,
        arsPoeticaAssetsDataSource: ArsPoeticaAssetsDataSource,
        poemOfDayStore: PoemOfDayStore
    ): IPoemInteractor = PoemInteractor(poemsDao, arsPoeticaAssetsDataSource, poemOfDayStore)

    @Provides
    @Singleton
    fun providePoemRepository(interactor: IPoemInteractor): IPoemRepository {
        return PoemRepository(interactor)
    }

    @Provides
    @Singleton
    fun provideArticlesRepository(
        interactor: ParseInteractor,
        articlesDao: ArticlesDao,
    ): ArticlesRepository {
        return ArticlesRepository(interactor, articlesDao)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}
