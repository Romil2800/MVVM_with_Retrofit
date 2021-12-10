package com.example.mvvmwithretrofit

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.mvvmwithretrofit.api.CharacterService
import com.example.mvvmwithretrofit.api.RetrofitHelper
import com.example.mvvmwithretrofit.repository.CharacterRepository
import com.example.mvvmwithretrofit.viewmodels.MainViewModelFactory
import com.example.mvvmwithretrofit.viewmodels.MainViewModels
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var text: TextView
    lateinit var mainViewModels: MainViewModels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val characterService=RetrofitHelper.getInstance().create(CharacterService::class.java)
        val repository=CharacterRepository(characterService)
        listView = findViewById<ListView>(R.id.listview)


        mainViewModels=ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModels::class.java)


        mainViewModels.character.observe(this, {
            val characterList=it.results
            val listItems = arrayOfNulls<String>(characterList.size)
            for (i in 0 until characterList.size) {
                val data = characterList[i]


                listItems[i] = data.name
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
                listView.adapter = adapter



            }


        })


    }
}