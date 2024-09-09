package com.abrebo.todoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "yapilicaklar")
data class ToDo(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @NotNull var id:Int,
                @ColumnInfo(name = "desc") @NotNull var desc:String,
                @ColumnInfo(name = "priotary") @NotNull var priotary:Int,
                @ColumnInfo(name = "date") @NotNull var date:String):Serializable {
}