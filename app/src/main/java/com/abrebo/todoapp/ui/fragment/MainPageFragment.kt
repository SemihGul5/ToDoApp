package com.abrebo.todoapp.ui.fragment

import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.abrebo.todoapp.R
import com.abrebo.todoapp.data.model.ToDo
import com.abrebo.todoapp.databinding.FragmentMainPageBinding
import com.abrebo.todoapp.ui.adapter.ToDoAdapter
import com.abrebo.todoapp.ui.viewmodel.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : Fragment() {
    private lateinit var binding:FragmentMainPageBinding
    private lateinit var viewModel:MainPageViewModel
    private lateinit var todoList: List<ToDo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp:MainPageViewModel by viewModels()
        viewModel=temp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentMainPageBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mainPageFragment_to_addToDoFragment)
        }
        todoList=ArrayList<ToDo>()

        // RecyclerView için ItemTouchHelper Callback ayarı
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                // Sürükleme ve bırakma için, burada bir şey yapmaya gerek yok
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Eleman kaydırıldığında yapılacak işlemler
                AlertDialog.Builder(requireContext())
                    .setTitle("Sil")
                    .setMessage("Silmek istediğinizden emin misiniz?")
                    .setPositiveButton("Evet",DialogInterface.OnClickListener { dialogInterface, i ->
                        val position = viewHolder.adapterPosition
                        val todo=ToDoAdapter.getToDoFromPosition(position,todoList)
                        viewModel.delete(todo.id)
                        Toast.makeText(requireContext(),"Silindi",Toast.LENGTH_SHORT).show()
                    })
                    .setNegativeButton("Hayır",DialogInterface.OnClickListener { dialogInterface, i ->
                        viewModel.upload()
                    })
                    .show()
            }
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                // Kaydırma sırasında arka plan çizme (kırmızı ve çöp kutusu ikonu)
                val icon: Drawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.baseline_delete_24_white // Çöp kutusu ikonu
                )!!
                val background = ColorDrawable(Color.RED) // Kırmızı arkaplan

                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
                val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                val iconBottom = iconTop + icon.intrinsicHeight

                if (dX < 0) { // Sola kaydırma
                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                    background.setBounds(
                        itemView.right + dX.toInt() - 20,  // Arkaplanı dX ile orantılı olarak genişlet
                        itemView.top, itemView.right, itemView.bottom
                    )
                } else {
                    background.setBounds(0, 0, 0, 0)  // Eğer sola kaydırılmıyorsa, arka planı sıfırla
                }

                background.draw(c)
                icon.draw(c)

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        viewModel.toDoList.observe(viewLifecycleOwner){
            todoList=it
            val adapter=ToDoAdapter(requireContext(),it,viewModel)
            binding.recyclerView.adapter=adapter
            val itemTouchHelper = ItemTouchHelper(simpleCallback)
            itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        }

        binding.topAppBar.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == R.id.search) {
                val searchView = item.actionView as SearchView
                val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                searchEditText.setTextColor(Color.WHITE)

                searchView.setQuery("", false)
                searchView.requestFocus()

                searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if(query!=null){
                            viewModel.search(query)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if(newText!=null){
                            viewModel.search(newText)
                        }
                        return true
                    }
                })

                return@setOnMenuItemClickListener true
            }
            else if (id==R.id.priorityLow){
                viewModel.priotaryHigh()
            }else if (id==R.id.priorityHigh){
                viewModel.priotaryLow()
            }else if (id==R.id.dateOrderAsc){
                viewModel.orderByDateAsc()
            }else if (id==R.id.dateOrderDesc){
                viewModel.orderByDateDesc()
            }
            false
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.upload()
    }


}