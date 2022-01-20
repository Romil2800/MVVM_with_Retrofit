package com.example.mvvmwithretrofit.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmwithretrofit.repository.CharacterRepository
import com.example.mvvmwithretrofit.viewmodels.MainViewModels as MainViewModels1

class MainViewModelFactory(private val repository: CharacterRepository,val page:Int) :ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModels1(repository,page) as T
    }
}