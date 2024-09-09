package com.abrebo.todoapp.di

import android.content.Context
import androidx.room.Room
import com.abrebo.todoapp.data.datasource.DataSource
import com.abrebo.todoapp.data.repo.Repository
import com.abrebo.todoapp.room.Database
import com.abrebo.todoapp.room.ToDoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataSource(toDoDao: ToDoDao):DataSource{
        return DataSource(toDoDao)
    }

    @Provides
    @Singleton
    fun provideToDoDao(@ApplicationContext context: Context):ToDoDao{
        val db=Room.databaseBuilder(context,Database::class.java,"todo.sqlite")
            .createFromAsset("todo.sqlite").build()
        return db.getToDoDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dataSource:DataSource):Repository{
        return Repository(dataSource)
    }

}