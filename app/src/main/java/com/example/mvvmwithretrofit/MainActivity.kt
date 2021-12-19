package com.example.mvvmwithretrofit

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmwithretrofit.repository.Response
import com.example.mvvmwithretrofit.viewmodels.MainViewModelFactory
import com.example.mvvmwithretrofit.viewmodels.MainViewModels
import android.view.Menu
import android.view.MenuInflater


class MainActivity : AppCompatActivity() {
    lateinit var mainViewModels: MainViewModels

    //lateinit var binding:ActivityMainBinding
    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.listview)
        val repository = (application as CharacterApplication).characterRepository

        mainViewModels = ViewModelProvider(
            this,
            MainViewModelFactory(repository)
        ).get(MainViewModels::class.java)



//        fun onDelete(item: MenuItem) {
//            if (item.itemId == R.id.menu_delete) {
//                mainViewModels.deleteAllData()
//            }
//        }
        mainViewModels.character.observe(this, {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data?.let {
                        val characterList = it.results
                        val listItems = arrayOfNulls<String>(characterList.size)
                        for (i in 0 until characterList.size) {
                            val data = characterList[i]


                            listItems[i] = data.name
                            val adapter =
                                ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
                            listView.adapter = adapter

                            Log.d("romil",data.name)


                        }
                    }

                }
                is Response.Error -> {}

            }


        })


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