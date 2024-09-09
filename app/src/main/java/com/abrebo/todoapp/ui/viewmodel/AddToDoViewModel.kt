package com.abrebo.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.abrebo.todoapp.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToDoViewModel @Inject constructor(var repository: Repository):ViewModel() {

    fun save(desc:String,priotary:Int,date:String){
        CoroutineScope(Dispatchers.Main).launch {
            repository.save(desc, priotary, date)
        }
    }
}