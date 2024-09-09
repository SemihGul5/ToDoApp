package com.abrebo.todoapp.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.abrebo.todoapp.R
import com.abrebo.todoapp.data.model.ToDo
import com.abrebo.todoapp.databinding.FragmentAddToDoBinding
import com.abrebo.todoapp.databinding.ToDoCardLayoutBinding
import com.abrebo.todoapp.ui.fragment.MainPageFragmentDirections
import com.abrebo.todoapp.ui.viewmodel.MainPageViewModel

class ToDoAdapter(var mContext:Context, var todoList: List<ToDo>,var viewModel: MainPageViewModel)
    :RecyclerView.Adapter<ToDoAdapter.CardHolder>() {

    inner class CardHolder(var binding:ToDoCardLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val binding= ToDoCardLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val todo=todoList.get(position)
        val binding=holder.binding

        binding.textViewDesc.text=todo.desc
        binding.textViewDate.text=todo.date
        val priority=todo.priotary
        if (priority==1){
            binding.imageView.setImageResource(R.drawable.baseline_circle_24_green)
        }else if (priority==2){
            binding.imageView.setImageResource(R.drawable.baseline_circle_24_yellow)
        }else if(priority==3){
            binding.imageView.setImageResource(R.drawable.baseline_circle_24_red)
        }

        binding.cardView.setOnClickListener {
            val go=MainPageFragmentDirections.actionMainPageFragmentToDetailToDoFragment(todo)
            Navigation.findNavController(it).navigate(go)
        }


    }
    companion object{
        fun getToDoFromPosition(position: Int,todoList: List<ToDo>):ToDo{
            val todo=todoList.get(position)
            return todo
        }
    }

}