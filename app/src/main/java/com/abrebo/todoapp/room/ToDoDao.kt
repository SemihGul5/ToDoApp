package com.abrebo.todoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.abrebo.todoapp.data.model.ToDo

@Dao
interface ToDoDao {

    @Insert
    suspend fun save(todo: ToDo)

    @Query("select  * from yapilicaklar")
    suspend fun upload():List<ToDo>

    @Query("delete from yapilicaklar where id= :id")
    suspend fun delete(id:Int)

    @Update
    suspend fun update(todo:ToDo)

    @Query("select * from yapilicaklar where `desc` like '%' ||:search|| '%'")
    suspend fun search(search:String):List<ToDo>

    @Query("select * from yapilicaklar order by priotary ")
    suspend fun priotaryLow():List<ToDo>

    @Query("select * from yapilicaklar order by priotary desc")
    suspend fun priotaryHigh():List<ToDo>

    @Query("select * from yapilicaklar order by date")
    suspend fun dateOrderBy():List<ToDo>

    @Query("select * from yapilicaklar order by date desc")
    suspend fun dateOrderByDesc():List<ToDo>

}