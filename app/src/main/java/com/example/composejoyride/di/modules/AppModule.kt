package com.example.composejoyride.di.modules

import android.content.Context
import androidx.room.Room
import com.example.composejoyride.data.Interactors.ParseInteractor
import com.example.composejoyride.data.dao.ArticlesDao
import com.example.composejoyride.data.dao.NotesDao
import com.example.composejoyride.data.databases.ArticlesDatabase
import com.example.composejoyride.data.databases.NotesDatabase
import com.example.composejoyride.data.repositories.ArticlesRepository
import com.example.composejoyride.data.repositories.NotesRepository
import com.example.composejoyride.data.repositories.RhymeRepository
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
            "notes"
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideNotesDao(database: NotesDatabase): NotesDao {
        return database.notesDao()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepository(notesDao)
    }

    @Provides
    @Singleton
    fun provideRhymeRepository(interactor: ParseInteractor): RhymeRepository {
        return RhymeRepository(interactor)
    }

    @Provides
    @Singleton
    fun provideInteractor(): ParseInteractor {
        return ParseInteractor()
    }


    @Provides
    @Singleton
    fun provideArticlesDatabase(@ApplicationContext context: Context): ArticlesDatabase {
        return Room.databaseBuilder(
            context,
            ArticlesDatabase::class.java,
            "cache"
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideArticlesDao(database: ArticlesDatabase): ArticlesDao {
        return database.articlesDao()
    }

    @Provides
    @Singleton
    fun provideArticlesRepository(
        interactor: ParseInteractor,
        articlesDao: ArticlesDao
    ): ArticlesRepository {
        return ArticlesRepository(interactor, articlesDao)
    }

//    @Provides
//    @Singleton
//    fun provideApplication(@ApplicationContext context: Context): Application {
//        return context as Application
//    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}
