package com.example.mvvmwithretrofit.api

import com.example.mvvmwithretrofit.models.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("character/")
    suspend fun getCharacter(@Query("page")page:Int):Response<CharacterList>

}