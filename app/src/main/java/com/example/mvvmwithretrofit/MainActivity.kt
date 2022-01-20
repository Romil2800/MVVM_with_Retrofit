package com.example.mvvmwithretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmwithretrofit.repository.Response
import com.example.mvvmwithretrofit.viewmodels.MainViewModelFactory
import com.example.mvvmwithretrofit.viewmodels.MainViewModels
import android.view.Menu
import android.view.MenuInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmwithretrofit.adapter.DataAdapter
import com.example.mvvmwithretrofit.databinding.ActivityMainBinding
import com.example.mvvmwithretrofit.models.Result


class MainActivity : AppCompatActivity() {
    lateinit var mainViewModels: MainViewModels
    lateinit var adapter: DataAdapter
    lateinit var binding: ActivityMainBinding
    private var result = mutableListOf<Result>()
    var pageNum = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        //setContentView(R.layout.activity_main)

        val repository = (application as CharacterApplication).characterRepository

        mainViewModels = ViewModelProvider(
            this,
            MainViewModelFactory(repository,pageNum)
        ).get(MainViewModels::class.java)


        mainViewModels.character.observe(this, {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {


                    it.data?.let {

                        var character=it.results
                        layoutManager(character)
                        getList(character)
                    }

                }
                is Response.Error -> {}

            }


        })

        binding.apply {
            characterRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = DataAdapter(this@MainActivity, result)
            characterRecyclerView.adapter = adapter


        }


    }



    private fun layoutManager(character: List<Result>) {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.characterRecyclerView.layoutManager = linearLayoutManager

        binding.characterRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount = linearLayoutManager.childCount
                val pastVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount
//                Log.d(mTAG, "Total is  $total")
//                Log.d(mTAG, "visibleItemCount is  $visibleItemCount")
//                Log.d(mTAG, "pastVisibleItem is  $pastVisibleItem")
                if ((visibleItemCount + pastVisibleItem) >= total) {
                    pageNum++
                    Log.d("romil", "onScrolled: $pageNum")
                    print(pageNum)
                    getList(character)
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }


    private fun getList(character: List<Result>) {
        result.addAll(character)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.delete_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_delete -> {
                mainViewModels.deleteAllData()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}