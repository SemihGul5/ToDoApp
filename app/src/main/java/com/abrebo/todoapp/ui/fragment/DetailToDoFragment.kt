package com.abrebo.todoapp.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.abrebo.todoapp.R
import com.abrebo.todoapp.data.model.ToDo
import com.abrebo.todoapp.databinding.FragmentDetailToDoBinding
import com.abrebo.todoapp.ui.viewmodel.DetailToDoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailToDoFragment : Fragment() {
    private lateinit var binding: FragmentDetailToDoBinding
    private lateinit var viewModel:DetailToDoViewModel
    private var pr=0
    private lateinit var todo:ToDo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:DetailToDoViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentDetailToDoBinding.inflate(inflater, container, false)

        val bundle=DetailToDoFragmentArgs.fromBundle(requireArguments())
        todo=bundle.todo

        binding.toDoDescText.setText(todo.desc)
        binding.dateText.setText(todo.date)
        val priotary=todo.priotary
        // ToggleButton başlangıç durumunu ayarlama
        when (priotary) {
            1 -> binding.toggleButton.check(R.id.buttonPriorityLow)
            2 -> binding.toggleButton.check(R.id.buttonPriorityMid)
            3 -> binding.toggleButton.check(R.id.buttonPriorityHigh)
        }

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if(isChecked){
                when (checkedId) {
                    R.id.buttonPriorityLow -> {
                        pr=1
                    }
                    R.id.buttonPriorityMid -> {
                        pr=2
                    }
                    R.id.buttonPriorityHigh -> {
                        pr=3
                    }
                }
            }
        }

        binding.topAppBarAddToDoPage.setOnMenuItemClickListener {
            if(it.itemId==R.id.menu_delete_todo){
                AlertDialog.Builder(requireContext())
                    .setTitle("Sil")
                    .setMessage("Silmek istediğinizden emin misiniz?")
                    .setPositiveButton("Evet",DialogInterface.OnClickListener { dialogInterface, i ->
                        viewModel.delete(todo.id)
                        Toast.makeText(requireContext(),"Silindi",Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(binding.buttonSave).navigate(R.id.action_detailToDoFragment_to_mainPageFragment)
                    })
                    .setNegativeButton("Hayır",DialogInterface.OnClickListener { dialogInterface, i -> })
                    .show()
            }
            true
        }
        binding.buttonSave.setOnClickListener {
            val desc=binding.toDoDescText.text.toString()
            val date=binding.dateText.text.toString()
            if (!isNull()){
                val updateTodo= ToDo(todo.id,desc,pr,date)
                viewModel.update(updateTodo)
                Toast.makeText(requireContext(),"Güncellendi", Toast.LENGTH_SHORT).show()
            }else{
                Snackbar.make(it,"Tüm alanları doldurun.", Snackbar.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    fun isNull(): Boolean {
        return binding.toDoDescText.text.isNullOrEmpty() || pr == 0 || binding.dateText.text.isNullOrEmpty()
    }
}