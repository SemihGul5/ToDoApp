package com.abrebo.todoapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abrebo.todoapp.data.model.ToDo
import com.abrebo.todoapp.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(var repository: Repository):ViewModel() {
    var toDoList=MutableLiveData<List<ToDo>>()

    init {
        upload()
    }

    fun upload(){
        CoroutineScope(Dispatchers.Main).launch {
            toDoList.value = repository.upload()
        }
    }
    fun delete(id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            repository.delete(id)
            upload()
        }
    }

    fun search(search:String){
        CoroutineScope(Dispatchers.Main).launch {
            toDoList.value=repository.search(search)
        }
    }

    fun priotaryHigh(){
        CoroutineScope(Dispatchers.Main).launch {
            toDoList.value=repository.priotaryHigh()
        }
    }
    fun priotaryLow(){
        CoroutineScope(Dispatchers.Main).launch {
            toDoList.value=repository.priotaryLow()
        }
    }
    fun orderByDateAsc(){
        CoroutineScope(Dispatchers.Main).launch {
            toDoList.value=repository.orderByDateAsc()
        }
    }
    fun orderByDateDesc(){
        CoroutineScope(Dispatchers.Main).launch {
            toDoList.value=repository.orderByDateDesc()
        }
    }






}
