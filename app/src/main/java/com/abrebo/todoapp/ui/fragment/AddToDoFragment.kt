package com.abrebo.todoapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.abrebo.todoapp.R
import com.abrebo.todoapp.databinding.FragmentAddToDoBinding
import com.abrebo.todoapp.ui.viewmodel.AddToDoViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
@AndroidEntryPoint
class AddToDoFragment : Fragment() {
    private lateinit var binding:FragmentAddToDoBinding
    private lateinit var viewModel:AddToDoViewModel
    private var priotary:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:AddToDoViewModel by viewModels()
        viewModel=temp
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentAddToDoBinding.inflate(inflater, container, false)

        binding.toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if(isChecked){
                when (checkedId) {
                    R.id.buttonPriorityLow -> {
                        priotary=1
                    }
                    R.id.buttonPriorityMid -> {
                        priotary=2
                    }
                    R.id.buttonPriorityHigh -> {
                        priotary=3
                    }
                }
            }
        }
        binding.dateText.setOnClickListener {
            val dp = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Tarih Seç")
                .build()

            dp.show(parentFragmentManager,"Tarih")

            dp.addOnPositiveButtonClickListener {
                val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val tarih = df.format(it)
                binding.dateText.setText(tarih)
            }
        }



        binding.buttonSave.setOnClickListener {
            if (!isNull()){
                val desc=binding.toDoDescText.text.toString()
                val date=binding.dateText.text.toString()
                viewModel.save(desc,priotary,date)
                Toast.makeText(requireContext(),"Kaydedildi",Toast.LENGTH_SHORT).show()
                Navigation.findNavController(it).navigate(R.id.action_addToDoFragment_to_mainPageFragment)
            }else{
                Snackbar.make(it,"Tüm alanları doldurun.",Snackbar.LENGTH_SHORT).show()
            }
        }



        return binding.root
    }

    fun isNull(): Boolean {
        return binding.toDoDescText.text.isNullOrEmpty() || priotary == 0 || binding.dateText.text.isNullOrEmpty()
    }




}