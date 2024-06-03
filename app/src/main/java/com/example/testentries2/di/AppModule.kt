package com.example.testentries2.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.testentries2.repository.NoteRepository
import com.example.testentries2.room.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): NotesDatabase {
        return Room.databaseBuilder(
            appContext,
            NotesDatabase::class.java,
            "notes_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NotesDatabase): NoteRepository {
        return NoteRepository(database.noteDao())
    }
}