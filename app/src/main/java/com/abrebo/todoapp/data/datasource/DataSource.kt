package com.abrebo.todoapp.data.datasource

import com.abrebo.todoapp.data.model.ToDo
import com.abrebo.todoapp.room.ToDoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataSource(var toDoDao: ToDoDao) {

    suspend fun save(desc:String,priotary:Int,date:String){
        val todo=ToDo(0,desc,priotary,date)
        toDoDao.save(todo)
    }

    suspend fun upload():List<ToDo> =
        withContext(Dispatchers.IO){
            return@withContext toDoDao.upload()
        }

    suspend fun delete(id:Int){
        toDoDao.delete(id)
    }
    suspend fun update(toDo: ToDo){
        toDoDao.update(toDo)
    }

    suspend fun search(search:String):List<ToDo> =
        withContext(Dispatchers.IO){
            return@withContext toDoDao.search(search)
        }

    suspend fun priotaryLow():List<ToDo> =
        withContext(Dispatchers.IO){
            return@withContext toDoDao.priotaryLow()
        }
    suspend fun priotaryHigh():List<ToDo> =
        withContext(Dispatchers.IO){
            return@withContext toDoDao.priotaryHigh()
        }

    suspend fun orderByDateAsc():List<ToDo> =
        withContext(Dispatchers.IO){
            return@withContext toDoDao.dateOrderBy()
        }

    suspend fun orderByDateDesc(): List<ToDo> =
        withContext(Dispatchers.IO){
            return@withContext toDoDao.dateOrderByDesc()
        }





}