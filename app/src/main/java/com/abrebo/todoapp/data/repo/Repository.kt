package com.abrebo.todoapp.data.repo

import com.abrebo.todoapp.data.datasource.DataSource
import com.abrebo.todoapp.data.model.ToDo

class Repository(var dataSource: DataSource) {


    suspend fun save(desc:String,priotary:Int,date:String)=dataSource.save(desc, priotary, date)
    suspend fun upload():List<ToDo> = dataSource.upload()
    suspend fun delete(id:Int) = dataSource.delete(id)
    suspend fun update(toDo: ToDo) = dataSource.update(toDo)
    suspend fun search(search:String):List<ToDo> = dataSource.search(search)
    suspend fun priotaryLow():List<ToDo> = dataSource.priotaryLow()
    suspend fun priotaryHigh():List<ToDo> = dataSource.priotaryHigh()
    suspend fun orderByDateAsc():List<ToDo> = dataSource.orderByDateAsc()
    suspend fun orderByDateDesc(): List<ToDo> = dataSource.orderByDateDesc()









}