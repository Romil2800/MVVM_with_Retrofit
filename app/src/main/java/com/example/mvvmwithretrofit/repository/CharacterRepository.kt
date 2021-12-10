package com.example.mvvmwithretrofit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmwithretrofit.api.CharacterService
import com.example.mvvmwithretrofit.models.CharacterList

class CharacterRepository(private val characterService:CharacterService) {

    private val characterLiveData=MutableLiveData<CharacterList>()

    val characters:LiveData<CharacterList>
    get() = characterLiveData

    suspend fun getCharacter(page:Int){
        val result=characterService.getCharacter(page)

       if(result?.body()!=null){
           characterLiveData.postValue(result.body())
       }


    }
}