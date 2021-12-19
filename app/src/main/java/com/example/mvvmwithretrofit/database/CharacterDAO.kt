package com.example.mvvmwithretrofit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mvvmwithretrofit.models.Result


@Dao
interface CharacterDAO {

    @Insert
    suspend fun addCharacter(character:List<Result>)

    @Query("SELECT * FROM characters")
    suspend fun getCharacters():List<Result>

    @Query("DELETE FROM characters")
    suspend fun deleteAllData()
}