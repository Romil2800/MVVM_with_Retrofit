package com.example.mvvmwithretrofit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmwithretrofit.models.CharacterList
import com.example.mvvmwithretrofit.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModels(private val repository: CharacterRepository) :ViewModel(){

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacter(1)
        }
    }
        val character:LiveData<CharacterList>

        get()=repository.characters

}