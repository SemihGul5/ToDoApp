package com.abrebo.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.abrebo.todoapp.data.model.ToDo
import com.abrebo.todoapp.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailToDoViewModel @Inject constructor(var repository: Repository):ViewModel() {

    fun delete(id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            repository.delete(id)
        }
    }
    fun update(toDo:ToDo){
        CoroutineScope(Dispatchers.Main).launch {
            repository.update(toDo)
        }
    }
}