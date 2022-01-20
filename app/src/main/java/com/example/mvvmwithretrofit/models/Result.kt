package com.example.mvvmwithretrofit.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val characterId: Int,
    val gender: String,
    val id: Int,
    val image: String,
  //  val location: Location,
    val name: String,
    val species: String,
    val status: String,
)