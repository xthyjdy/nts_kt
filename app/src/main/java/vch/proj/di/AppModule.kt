package vch.proj.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import vch.proj.database.NotesDatabase
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
//    @Provides
//    @Singleton
//    fun provideDatabase(
//        app: Application,
//        callback: NotesDatabase.Callback
//    ) = Room.databaseBuilder(app, NotesDatabase::class.java, "notes_db")
//            .fallbackToDestructiveMigration()
//            .addCallback(callback)
//            .build()
//
//    @Provides
//    fun providesDao(db: NotesDatabase) = db.getNoteDao()
//
//    @ApplicationScope
//    @Provides
//    @Singleton
//    fun provideApplicationScope() {
//        CoroutineScope(SupervisorJob())
//    }
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope