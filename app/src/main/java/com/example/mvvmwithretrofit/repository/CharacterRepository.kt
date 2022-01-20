package com.example.mvvmwithretrofit.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmwithretrofit.api.CharacterService
import com.example.mvvmwithretrofit.database.CharacterDatabase
import com.example.mvvmwithretrofit.models.CharacterList
import com.example.mvvmwithretrofit.utils.NetworkUtils
import java.lang.Exception

class CharacterRepository(
    private val characterService: CharacterService,
    private val characterDatabase: CharacterDatabase,
    private val applicationContext: Context
) {

    private val characterLiveData = MutableLiveData<Response<CharacterList>>()

    val characters: LiveData<Response<CharacterList>>
        get() = characterLiveData

    suspend fun getCharacter(page: Int) {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            try {
                val result = characterService.getCharacter(page)
                if (result?.body() != null) {
                    characterDatabase.characterDao().addCharacter(result.body()!!.results)
                    characterLiveData.postValue(Response.Success(result.body()))
                }else
                {
                    characterLiveData.postValue(Response.Error("API Error"))
                }
            }catch (e:Exception){
                characterLiveData.postValue(Response.Error(e.message.toString()))
            }



        } else {
            val characters = characterDatabase.characterDao().getCharacters()
            val characterList = CharacterList(characters)
            characterLiveData.postValue(Response.Success(characterList))
        }


    }

    suspend fun deleteAllData(){
        characterDatabase.characterDao().deleteAllData()
    }

    suspend fun getCharacterBackground() {
        val randomNumber = (Math.random() * 10).toInt()
        val result = characterService.getCharacter(randomNumber)
        if (result?.body() != null) {
            characterDatabase.characterDao().addCharacter(result.body()!!.results)
        }

    }
}