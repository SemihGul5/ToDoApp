package com.abrebo.todoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abrebo.todoapp.data.model.ToDo

@Database(entities = [ToDo::class], version = 1)
abstract class Database:RoomDatabase() {

    abstract fun getToDoDao():ToDoDao

}